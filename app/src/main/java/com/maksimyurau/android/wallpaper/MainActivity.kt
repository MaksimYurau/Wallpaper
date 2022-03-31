package com.maksimyurau.android.wallpaper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.WallpaperManager

import android.content.Intent
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.choose_wallpaper).setOnClickListener {
            val intent = Intent()
            intent.action = WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER
            startActivity(intent)
        }
    }
}