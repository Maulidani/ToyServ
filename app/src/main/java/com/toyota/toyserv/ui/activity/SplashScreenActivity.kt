package com.toyota.toyserv.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import com.toyota.toyserv.MainActivity
import com.toyota.toyserv.R
import com.toyota.toyserv.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()

        binding.parentSplashScreenn.animation = AnimationUtils.loadAnimation(this, R.anim.load)

        GlobalScope.launch(context = Dispatchers.Main) {
            delay(2000)
            startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
            overridePendingTransition(R.anim.splashscreen_fade_in, R.anim.splashscreen_fade_out)
            finish()
        }

        getDate()
    }


    private lateinit var dateTime: Date
    private lateinit var dateFormat: DateFormat

    @SuppressLint("SimpleDateFormat")
    private fun getDate() {
        dateTime = Date()
        dateFormat = SimpleDateFormat("yyyy-MM-dd")

        Log.e("getDate: ", dateFormat.format(dateTime).toString())
    }
}