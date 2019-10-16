package com.fernandez.rico.jetpatck.core

import kotlinx.coroutines.*

interface BoundaryCoroutine<in Scope> where Scope : CoroutineScope
{
    fun execute(scope: Scope, dataIncome: ()->Unit)
    {
        scope.launch {

            val deferred = async { dataIncome.invoke() }

            withContext(Dispatchers.Main)
            {
                deferred.await()
            }
        }
    }
}