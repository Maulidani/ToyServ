package com.toyota.toyserv.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.toyota.toyserv.databinding.ActivityRegisterBinding
import com.toyota.toyserv.model.DataResponse
import com.toyota.toyserv.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()

        val intentId = intent.getStringExtra("id")
        val intentFullName = intent.getStringExtra("full_name")
        val intentVehicle = intent.getStringExtra("vehicle")
        val intentPoliceNumber = intent.getStringExtra("police_number")
        val intentPhone = intent.getStringExtra("phone")
        val intentUsername = intent.getStringExtra("username")
        val intentPassword = intent.getStringExtra("password")

        val intentData = intent.getBooleanExtra("intent", false)

        if (intentData) {
            binding.inputFullname.setText(intentFullName)
            binding.inputVehicle.setText(intentVehicle)
            binding.inputPoliceNumber.setText(intentPoliceNumber)
            binding.inputPhoneNumber.setText(intentPhone)
            binding.inputUsername.setText(intentUsername)
            binding.inputPassword.setText(intentPassword)
            binding.btnRegister.text = "edit"
        }

        binding.btnRegister.setOnClickListener {
            val fullName = binding.inputFullname.text.toString()
            val vehicle = binding.inputVehicle.text.toString()
            val policeNumber = binding.inputPoliceNumber.text.toString()
            val phoneNumber = binding.inputPhoneNumber.text.toString()
            val username = binding.inputUsername.text.toString()
            val password = binding.inputPassword.text.toString()

            when {
                fullName.isEmpty() -> {
                    binding.inputUsername.error = "masukkan fullname"
                }
                vehicle.isEmpty() -> {
                    binding.inputUsername.error = "masukkan vehicle"
                }
                policeNumber.isEmpty() -> {
                    binding.inputUsername.error = "masukkan no. polisi"
                }
                phoneNumber.isEmpty() -> {
                    binding.inputUsername.error = "masukkan no. hp"
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
                            intentId!!,
                            fullName,
                            vehicle,
                            policeNumber,
                            phoneNumber,
                            username,
                            password,
                            "customer"
                        )
                    } else {
                        register(
                            fullName,
                            vehicle,
                            policeNumber,
                            phoneNumber,
                            username,
                            password,
                            "customer"
                        )
                    }
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

                    Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, t.message.toString(), Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

    private fun editAkun(
        id: String,
        fullName: String,
        vehicle: String,
        policeNumber: String,
        phoneNumber: String,
        username: String,
        password: String,
        type: String
    ) {

        ApiClient.instances.editAccount(
            id,
            fullName,
            vehicle,
            policeNumber,
            phoneNumber,
            username,
            password,
            type
        ).enqueue(
            object : Callback<DataResponse> {
                override fun onResponse(
                    call: Call<DataResponse>,
                    response: Response<DataResponse>
                ) {
                    val value = response.body()?.value
                    val message = response.body()?.message

                    if (response.isSuccessful && value == "1") {

                        Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                    Toast.makeText(this@RegisterActivity, t.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }
}