@file:Suppress("DEPRECATION")

package com.toyota.toyserv.ui.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import apotekku.projectapotekku.utils.Constant
import apotekku.projectapotekku.utils.PreferencesHelper
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging
import com.toyota.toyserv.MainActivity
import com.toyota.toyserv.databinding.ActivityLoginBinding
import com.toyota.toyserv.model.DataResponse
import com.toyota.toyserv.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var sharedPref: PreferencesHelper
    private lateinit var binding: ActivityLoginBinding
    private val CHANNEL_ID = "101"
    private lateinit var token: String

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()
        sharedPref = PreferencesHelper(this)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Loading")
        progressDialog.setMessage("Masuk...")
        progressDialog.setCancelable(false)

        binding.btnLogin.setOnClickListener {
            progressDialog.show()

            val username = binding.inputUsername.text.toString()
            val password = binding.inputPassword.text.toString()
            val type = "customer"

            when {
                username.isEmpty() -> {
                    binding.inputUsername.error = "masukkan username"
                }
                password.isEmpty() -> {
                    binding.inputPassword.error = "masukkan password"
                }
                else -> {
                    login(type, username, password)
                }
            }
        }

        binding.tvLoginCsAdmin.setOnClickListener {
            startActivity(Intent(this, LoginCsAdminActivity::class.java))
        }

        createNotificationChannel()
        getToken()
    }

    private fun login(type: String, username: String, password: String) {

        ApiClient.instances.login(type, username, password)
            .enqueue(object : Callback<DataResponse> {
                override fun onResponse(
                    call: Call<DataResponse>,
                    response: Response<DataResponse>
                ) {
                    val value = response.body()?.value
                    val message = response.body()?.message

                    if (response.isSuccessful && value == "1") {
                        val type = response.body()?.type
                        val id = response.body()?.id
                        val nameLogin = response.body()?.name

                        registerToken(id, type, token, nameLogin)
                        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT)
                            .show()
                        progressDialog.dismiss()
                    }
                }

                override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                    Toast.makeText(
                        this@LoginActivity,
                        t.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    progressDialog.dismiss()
                }
            })
    }

    private fun saveSession(id: String?, type: String?, nameLogin: String?) {

        sharedPref.put(Constant.PREF_IS_LOGIN_ID, id.toString())
        sharedPref.put(Constant.PREF_IS_LOGIN_TYPE, type.toString())
        sharedPref.put(Constant.PREF_IS_LOGIN_NAME, nameLogin.toString())
        sharedPref.put(Constant.PREF_IS_LOGIN, true)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
        progressDialog.dismiss()

        val name: String? = sharedPref.getString(Constant.PREF_IS_LOGIN_NAME)

        Toast.makeText(this, "name  : ${name.toString()}", Toast.LENGTH_SHORT).show()
    }

    private fun registerToken(id: String?, type: String?, token: String, nameLogin: String?) {
        ApiClient.instances.registerToken(id.toString(), token)
            .enqueue(object : Callback<DataResponse> {
                override fun onResponse(
                    call: Call<DataResponse>,
                    response: Response<DataResponse>
                ) {
                    val value = response.body()?.value
                    val message = response.body()?.message

                    if (token != null) {
                        if (response.isSuccessful && value == "1") {

                            saveSession(id, type, nameLogin)

                            Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT)
                                .show()

                        } else {
                            Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT)
                                .show()
                            progressDialog.dismiss()
                        }
                    } else {
                        getToken()
                        progressDialog.dismiss()
                    }
                }

                override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, t.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                    progressDialog.dismiss()
                }

            })
    }

    //get the app token
    private fun getToken() {
        FirebaseInstallations.getInstance().getToken(true).addOnCompleteListener {
            val myToken = it.result!!.token
            Log.e("getToken1: ", myToken)
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            token = task.result.toString()
        })
    }

    //create a notification channel
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = "Channel_Name"
            val descriptionText = "Channel_Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.description = descriptionText
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    override fun onResume() {
        super.onResume()
        if (sharedPref.getBoolean(Constant.PREF_IS_LOGIN)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
        }
    }
}