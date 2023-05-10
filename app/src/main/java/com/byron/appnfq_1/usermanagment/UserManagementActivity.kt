package com.byron.appnfq_1.usermanagment

import SharedPref
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.byron.appnfq_1.R
import com.byron.appnfq_1.api.API.createApiService
import com.byron.appnfq_1.api.ApiService
import com.byron.appnfq_1.databinding.ActivityUserManagmentBinding
import com.byron.appnfq_1.usermanagment.model.DataProvinces
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserManagementActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserManagmentBinding
    private lateinit var tv: TextView
    private var myApiService: ApiService? = null
    private lateinit var sharedPref: SharedPref


    override fun onCreate(savedInstanceState: Bundle?) {
        //guardo el estado de mi actividad
        super.onCreate(savedInstanceState)
        //ejecuto el xml cuando este en el onCreate
        setContentView(R.layout.activity_user_managment)
        //le doy acceso al R.id.componentes de mi xml
        binding = ActivityUserManagmentBinding.inflate(layoutInflater)
        //le doy acceso
        setContentView(binding.root)
        //instancio mis preferencias de sistema para la ejecucion de mi datos almacenados
        sharedPref = SharedPref(this)

        initViews()

//        getProvinces()
    }

    private fun initViews() {
        val buttoncreateuser = binding.buttonCreate
        val buttondeleteuser = binding.buttonDelete
        val buttonsearchuser = binding.buttonSearch
        val etsearchuser = binding.editTextNewUser
        val etcreateuser = binding.editTextNewUser
        val recyclerviewuser = binding.recyclerViewUsers
    }

    private fun getProvinces() {
        val baseUrl = sharedPref.getBaseUrl("base_url")
        myApiService =
            createApiService(baseUrl.toString()) // Inicializa myApiService antes de llamar a getProvinces()

        // Realiza la llamada a la API utilizando myApiService
        val call = myApiService?.receiveProvinces("64")
//        val call = retrofitService.enviarDatosbd("64")
        Log.d(ContentValues.TAG, "get provinces ************************************")

        call?.enqueue(object : Callback<DataProvinces> {
            override fun onResponse(
                call: Call<DataProvinces>,
                response: Response<DataProvinces>
            ) {
                if (response.isSuccessful) {
                    val dataResponse = response.body()
                    if (dataResponse != null) {
                        Log.d(ContentValues.TAG, "RESPONSE ************************************")
                        checkResult2(dataResponse)
                    }
                } else {
                    Log.d(ContentValues.TAG, "NO SALIO ************************************")
                }

            }

            override fun onFailure(call: Call<DataProvinces>, t: Throwable) {
                // Manejar el error aquí
                showError()
                Log.e(ContentValues.TAG, "ALGO FALLO ************************************")
                println("No se pudo obtener. El error: ${t.message}")
            }
        })
    }


    private fun checkResult2(dataResponse: DataProvinces) {//spinner
        if (dataResponse.success) {
            showSuccess()
//            val provinces = dataResponse.provinces
//
//// Comprueba que la lista tenga al menos dos elementos antes de intentar acceder al índice 1
//            if (provinces.size >= 2) {
//                val provinceName = provinces[1].name
//                binding.textViewUser.text = provinceName
//            } else {
//                showError()
//                // Muestra un mensaje de error o maneja el caso en el que no haya suficientes provincias en la lista
//            }
        } else {
            showError()
//            Log.e(TAG, "Error: ${dataResponse.message}")
        }
    }

    private fun showSuccess() {
        runOnUiThread {
            Toast.makeText(this, "Succesfull", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showError() {
        runOnUiThread {
            Toast.makeText(this, "Something wrong happens", Toast.LENGTH_SHORT).show()
        }//no significa que sea asincrono
    }

}