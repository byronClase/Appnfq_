package com.byron.appnfq_1.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DataMessage(
    @SerializedName("success")
    @Expose
    val success: Boolean,
    @SerializedName("message")
    @Expose
    val message: String
)
