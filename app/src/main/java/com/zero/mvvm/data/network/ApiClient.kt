package com.zero.mvvm.data.network

import com.zero.mvvm.BuildConfig
import com.zero.mvvm.data.model.DogRandom
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    private val api: ApiService

    init {
        val client = OkHttpClient().newBuilder().apply {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else
                    HttpLoggingInterceptor.Level.NONE
            })
            addInterceptor(Interceptor { chain ->
                val builder = chain.request().newBuilder()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")

                return@Interceptor chain.proceed(builder.build())
            })
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
        }

        val server =
            "https://dog.ceo/api/"
        api = Retrofit.Builder()
            .baseUrl(server)
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .build()
            .create(ApiService::class.java)

        api.getRandomDog()
    }

    fun getRandomDog(): Single<DogRandom> {
        return api.getRandomDog()
    }
}