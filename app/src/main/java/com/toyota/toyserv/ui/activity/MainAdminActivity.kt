package com.toyota.toyserv.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.toyota.toyserv.R
import com.toyota.toyserv.databinding.ActivityMainAdminBinding
import com.toyota.toyserv.ui.fragment.AkunFragment
import com.toyota.toyserv.ui.fragment.PermintaanServisFragment

class MainAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainAdminBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.sheet.visibility = View.INVISIBLE
        BottomSheetBehavior.from(binding.sheet).apply {
            peekHeight = 100
            this.state = BottomSheetBehavior.STATE_DRAGGING
        }

        binding.cardPermintaanServis.setOnClickListener {
            binding.sheet.visibility = View.VISIBLE
            binding.tvKeterangan.text = "Permintaan Servis"
            loadFragment(PermintaanServisFragment())
        }
        binding.cardAkun.setOnClickListener {
            binding.sheet.visibility = View.VISIBLE
            binding.tvKeterangan.text = "Akun"
            loadFragment(AkunFragment())
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame, fragment)
            commit()
        }
    }
}