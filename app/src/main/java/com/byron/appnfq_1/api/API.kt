package com.byron.appnfq_1.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object API {
    fun createApiService(baseUrl: String): ApiService {
        val retrofitService: ApiService by lazy {
            getRetrofit(baseUrl).create(ApiService::class.java)
        }
        return retrofitService
    }

    private fun getRetrofit(baseUrl: String): Retrofit {
        val logging = HttpLoggingInterceptor()
        val httpClient = OkHttpClient.Builder()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        httpClient.addInterceptor(logging)
        val gson = GsonBuilder().setLenient().create()

        return Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())
            .build()
    }
}

//    private lateinit var baseUrl : String
//    val retrofitService: ApiService by lazy {
//        getRetrofit(baseUrl).create(ApiService::class.java)
//    }//solo se crea cuando alguien lo llama
//
//    fun getRetrofit(baseUrl: String): Retrofit {
//        val logging = HttpLoggingInterceptor()//sirve para ver la llamada
//        val httpClient = OkHttpClient.Builder()
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
//        httpClient.addInterceptor(logging)//esta en medio de la llamada para interceptar cosas
//        val gson = GsonBuilder().setLenient().create()
//
//        return Retrofit
//            .Builder()
//            .baseUrl(baseUrl)
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .client(httpClient.build())
//            .build()
//    }
