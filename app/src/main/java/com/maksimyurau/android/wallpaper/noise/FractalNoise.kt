package com.maksimyurau.android.wallpaper.noise

import java.util.ArrayList

class FractalNoise(baseScale: Int, random: Random, octavesCount: Int) {
    private var octaves = ArrayList<Noise>()
    fun getValue(x: Int, y: Int): Float {
        var sum = 0f
        var fraction = 0.5f
        for (noise in octaves) {
            sum += noise.getValue(x, y) * fraction
            fraction *= 0.5f
        }
        return sum
    }

    init {
        var scale = baseScale
        for (i in 0 until octavesCount) {
            octaves.add(Noise(scale, random))
            scale /= 2
        }
    }
}