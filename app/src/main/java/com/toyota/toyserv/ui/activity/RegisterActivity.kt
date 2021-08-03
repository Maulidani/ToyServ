package com.toyota.toyserv.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.toyota.toyserv.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()

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
                else -> register(
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

    private fun register(
        fullName: String,
        vehicle: String,
        policeNumber: String,
        phoneNumber: String,
        username: String,
        password: String,
        type: String,
    ) {

    }
}