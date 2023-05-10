package com.byron.appnfq_1.api

import com.byron.appnfq_1.login.model.DataLogin
import com.byron.appnfq_1.usermanagment.model.DataProvinces
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {


    @Headers("Authorization: Bearer 287b6b965cbd645afcb16a177c75ca74")
    @FormUrlEncoded
    @POST("provinces.php")
    fun receiveProvinces(@Field("id") id: String): Call<DataProvinces>

    @FormUrlEncoded
    @POST("provinces.php")
    fun receiveProvincesNew(@Field("id") id: String): Call<DataProvinces>

    @FormUrlEncoded
    @POST("login.php")
    fun loginCallPost(@Field("email") email: String, @Field("password") password: String): Call<DataLogin>

}