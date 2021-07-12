package com.toyota.toyserv.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.toyota.toyserv.R
import com.toyota.toyserv.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}