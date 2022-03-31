package com.maksimyurau.android.wallpaper

import com.maksimyurau.android.wallpaper.noise.FractalNoise

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import com.maksimyurau.android.wallpaper.noise.Random

class Drawer {
    private var bitmap: Bitmap? = null
    private var paint = Paint()
    private lateinit var pixels: IntArray
    private var shift = 0
    private var noise: FractalNoise? = null

    fun draw(canvas: Canvas) {
        if (!initFrame(canvas)) {
            bitmap!!.getPixels(
                pixels, 0, canvas.width, 1, 0,
                canvas.width - 1, canvas.height
            )
            for (i in 0 until canvas.height) {
                val pixelIndex = i * canvas.width + canvas.width - 1
                var value = 0xff and (noise!!.getValue(canvas.width - 1 + shift, i) * 255).toInt()
                value = (value + 64 * (i.toFloat() / canvas.height)).coerceAtMost(255f).toInt()
                pixels[pixelIndex] = -0x1000000 or (value shl 16) or (value shl 8) or 255
            }
            bitmap!!.setPixels(
                pixels, 0, canvas.width, 0, 0,
                canvas.width, canvas.height
            )
            canvas.drawBitmap(bitmap!!, 0f, 0f, paint)
            shift += 1
        }
    }

    private fun initFrame(canvas: Canvas?): Boolean {
        if (bitmap == null || bitmap!!.width != canvas?.width || bitmap!!.height != canvas.height || bitmap!!.isRecycled
        ) {
            if (bitmap != null && !bitmap!!.isRecycled) {
                bitmap!!.recycle()
            }
            bitmap = canvas?.width?.let {
                Bitmap.createBitmap(
                    it,
                    canvas.height,
                    Bitmap.Config.ARGB_8888
                )
            }
            pixels = canvas?.width?.times(canvas.height)?.let { IntArray(it) }!!
            noise = FractalNoise(512, Random(canvas.width, 1000), 9)
            var pixelIndex = 0
            for (i in 0 until canvas.height) {
                for (j in 0 until canvas.width) {
                    var value = 0xff and (noise!!.getValue(j + shift, i) * 255).toInt()
                    value =
                        (value + 64 * (i.toFloat() / canvas.height)).coerceAtMost(255f).toInt()
                    pixels[pixelIndex++] = -0x1000000 or (value shl 16) or (value shl 8) or 255
                }
            }
            canvas.width.let {
                bitmap?.setPixels(
                    pixels, 0, canvas.width, 0, 0,
                    it, canvas.height
                )
            }
            bitmap?.let { canvas.drawBitmap(it, 0.0f, 0.0f, paint) }
            return true
        }
        return false
    }
}