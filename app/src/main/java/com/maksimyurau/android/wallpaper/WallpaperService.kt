package com.maksimyurau.android.wallpaper

import android.os.Handler
import android.service.wallpaper.WallpaperService
import android.util.Log
import android.view.SurfaceHolder

class WallpaperService : WallpaperService() {
    override fun onCreateEngine(): Engine {
        return WallpaperEngine()
    }

    internal inner class WallpaperEngine : Engine() {
        private var holder: SurfaceHolder? = null
        var handler: Handler? = null
        private var drawer = Drawer()

        private var redrawRunnable: Runnable = object : Runnable {
            override fun run() {
                draw()
                handler?.postDelayed(this, 10)
            }
        }

        private fun draw() {
            if (surfaceHolder != null) {
                try {
                    val canvas = surfaceHolder!!.lockCanvas()
                    if (canvas != null) {
                        drawer.draw(canvas)
                    }
                    surfaceHolder!!.unlockCanvasAndPost(canvas)
                } catch (exception: Exception) {
                    Log.e("Wallpaper", exception.message, exception)
                }
            }
        }

        override fun onCreate(surfaceHolder: SurfaceHolder) {
            super.onCreate(surfaceHolder)
            holder = surfaceHolder
            handler = Handler()
        }

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
            handler?.removeCallbacks(redrawRunnable)
            if (visible) {
                handler?.post(redrawRunnable)
            }
        }

        override fun onSurfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            super.onSurfaceChanged(holder, format, width, height)
            this.holder = holder
        }

        override fun onSurfaceRedrawNeeded(holder: SurfaceHolder) {
            super.onSurfaceRedrawNeeded(holder)
            this.holder = holder
            draw()
        }

        override fun onSurfaceCreated(holder: SurfaceHolder) {
            super.onSurfaceCreated(holder)
            this.holder = holder
            handler?.post(redrawRunnable)
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder) {
            super.onSurfaceDestroyed(holder)
            handler?.removeCallbacks(redrawRunnable)
        }
    }
}