package com.byron.appnfq_1.usermanagment.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DataProvinces(
    @SerializedName("success")
    @Expose
    val success: Boolean,
    @SerializedName("provinces")
    @Expose
    val provinces: List<Province>
//    ,
//    @SerializedName("message")
//    @Expose
//    val message: String
)