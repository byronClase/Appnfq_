package com.byron.appnfq_1.login

import SharedPref
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.byron.appnfq_1.api.API
import com.byron.appnfq_1.api.ApiService
import com.byron.appnfq_1.api.controller.ProcessResult
import com.byron.appnfq_1.databinding.FragmentLoginBinding

class LoginFragment : Fragment(), ProcessResult {

    private lateinit var sharedPref: SharedPref
    private var myApiService: ApiService? = null
//    private lateinit var apiUtils: ApiUtils
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        sharedPref = SharedPref(requireContext())
//        apiUtils = ApiUtils()
        initView()
        initListener()

        return view
    }

    private fun initListener() {

        binding.buttonLogin.setOnClickListener {
            showLoad() // Mostrar ProgressBar antes de realizar la operación            submitUrl()
            submitUrl() //leo de mi edit text de URL
            getToken(getEditTextValueEmail(), getEditTextValuePassword())
            //            finish()
        }

    }


    private fun submitUrl() {
        val baseUrl = binding.editTextUrl.text.toString().trim()
        val key = "base_url"
        sharedPref.saveBaseUrl(baseUrl, key)
        if (baseUrl.isNullOrEmpty()) {
            binding.editTextUrl.error = "Por favor, ingrese una URL válida"

            Toast.makeText(
                requireContext(),
                "Por favor, ingrese una URL válida",
                Toast.LENGTH_SHORT
            ).show()

            return
        }
        myApiService = API.createApiService(baseUrl)
        // Realiza la llamada a la API utilizando myApiService

    }

    private fun onSuccessfulLogin() {
        //TODO
    }

    private fun getToken(email: String, password: String) {
//        val loginCall = myApiService?.loginCallPost(email, password) as Call<T>
//        apiUtils.fetchData(loginCall, this)
        TODO()
    }

    override fun onSuccess() {
        hideLoad()
        onSuccessfulLogin()
    }

    override fun onError(errorMessage: String) {
        showError()
        hideLoad()

    }

    private fun getEditTextValueEmail(): String {
        // Obtiene y devuelve el valor del EditText como una cadena
        return binding.editTextEmail.text.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun getEditTextValuePassword(): String {
        return binding.editTextPassword.text.toString()
    }

    private fun initView() {
//        editTextPassword = findViewById(R.id.editTextPassword)

    }

    private fun showLoad() {

        binding.progressBar.visibility = View.VISIBLE

    }

    private fun hideLoad() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showSuccess() {

        Toast.makeText(requireContext(), "login successful", Toast.LENGTH_SHORT).show()

    }

    private fun showError() {

        Toast.makeText(requireContext(), "User/Password/Url incorrect.", Toast.LENGTH_SHORT).show()
        //no significa que sea asincrono
    }


}