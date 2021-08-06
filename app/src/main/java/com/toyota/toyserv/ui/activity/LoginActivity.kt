package com.toyota.toyserv.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import apotekku.projectapotekku.utils.Constant
import apotekku.projectapotekku.utils.PreferencesHelper
import com.toyota.toyserv.MainActivity
import com.toyota.toyserv.R
import com.toyota.toyserv.databinding.ActivityLoginBinding
import com.toyota.toyserv.model.DataResponse
import com.toyota.toyserv.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var sharedPref: PreferencesHelper
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()
        sharedPref = PreferencesHelper(this)

        binding.btnLogin.setOnClickListener {
            val username = binding.inputUsername.text.toString()
            val password = binding.inputPassword.text.toString()
            val type = "customer"

            when {
                username.isEmpty() -> {
                    binding.inputUsername.error = "masukkan username"
                }
                password.isEmpty() -> {
                    binding.inputPassword.error = "masukkan password"
                }
                else -> {
                    login(type, username, password)
                }
            }
        }

        binding.tvLoginCsAdmin.setOnClickListener {
            startActivity(Intent(this, LoginCsAdminActivity::class.java))
        }
    }

    private fun login(type: String, username: String, password: String) {

        ApiClient.instances.login(type, username, password)
            .enqueue(object : Callback<DataResponse> {
                override fun onResponse(
                    call: Call<DataResponse>,
                    response: Response<DataResponse>
                ) {
                    val value = response.body()?.value
                    val message = response.body()?.message

                    if (response.isSuccessful && value == "1") {
                        val type = response.body()?.type
                        val id = response.body()?.id
                        saveSession(id, type)
                        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT)
                            .show()
                    }

                }

                override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                    Toast.makeText(
                        this@LoginActivity,
                        t.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
    }

    private fun saveSession(id: String?, type: String?) {

        sharedPref.put(Constant.PREF_IS_LOGIN_ID, id.toString())
        sharedPref.put(Constant.PREF_IS_LOGIN_TYPE, type.toString())
        sharedPref.put(Constant.PREF_IS_LOGIN, true)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onResume() {
        super.onResume()
        if (sharedPref.getBoolean(Constant.PREF_IS_LOGIN)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
        }
    }
}