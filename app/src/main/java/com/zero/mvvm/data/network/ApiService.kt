package com.zero.mvvm.data.network

import com.zero.mvvm.data.model.DogRandom
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {
    @GET("breeds/image/random")
    fun getRandomDog(): Single<DogRandom>
}