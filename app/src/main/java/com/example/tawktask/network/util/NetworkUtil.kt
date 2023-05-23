package com.example.tawktask.network.util

import com.example.tawktask.BuildConfig
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object NetworkUtil {

    fun getRetrofit(baseUrl: String): Retrofit {
        val moshi = Moshi.Builder()
            .build()

        val interceptor = Interceptor { chain ->
            val original: Request = chain.request()
            val request: Request = original.newBuilder()
               // .header("authorization", "Bearer ${BuildConfig.ACCESS_TOKEN}")
                .build()
            chain.proceed(request)
        }

        val client = OkHttpClient.Builder()
            .callTimeout(0, TimeUnit.MINUTES)
            .connectTimeout(0, TimeUnit.SECONDS)
            .readTimeout(0, TimeUnit.SECONDS)
            .writeTimeout(0, TimeUnit.SECONDS)
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        client.addInterceptor(loggingInterceptor)
        client.addInterceptor(interceptor)

        return Retrofit.Builder()
            .client(client.build())
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    }
}