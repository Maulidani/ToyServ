package com.toyota.toyserv.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.toyota.toyserv.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}