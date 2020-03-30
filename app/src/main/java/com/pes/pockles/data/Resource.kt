package com.pes.pockles.data

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 *
 * Source: https://github.com/android/architecture-samples/blob/master/app/src/main/java/com/example/android/architecture/blueprints/todoapp/data/Result.kt
 */
sealed class Resource<out R>(val status: Status) {
    data class Success<out T>(val data: T) : Resource<T>(Status.SUCCESS)
    data class Error(val exception: Throwable) : Resource<Nothing>(Status.ERROR)
    object Loading : Resource<Nothing>(Status.LOADING)

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

/**
 * `true` if [Result] is of type [Resource.Success] & holds non-null [Resource.Success.data].
 */
val Resource<*>.succeeded
    get() = this is Resource.Success && data != null

val Resource<*>.loading
    get() = this is Resource.Loading