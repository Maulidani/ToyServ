package com.toyota.toyserv.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.toyota.toyserv.R
import com.toyota.toyserv.databinding.ActivityMainClientBinding
import com.toyota.toyserv.ui.fragment.*

class MainClientActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainClientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainClientBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()

        binding.sheet.visibility = View.INVISIBLE
        BottomSheetBehavior.from(binding.sheet).apply {
            peekHeight = 100
            this.state = BottomSheetBehavior.STATE_DRAGGING
        }

        binding.cardServis.setOnClickListener {
            binding.sheet.visibility = View.VISIBLE
            binding.tvKeterangan.text = "Servis"
            loadFragment(ServisFragment())
        }
        binding.cardServisSaya.setOnClickListener {
            binding.sheet.visibility = View.VISIBLE
            binding.tvKeterangan.text = "Servis Saya"
            loadFragment(ServisSayaFragment())
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame, fragment)
            commit()
        }
    }
}