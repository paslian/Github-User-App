package com.example.navigationapi_pasliansahatrafael.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import com.example.navigationapi_pasliansahatrafael.R

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val splashLogo = findViewById<View>(R.id.splash_logo)

        splashLogo.alpha = 0f
        splashLogo.animate().setDuration(ANIMATION_DURATION.toLong()).alpha(1f).withEndAction{
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }

    }

    companion object {
        const val ANIMATION_DURATION = 1500
    }
}