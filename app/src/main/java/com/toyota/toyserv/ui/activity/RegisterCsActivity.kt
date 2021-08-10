package com.toyota.toyserv.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.toyota.toyserv.databinding.ActivityRegisterCsBinding
import com.toyota.toyserv.model.DataResponse
import com.toyota.toyserv.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterCsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterCsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterCsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()

        val intentId = intent.getStringExtra("id")
        val intentFullName = intent.getStringExtra("full_name")
        val intentUsername = intent.getStringExtra("username")
        val intentPassword = intent.getStringExtra("password")

        val intentData = intent.getBooleanExtra("intent", false)

        if (intentData) {
            binding.inputFullname.setText(intentFullName)
            binding.inputUsername.setText(intentUsername)
            binding.inputPassword.setText(intentPassword)
            binding.btnRegister.text = "edit"
        }

        binding.btnRegister.setOnClickListener {
            val fullName = binding.inputFullname.text.toString()
            val username = binding.inputUsername.text.toString()
            val password = binding.inputPassword.text.toString()

            when {
                fullName.isEmpty() -> {
                    binding.inputUsername.error = "masukkan fullname"
                }
                username.isEmpty() -> {
                    binding.inputUsername.error = "masukkan username"
                }
                password.isEmpty() -> {
                    binding.inputUsername.error = "masukkan password"
                }
                else -> {
                    if (intentData) {
                        editAkun(
                            intentId, fullName, username, password,
                            "customer_service"
                        )
                    }
                    register(
                        fullName,
                        "",
                        "",
                        "",
                        username,
                        password,
                        "customer_service"
                    )
                }
            }
        }
    }


    private fun register(
        fullName: String,
        vehicle: String,
        policeNumber: String,
        phoneNumber: String,
        username: String,
        password: String,
        type: String,
    ) {

        ApiClient.instances.register(
            fullName,
            vehicle,
            policeNumber,
            phoneNumber,
            username,
            password,
            type
        ).enqueue(object : Callback<DataResponse> {
            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                val value = response.body()?.value
                val message = response.body()?.message

                if (response.isSuccessful && value == "1") {

                    Toast.makeText(this@RegisterCsActivity, message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@RegisterCsActivity, message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                Toast.makeText(this@RegisterCsActivity, t.message.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun editAkun(
        intentId: String?,
        fullName: String,
        username: String,
        password: String,
        type: String
    ) {
        ApiClient.instances.editAccount(intentId!!, fullName, "", "", "", username, password, type)
            .enqueue(
                object : Callback<DataResponse> {
                    override fun onResponse(
                        call: Call<DataResponse>,
                        response: Response<DataResponse>
                    ) {
                        val value = response.body()?.value
                        val message = response.body()?.message

                        if (response.isSuccessful && value == "1") {

                            Toast.makeText(this@RegisterCsActivity, message, Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(this@RegisterCsActivity, message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                        Toast.makeText(
                            this@RegisterCsActivity,
                            t.message.toString(),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                })
    }
}