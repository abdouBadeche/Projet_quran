package com.example.quran.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quran.MainActivity
import com.example.quran.R
import kotlinx.android.synthetic.main.splash_screen.*


class SplashScreenActivity : AppCompatActivity() {

    // This is the loading time of the splash screen
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        button.setOnClickListener{
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}