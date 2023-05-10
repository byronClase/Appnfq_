package com.byron.appnfq_1.login.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DataLogin(
    @SerializedName("success")
    @Expose
    val success: Boolean,
    @SerializedName("token")
    @Expose
    val token: String,
    @SerializedName("message")
    @Expose
    val message: String
)