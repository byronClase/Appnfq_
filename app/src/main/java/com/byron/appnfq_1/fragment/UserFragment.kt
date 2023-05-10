package com.byron.appnfq_1.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.byron.appnfq_1.R
import com.byron.appnfq_1.api.API2
import com.byron.appnfq_1.usermanagment.model.DataProvinces
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListenerAndView(view)
    }

    private fun getDataProvinces64(){
        // Inicializa API
        val apiService = API2.createApiService(requireContext())
        apiService.receiveProvincesNew("64") // Reemplaza "some_id" con el ID de la provincia que deseas obtener
            .enqueue(object : Callback<DataProvinces> {
                override fun onResponse(call: Call<DataProvinces>, response: Response<DataProvinces>) {
                    if (response.isSuccessful) {
                        val provinces = response.body()?.provinces
                        val provinceNames = provinces?.joinToString(", ") { it.name }
                        Toast.makeText(requireContext(), "Provincias: $provinceNames", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Error al obtener las provincias.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<DataProvinces>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error en la llamada a la API.", Toast.LENGTH_SHORT).show()
                }
            })
    }
    @SuppressLint("CutPasteId")
    private fun initListenerAndView(view: View){
        // Inicializar el Spinner con las opciones de modificación
        val spinnerOptions = arrayOf("Código postal", "Correo electrónico", "Contraseña")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, spinnerOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view.findViewById<Spinner>(R.id.spinner_options).adapter = adapter

        // Inicializar el botón para actualizar la información
        view.findViewById<Button>(R.id.btn_update).setOnClickListener {
            val selectedItem = view.findViewById<Spinner>(R.id.spinner_options).selectedItem.toString()
            val newInfo = view.findViewById<EditText>(R.id.et_input).text.toString()

            if (newInfo.isBlank()) {
                Toast.makeText(requireContext(), "Por favor, introduce la nueva información.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            when (selectedItem) {
                "Código postal" -> {
                    getDataProvinces64()
                    // Actualizar el código postal

                }
                "Correo electrónico" -> {
                    // Actualizar el correo electrónico
                }
                "Contraseña" -> {
                    // Actualizar la contraseña
                }
            }

            Toast.makeText(requireContext(), "Información actualizada: $selectedItem - $newInfo", Toast.LENGTH_SHORT).show()
        }
    }

}