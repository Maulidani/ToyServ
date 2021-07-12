package com.toyota.toyserv.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.toyota.toyserv.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }
}