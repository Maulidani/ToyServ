package com.toyota.toyserv.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.toyota.toyserv.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


    }
}