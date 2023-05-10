package com.byron


import SharedPref
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.byron.appnfq_1.R
import com.byron.appnfq_1.fragment.OpenCloseFragment
import com.byron.appnfq_1.fragment.SalesFragment
import com.byron.appnfq_1.fragment.SettingsFragment
import com.byron.appnfq_1.fragment.UserFragment
import com.byron.appnfq_1.login.LoginActivity
import com.byron.appnfq_1.login.model.DataToken
import com.byron.appnfq_1.usermanagment.UserManagementActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPref = SharedPref(this)
        val mitok = loadToken()
        println("mitok: $mitok")
        if (mitok != null ) {
            resetToken()
        }
        if (mitok.isNullOrEmpty()) {
            // Si no hay token, redirige al usuario a LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
//            finish()
        } else {
            // Si hay token, muestra el contenido de MainActivity
            setContentView(R.layout.activity_main)
            initView()
            initListener()
        }

    }

    private fun resetToken() {
        sharedPref.removeToken()
    }


    private fun loadToken(): String? {
        val dataToken: DataToken? = sharedPref.get("token")

        return dataToken?.token
    }//tengo el valor del token inicial

    private fun initView() {
        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)

    }


    private fun activityUser() {
        val intent = Intent(this, UserManagementActivity::class.java)
        startActivity(intent)
        finish()
    }


    private fun initListener() {
        loadDefaultFragment()
//        bottomNavigationView.selectedItemId = R.id.menu_option_managment // Establece el elemento predeterminado
        bottomNavigationView.setOnItemSelectedListener { item: MenuItem ->
            val selectedFragment: Fragment? = when (item.itemId) {
                R.id.menu_option_managment -> {
                    // Acción para la opción 1
                    Log.d("TAG", "Estoy en usuarios")

                    runOnUiThread {
                        Toast.makeText(this, "TOKEN: " + loadToken(), Toast.LENGTH_SHORT).show()
                    }//no significa que sea asincrono
                    supportActionBar?.setTitle(R.string.client_management)
                    UserFragment() // Instancia del fragment de usuarios
                }

                R.id.menu_option_sales -> {
                    // Acción para la opción 2
                    Log.d("TAG", "Estoy en ventas")
                    supportActionBar?.setTitle(R.string.sales)
                    SalesFragment() // Instancia del fragment de ventas
                }

                R.id.menu_option_register -> {
                    // Acción para la opción 3
                    Log.d("TAG", "Estoy en caja")
                    resetToken()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    supportActionBar?.setTitle(R.string.open_close_cash_register)
                    OpenCloseFragment() // Instancia del fragment de registro
                }

                R.id.menu_option_settings -> {
                    // Acción para la opción 4
                    Log.d("TAG", "Estoy en ajustes")
                    supportActionBar?.setTitle(R.string.settings)
                    SettingsFragment() // Instancia del fragment de ajustes
                }

                else -> null
            }

            // Reemplazar el fragment en el contenedor
            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frameContainer, selectedFragment)
                    .commit()
            }

            true
        }


    }

    private fun loadDefaultFragment() {
        supportActionBar?.setTitle(R.string.client_management)
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameContainer, UserFragment())
            .commit()
    }


}