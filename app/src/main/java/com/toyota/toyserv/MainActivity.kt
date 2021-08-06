package com.toyota.toyserv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import apotekku.projectapotekku.utils.Constant
import apotekku.projectapotekku.utils.PreferencesHelper
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.toyota.toyserv.databinding.ActivityMainBinding
import com.toyota.toyserv.ui.activity.LoginActivity
import com.toyota.toyserv.ui.fragment.AkunFragment
import com.toyota.toyserv.ui.fragment.PermintaanServisFragment
import com.toyota.toyserv.ui.fragment.ServisFragment
import com.toyota.toyserv.ui.fragment.ServisSayaFragment

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPref: PreferencesHelper
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()
        sharedPref = PreferencesHelper(this)

        binding.sheet.visibility = View.INVISIBLE
        BottomSheetBehavior.from(binding.sheet).apply {
            peekHeight = 100
            this.state = BottomSheetBehavior.STATE_DRAGGING
        }

        val type = sharedPref.getString(Constant.PREF_IS_LOGIN_TYPE)
        val id = sharedPref.getString(Constant.PREF_IS_LOGIN_ID)

        Toast.makeText(this, "id  : ${id.toString()}", Toast.LENGTH_SHORT).show()

        binding.tvTypeName.text = type

        when (type) {
            "customer" -> {
                customer()
            }
            "customer_service" -> {
                customerService()
            }
            "admin" -> {
                admin()
            }
        }

        binding.imgLogout.setOnClickListener {
            sharedPref.logout()
            Toast.makeText(this, "Keluar", Toast.LENGTH_SHORT).show()
            startActivity(Intent(Intent(this, LoginActivity::class.java)))
            finish()
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
            loadFragment(PermintaanServisFragment())
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
            loadFragment(PermintaanServisFragment())
        }

        binding.tvCardTwo.text = "Akun"
        binding.cardTwo.setOnClickListener {
            binding.sheet.visibility = View.VISIBLE
            binding.tvInfo.text = "Akun"
            loadFragment(AkunFragment())
        }
    }

    override fun onResume() {
        super.onResume()
        if (sharedPref.getBoolean(Constant.PREF_IS_LOGIN)) {
            Toast.makeText(this, "Sudah Login", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Belum Login", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}