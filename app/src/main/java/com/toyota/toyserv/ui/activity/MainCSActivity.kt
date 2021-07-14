package com.toyota.toyserv.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.toyota.toyserv.R
import com.toyota.toyserv.databinding.ActivityMainCsBinding
import com.toyota.toyserv.ui.fragment.*

class MainCSActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainCsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainCsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()

        binding.sheet.visibility = View.INVISIBLE
        BottomSheetBehavior.from(binding.sheet).apply {
            peekHeight = 100
            this.state = BottomSheetBehavior.STATE_DRAGGING
        }

        binding.cardSemuaServis.setOnClickListener {
            binding.sheet.visibility = View.VISIBLE
            binding.tvKeterangan.text = "Semua Servis"
            loadFragment(SemuaServisFragment())
        }
        binding.cardServisSaya.setOnClickListener {
            binding.sheet.visibility = View.VISIBLE
            binding.tvKeterangan.text = "Servis Saya"
            loadFragment(ServisSayaFragment())
        }
        binding.cardDaftarKendaraan.setOnClickListener {
            binding.sheet.visibility = View.VISIBLE
            binding.tvKeterangan.text = "Daftar Kendaraan"
            loadFragment(PermintaanServisFragment())
        }
        binding.cardTentang.setOnClickListener {
            binding.sheet.visibility = View.VISIBLE
            binding.tvKeterangan.text = "Tentang"
            loadFragment(TentangFragment())
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame, fragment)
            commit()
        }
    }
}