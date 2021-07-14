package com.toyota.toyserv.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.toyota.toyserv.R
import com.toyota.toyserv.databinding.ActivityMainClientBinding
import com.toyota.toyserv.ui.fragment.RiwayatFragment
import com.toyota.toyserv.ui.fragment.ServiceBeratFragment
import com.toyota.toyserv.ui.fragment.ServiceRinganFragment
import com.toyota.toyserv.ui.fragment.TentangFragment

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

        binding.cardServisRingan.setOnClickListener {
            binding.sheet.visibility = View.VISIBLE
            binding.tvKeterangan.text = "Servis Ringan"
            loadFragment(ServiceRinganFragment())
        }
        binding.cardServisBerat.setOnClickListener {
            binding.sheet.visibility = View.VISIBLE
            binding.tvKeterangan.text = "Servis Berat"
            loadFragment(ServiceBeratFragment())
        }
        binding.cardRiwayat.setOnClickListener {
            binding.sheet.visibility = View.VISIBLE
            binding.tvKeterangan.text = "Riwayat"
            loadFragment(RiwayatFragment())
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