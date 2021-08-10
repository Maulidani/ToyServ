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
import com.toyota.toyserv.model.DataResponse
import com.toyota.toyserv.network.ApiClient
import com.toyota.toyserv.ui.activity.LoginActivity
import com.toyota.toyserv.ui.fragment.AkunFragment
import com.toyota.toyserv.ui.fragment.PermintaanServisFragment
import com.toyota.toyserv.ui.fragment.ServisFragment
import com.toyota.toyserv.ui.fragment.ServisSayaFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
                binding.imgLogout.setOnClickListener {
                    logout(id)
                }
            }
            "customer_service" -> {
                customerService()
                binding.imgLogout.setOnClickListener {
                    sharedPref.logout()
                    Toast.makeText(this@MainActivity, "Keluar", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(Intent(this@MainActivity, LoginActivity::class.java)))
                    finish()
                }
            }
            "admin" -> {
                admin()
                binding.imgLogout.setOnClickListener {
                    sharedPref.logout()
                    Toast.makeText(this@MainActivity, "Keluar", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(Intent(this@MainActivity, LoginActivity::class.java)))
                    finish()
                }
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
        binding.imgCardTwo.setImageResource(R.drawable.ic_akun)
        binding.cardTwo.setOnClickListener {
            binding.sheet.visibility = View.VISIBLE
            binding.tvInfo.text = "Akun"
            loadFragment(AkunFragment())
        }
    }

    private fun logout(id: String?) {
        ApiClient.instances.logout(id!!).enqueue(object : Callback<DataResponse> {
            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                val value = response.body()?.value
                val message = response.body()?.message

                if (response.isSuccessful && value == "1") {
                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()

                    sharedPref.logout()
                    Toast.makeText(this@MainActivity, "Keluar", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(Intent(this@MainActivity, LoginActivity::class.java)))
                    finish()
                } else {
                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message.toString(), Toast.LENGTH_SHORT).show()
            }

        })
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