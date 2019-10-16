package com.fernandez.rico.jetpatck.data.server

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import org.koin.core.KoinComponent
import java.io.IOException

class HeaderInterceptor : Interceptor, KoinComponent {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var requestBuilder: Request.Builder


        requestBuilder = request.newBuilder()

//UNCOMMENT TO SEE WHAT ARE WE SENDING
        Log.i("REQUEST",
            String.format("Sending request %s on %s %s", request.url(), chain.connection(), request.headers()))

        val response = chain.proceed(requestBuilder.build())

//UNCOMMENT TO SEE WHAT ARE WE RECEIVING
        Log.i("REQUEST",
            String.format("Received response for %s, headers: %s", request.url(), response.body()))

        val body = ResponseBody.create(response.body()?.contentType(), response.body()!!.string())
        return response.newBuilder().body(body).build()
    }
}
