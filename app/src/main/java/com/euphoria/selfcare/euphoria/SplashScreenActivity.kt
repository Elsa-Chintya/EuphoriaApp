package com.euphoria.selfcare.euphoria

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {
    var animation: Animation? = null

    private var appName: TextView? = null
    private var imageView: ImageView? = null
    private var descApp: TextView? = null
    private var mottoApp: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar?.hide()
        setContentView(R.layout.activity_splash_screen)

        animation = AnimationUtils.loadAnimation(this, R.anim.animation)

        appName = findViewById(R.id.appName)
        imageView = findViewById(R.id.imageView)
        descApp = findViewById(R.id.descApp)
        mottoApp = findViewById(R.id.mottoApp)

        with(appName) { this?.setAnimation(animation) }
        with(imageView) { this?.setAnimation(animation) }
        with(descApp) { this?.setAnimation(animation) }
        with(mottoApp) { this?.setAnimation(animation) }

        Handler().postDelayed({
            val intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH.toLong())
    }

    companion object {
        private const val SPLASH = 3000
    }
}