package com.toyota.toyserv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.toyota.toyserv.databinding.ActivityMainBinding
import com.toyota.toyserv.ui.fragment.ServisFragment
import com.toyota.toyserv.ui.fragment.ServisSayaFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.sheet.visibility = View.INVISIBLE
        BottomSheetBehavior.from(binding.sheet).apply {
            peekHeight = 100
            this.state = BottomSheetBehavior.STATE_DRAGGING
        }


        //test condition
        val customer = true
        val customerservice = false
        val admin = false

        when {
            customer -> {
                customer()
            }
            customerservice -> {
                customerService()
            }
            admin -> {
                admin()
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame, fragment)
            commit()
        }
    }

    private fun customer() {
        binding.tvCardOne.text = "Servis"
        binding.cardOne.setOnClickListener {
            binding.sheet.visibility = View.VISIBLE
            binding.tvInfo.text = "Servis"
            loadFragment(ServisFragment())
        }

        binding.tvCardTwo.text = "Servis Saya"
        binding.cardTwo.setOnClickListener {
            binding.sheet.visibility = View.VISIBLE
            binding.tvInfo.text = "Servis Saya"
            loadFragment(ServisSayaFragment())
        }
    }

    private fun customerService() {
        binding.tvCardOne.text = "Permintaan Servis"
        binding.cardOne.setOnClickListener {
            binding.sheet.visibility = View.VISIBLE
            binding.tvInfo.text = "Permintaan Servis"
            loadFragment(ServisFragment())
        }

        binding.tvCardTwo.text = "Servis Saya"
        binding.cardTwo.setOnClickListener {
            binding.sheet.visibility = View.VISIBLE
            binding.tvInfo.text = "Servis Saya"
            loadFragment(ServisSayaFragment())
        }
    }

    private fun admin() {
        binding.tvCardOne.text = "Permintaan Servis"
        binding.cardOne.setOnClickListener {
            binding.sheet.visibility = View.VISIBLE
            binding.tvInfo.text = "Permintaan Servis"
            loadFragment(ServisFragment())
        }

        binding.tvCardTwo.text = "Akun"
        binding.cardTwo.setOnClickListener {
            binding.sheet.visibility = View.VISIBLE
            binding.tvInfo.text = "Akun"
            loadFragment(ServisSayaFragment())
        }
    }
}