package com.byron.appnfq_1.login


import SharedPref
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.byron.MainActivity
import com.byron.appnfq_1.R
import com.byron.appnfq_1.api.API.createApiService
import com.byron.appnfq_1.api.ApiService
import com.byron.appnfq_1.login.model.DataLogin
import com.byron.appnfq_1.login.model.DataToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private lateinit var sharedPref: SharedPref
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextUrl: EditText
    private lateinit var buttonLogin: Button
    private lateinit var progressBar: ProgressBar
    var myApiService: ApiService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sharedPref = SharedPref(this)
        initView()
        initListener()
    }

    private fun initListener() {

        buttonLogin.setOnClickListener {
            showLoad() // Mostrar ProgressBar antes de realizar la operación            submitUrl()
            submitUrl() //leo de mi edit text de URL
            getToken(getEditTextValueEmail(), getEditTextValuePassword())
            //            finish()
        }

    }


    private fun submitUrl() {
        val baseUrl = editTextUrl.text.toString().trim()
        val key = "base_url"
        sharedPref.saveBaseUrl(baseUrl, key)
        if (baseUrl.isNullOrEmpty()) {
            editTextUrl.error = "Por favor, ingrese una URL válida"
            runOnUiThread {
                Toast.makeText(this, "Por favor, ingrese una URL válida", Toast.LENGTH_SHORT).show()
            }
            return
        }
        myApiService = createApiService(baseUrl)
        // Realiza la llamada a la API utilizando myApiService

    }


    fun saveToken(token: String) {
        val dataToken = DataToken(token)
        sharedPref.put(dataToken, "token")
    }

    fun loadToken(): String? {
        val dataToken: DataToken? = sharedPref.get("token")
        return dataToken?.token
    }


    private fun checkResult(dataResponse: DataLogin): Boolean {
        showLoad()
        if (dataResponse.success) {
            hideLoad()
            showSuccess()
            Log.d("TAG", "Éxito: ${dataResponse.token}")
            return true

        } else {
            hideLoad()
            showError()
            Log.e("TAG", "Error: ${dataResponse.message}")
            return false
        }
    }


    private fun getToken(email: String, password: String) {
//        var userToken: DataToken
        val call = myApiService?.loginCallPost(email, password)
        call?.enqueue(object : Callback<DataLogin> {
            override fun onResponse(call: Call<DataLogin>, response: Response<DataLogin>) {
                if (response.isSuccessful) {
                    val dataResponse = response.body()
                    if (dataResponse != null && checkResult(dataResponse)) {
                       hideLoad()
                        saveToken(dataResponse.token)
                        showSuccess()
                        onSuccessfulLogin()
                    }
                }
            }

            override fun onFailure(call: Call<DataLogin>, t: Throwable) {
                Log.e("TAG", "Failed to fetch. Error: ${t.message}")
                showError()
                hideLoad()
//                loginSession()
            }
        })
    }

    private fun onSuccessfulLogin() {
        // Redirige al usuario a MainActivity
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }



    private fun getEditTextValueEmail(): String {
        // Obtiene la referencia del EditText por su ID
        editTextEmail = findViewById(R.id.editTextEmail)
        // Obtiene y devuelve el valor del EditText como una cadena
        return editTextEmail.text.toString()
    }

    private fun getEditTextValuePassword(): String {
        // Obtiene la referencia del EditText por su ID
        editTextPassword = findViewById(R.id.editTextPassword)
        // Obtiene y devuelve el valor del EditText como una cadena
        return editTextPassword.text.toString()
    }

    private fun initView() {
//        editTextPassword = findViewById(R.id.editTextPassword)
        editTextUrl = findViewById(R.id.editTextUrl)
        buttonLogin = findViewById(R.id.buttonLogin)
        progressBar = findViewById(R.id.progressBar)
    }

    private fun showLoad() {
        runOnUiThread {
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun hideLoad() {
        runOnUiThread {
            progressBar.visibility = View.GONE
        }
    }

    private fun showSuccess() {
        runOnUiThread {
            Toast.makeText(this, "login successful", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showError() {
        runOnUiThread {
            Toast.makeText(this, "User/Password/Url incorrect.", Toast.LENGTH_SHORT).show()
        }//no significa que sea asincrono
    }

}