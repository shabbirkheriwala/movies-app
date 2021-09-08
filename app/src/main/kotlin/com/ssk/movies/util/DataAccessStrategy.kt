package com.ssk.movies.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.ssk.movies.util.Resource.Status.*
import kotlinx.coroutines.Dispatchers

/**
 * This higher order function to define the data access strategy for the app
 *
 * @param T Type of Live data
 * @param A Response Type of network data
 * @param dbQuery Query the database for cached data
 * @param networkCall Do a network call to fetch latest data
 * @param saveCallResult Save the latest result to the database
 * @return Update data to the UI via LiveData
 */
fun <T, A> performDataOperation(dbQuery: () -> LiveData<T>,
                                networkCall: suspend () -> Resource<A>,
                                saveCallResult: suspend (A) -> Unit): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
//        android.util.Log.d("APP_DEBUG", "performDataOperation() : emitting loading");
        emit(Resource.loading())
        val source = dbQuery.invoke().map { Resource.success(it) }
//        android.util.Log.d("APP_DEBUG", "performDataOperation() : database query done");
        emitSource(source)

//        android.util.Log.d("APP_DEBUG", "performDataOperation() : network call to fetch data");
        val responseStatus = networkCall.invoke()
        if (responseStatus.status == SUCCESS) {
            saveCallResult(responseStatus.data!!)
//            android.util.Log.d("APP_DEBUG", "performDataOperation() : network call SUCCESS, insert data to DB");

        } else if (responseStatus.status == ERROR) {
//            android.util.Log.d("APP_DEBUG", "performDataOperation() : network ERROR");
            emit(Resource.error(responseStatus.message!!))
            emitSource(source)
        }
    }
