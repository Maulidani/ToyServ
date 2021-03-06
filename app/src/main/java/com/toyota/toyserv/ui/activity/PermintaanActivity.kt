package com.toyota.toyserv.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.toyota.toyserv.R
import com.toyota.toyserv.databinding.ActivityPermintaanBinding
import com.toyota.toyserv.databinding.ActivityServisBinding
import com.toyota.toyserv.ui.fragment.PermintaanServisFragment

class PermintaanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPermintaanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPermintaanBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        loadFragment(PermintaanServisFragment())

        supportActionBar?.title = "Permintaan Servis"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame, fragment)
            commit()
        }
    }

}