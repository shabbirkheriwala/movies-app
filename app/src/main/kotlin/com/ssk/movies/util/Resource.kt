package com.ssk.movies.util

/**
 * Utility class that will communicate the current state of Network Call to the UI Layer
 *
 * @param T
 * @property status - enum SUCCESS, ERROR & LOADING
 * @property data - Data fetched from the network
 * @property message - any message when there is an error
 * @constructor Create empty Resource
 */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    // Representation of the UI State
    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}