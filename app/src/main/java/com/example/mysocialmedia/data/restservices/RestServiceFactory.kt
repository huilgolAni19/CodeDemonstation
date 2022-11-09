package com.example.mysocialmedia.data.restservices

import com.example.mysocialmedia.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RestServiceFactory {

    companion object {

        private val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        private val client = OkHttpClient().newBuilder().apply {
            addInterceptor(httpLoggingInterceptor)
        }.build()


        fun getRetrofitInstance(): Retrofit {

            return Retrofit.Builder().apply {
                addConverterFactory(GsonConverterFactory.create())
                baseUrl(Constants.BASE_URL)
                client(client)
            }.build()

        }
    }
}