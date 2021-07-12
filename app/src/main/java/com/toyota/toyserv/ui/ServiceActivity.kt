package com.toyota.toyserv.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.toyota.toyserv.databinding.ActivityServiceBinding

class ServiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityServiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}