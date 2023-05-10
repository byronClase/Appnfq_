package com.byron.appnfq_1.usermanagment.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

data class DataResponse(
    @SerializedName("success")
    @Expose
    val success: String,

    @SerializedName("provinces")
    @Expose
    val provinces: String
)