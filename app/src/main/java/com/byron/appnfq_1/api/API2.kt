package com.byron.appnfq_1.api

import SharedPref
import android.content.Context
import com.byron.appnfq_1.login.model.DataToken
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object API2 {

    fun createApiService(context: Context): ApiService {
        val sharedPref = SharedPref(context)
        val baseUrl = sharedPref.getBaseUrl("base_url") ?: throw IllegalStateException("Base URL not found in SharedPreferences")

        val retrofitService: ApiService by lazy {
            getRetrofit(baseUrl, context).create(ApiService::class.java)
        }
        return retrofitService
    }

    private fun getRetrofit(baseUrl: String, context: Context): Retrofit {
        val logging = HttpLoggingInterceptor()
        val httpClient = OkHttpClient.Builder()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        httpClient.addInterceptor(logging)

        // Agrega el interceptor para el token
        val tokenInterceptor = Interceptor { chain ->
            val request = chain.request()

            val sharedPref = SharedPref(context)
            val dataToken: DataToken? = sharedPref.get("token")
            val token = dataToken?.token ?: ""

            val newRequest = request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()

            chain.proceed(newRequest)
        }

        httpClient.addInterceptor(tokenInterceptor)

        val gson = GsonBuilder().setLenient().create()

        return Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())
            .build()
    }
}
