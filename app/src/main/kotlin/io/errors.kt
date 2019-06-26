package io

import retrofit2.HttpException

sealed class RisError{
    data class RestError(val cause: ApiError):  RisError()
    object Unknown:                             RisError()
}

enum class ApiError {
    badResponse, server, connection/*, other*/
}

fun Throwable.mapError() = when(this){
    is HttpException    -> RisError.RestError(code().parseHttpError())
    else                -> RisError.Unknown
}

private fun Int.parseHttpError() = when(this){
    404     -> ApiError.badResponse
    500     -> ApiError.server
    else    -> ApiError.connection
}