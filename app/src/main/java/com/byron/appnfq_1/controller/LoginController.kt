package com.byron.appnfq_1.controller

// LoginController.kt

import android.util.Log
import com.byron.appnfq_1.api.ApiService
import com.byron.appnfq_1.login.model.DataLogin


class LoginController(
    private val retrofitService: ApiService,
    private val loginCallback: LoginCallback
) {

    interface LoginCallback {
        fun onSuccess()
        fun onError()
        fun showLoading()
        fun hideLoading()

    }



    private fun checkResult(dataResponse: DataLogin): Boolean {
        if (dataResponse.success) {
            loginCallback.onSuccess()
            Log.d("TAG", "Éxito: ${dataResponse.token}")
            return true

        } else {
            loginCallback.onError()
            Log.e("TAG", "Error: ${dataResponse.message}")
            return false
        }
    }


    /*fun getToken(email: String, password: String){
        var userToken: DataToken
        val call = API.retrofitService.loginCallPost(email, password)

        call.enqueue(object : Callback<DataLogin> {
            override fun onResponse(call: Call<DataLogin>, response: Response<DataLogin>) {
                if(response.isSuccessful){
                    val dataResponse = response.body()
                    if(dataResponse!=null && checkResult(dataResponse)){
//                        userToken = DataToken(dataResponse.token)
//                        saveToken(dataResponse.token)
                    }
                }
            }

            override fun onFailure(call: Call<DataLogin>, t: Throwable) {
                Log.e("TAG", "Failed to fetch. Error: ${t.message}")
                loginCallback.onError()
            }
        })
    }*/

//    fun getDataLogin(email: String, password: String) {
//
//        val call = API.retrofitService.loginCallPost(email, password)
//
//        call.enqueue(object : Callback<DataLogin> {
//            override fun onResponse(call: Call<DataLogin>, response: Response<DataLogin>) {
//                if (!response.isSuccessful) {
//                    loginCallback.onError()
//                } else {
//                    val dataResponse = response.body()
//                    if (dataResponse != null && checkResult(dataResponse)) {
//                        val receivedToken = dataResponse.token
//                        val userToken = DataToken(receivedToken)
//                        println("Token: ${userToken.token}")
//                    }
//
//                }
//            }
//
//            override fun onFailure(call: Call<DataLogin>, t: Throwable) {
//                // Handle the error here
//                Log.e("TAG", "Failed to fetch. Error: ${t.message}")
//                loginCallback.onError()
//            }
//        })
//    }

}




