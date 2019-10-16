package com.fernandez.rico.jetpatck.core

import retrofit2.Call

interface BaseNetWorkRepository
{

    fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R>
    {
        return try {
            val response = call.execute()


            when (response.isSuccessful) {
                true -> Either.Right(transform((response.body() ?: default)))
                false -> {

                    Either.Left(Failure.ServerErrorCode(response.code()))
                }
            }
        } catch (exception: Throwable) {
            Either.Left(Failure.ServerException(exception))
        }
    }

}