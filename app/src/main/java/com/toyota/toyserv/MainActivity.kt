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
import com.toyota.toyserv.ui.activity.*
import com.toyota.toyserv.ui.fragment.*
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

        val name = sharedPref.getString(Constant.PREF_IS_LOGIN_NAME)
        val type = sharedPref.getString(Constant.PREF_IS_LOGIN_TYPE)
        val id = sharedPref.getString(Constant.PREF_IS_LOGIN_ID)

//        Toast.makeText(this, "name  : ${name.toString()}", Toast.LENGTH_SHORT).show()

        binding.tvNameProfile.text = name
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
                binding.cardThree.visibility = View.VISIBLE
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

    private fun customer() {
        binding.tvCardOne.text = "Servis"
        binding.cardOne.setOnClickListener {
            startActivity(Intent(this, ServisActivity::class.java))
        }

        binding.tvCardTwo.text = "Servis Saya"
        binding.cardTwo.setOnClickListener {
            startActivity(Intent(this, ServisSayaActivity::class.java))
        }
    }

    private fun customerService() {
        binding.cardTwo.visibility = View.INVISIBLE
        binding.tvCardOne.text = "Permintaan Servis"
        binding.cardOne.setOnClickListener {
            startActivity(Intent(this, PermintaanActivity::class.java))
        }

    }

    private fun admin() {
        binding.tvCardOne.text = "Permintaan Servis"
        binding.cardOne.setOnClickListener {
            startActivity(Intent(this, PermintaanActivity::class.java))
        }

        binding.tvCardTwo.text = "Akun"
        binding.imgCardTwo.setImageResource(R.drawable.ic_akun)
        binding.cardTwo.setOnClickListener {
            startActivity(Intent(this, AkunActivity::class.java))
        }

        binding.cardThree.setOnClickListener {
            startActivity(Intent(this, NotificationActivity::class.java))
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