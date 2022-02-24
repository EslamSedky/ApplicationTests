package com.sedky.applicationtests.utils

class Resource<out T>(val status: Status, val data: T?, message: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> = Resource(status = Status.SUCCESS, data = data, null)

        fun <T> error(data: T?, message: String?): Resource<T> =
            Resource(status = Status.ERROR, data = data, message = message)

        fun<T> loading(data : T?) : Resource<T> = Resource(status = Status.LOADING , data = data,null)
    }
}