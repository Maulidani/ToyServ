package com.toyota.toyserv.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.toyota.toyserv.R
import com.toyota.toyserv.databinding.ActivityAkunBinding
import com.toyota.toyserv.databinding.ActivityServisBinding
import com.toyota.toyserv.ui.fragment.AkunFragment

class AkunActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAkunBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAkunBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        loadFragment(AkunFragment())
        supportActionBar?.title = "Akun"
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