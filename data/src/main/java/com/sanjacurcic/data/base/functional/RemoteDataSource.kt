package com.sanjacurcic.data.base.functional

import Either
import retrofit2.Response

abstract class RemoteDataSource {

    protected suspend fun <T> getResult(
        call: suspend () -> Response<T>
    ): Either<Throwable, T> {
        val response = try {
            call.invoke()
        } catch (t: Throwable) {
            return Either.Error(t)
        }
        return handleResponse(response)
    }

    private fun <T> handleResponse(response: Response<T>): Either<Throwable, T> {

        val statusCode = response.code()

        if (statusCode < 200 || statusCode >= 400) {
            return Either.Error(Throwable(statusCode.toString()))
        }
        return Either.Result(response.body()!!)
    }
}