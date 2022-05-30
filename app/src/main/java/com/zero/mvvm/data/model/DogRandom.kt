package com.zero.mvvm.data.model

import com.google.gson.annotations.SerializedName

data class DogRandom(
    @SerializedName("message")  val message: String,
    @SerializedName("status")  val status: String
)
