package com.toyota.toyserv.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.toyota.toyserv.MainActivity
import com.toyota.toyserv.R
import kotlinx.android.synthetic.main.activity_splash_screen.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()

        parentSplashScreenn.animation = AnimationUtils.loadAnimation(this, R.anim.load)

        GlobalScope.launch(context = Dispatchers.Main) {
            delay(2000)
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            overridePendingTransition(R.anim.splashscreen_fade_in, R.anim.splashscreen_fade_out)
            finish()
        }
    }
}