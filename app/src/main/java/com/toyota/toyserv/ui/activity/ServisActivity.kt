package com.toyota.toyserv.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.toyota.toyserv.R
import com.toyota.toyserv.databinding.ActivityServisBinding
import com.toyota.toyserv.ui.fragment.ServisFragment

class ServisActivity : AppCompatActivity() {
    private lateinit var binding: ActivityServisBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServisBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        loadFragment(ServisFragment())
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame, fragment)
            commit()
        }
    }

}