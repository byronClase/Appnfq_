package com.byron.appnfq_1.api.controller

import android.util.Log
import com.byron.appnfq_1.login.model.NewDataResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ApiUtils {
    fun <T : NewDataResponse> checkResult(dataResponse: T, processResult: ProcessResult): Boolean {

        if (dataResponse.success) {

            Log.d("TAG", "Éxito: ${dataResponse.message}")
            processResult.onSuccess()
            return true

        } else {
            Log.e("TAG", "Error: ${dataResponse.message}")
            processResult.onError(dataResponse.message)
            return false
        }
    }

   fun <T : NewDataResponse> fetchData(call: Call<T>, processResult: ProcessResult) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    val dataResponse = response.body()
                    if (dataResponse != null) {
                        checkResult(dataResponse, processResult)
                    }
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                Log.e("TAG", "Failed to fetch. Error: ${t.message}")
                processResult.onError(t.message ?: "Error desconocido")
            }
        })
    }

}