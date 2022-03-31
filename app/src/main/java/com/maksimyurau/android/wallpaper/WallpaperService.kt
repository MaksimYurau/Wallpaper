package com.maksimyurau.android.wallpaper

import android.graphics.Canvas
import android.graphics.Color
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder

class WallpaperService : WallpaperService() {
    override fun onCreateEngine(): Engine {
        return WallpaperEngine()
    }

    internal inner class WallpaperEngine : Engine() {

        override fun onSurfaceRedrawNeeded(holder: SurfaceHolder) {
            super.onSurfaceRedrawNeeded(holder)
            val canvas: Canvas = holder.lockCanvas()
            canvas.drawColor(Color.RED)
            holder.unlockCanvasAndPost(canvas)
        }
    }
}