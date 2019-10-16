package com.fernandez.rico.jetpatck.core

sealed class Failure
{
    object ConnectivityError: Failure()
    object ParseGsonError : Failure()
    class ServerErrorCode(val error: Int): Failure()
    class ServerException(val exception: Throwable): Failure()

    abstract class CustomFailure(): Failure() //inherit from here from custom failure
}