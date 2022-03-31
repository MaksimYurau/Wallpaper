package com.maksimyurau.android.wallpaper.noise

class Noise(private var scale: Int, private var random: Random) {
    fun getValue(x: Int, y: Int): Float {
        val xgrid = x / scale
        val ygrid = y / scale
        val xgridNext = xgrid + 1
        val ygridNext = ygrid + 1

        val xStart = xgrid * scale
        val xEnd = xgridNext * scale
        val yStart = ygrid * scale
        val yEnd = ygridNext * scale

        val value12: Float = random.getRandomValue(xgrid, ygrid).toFloat()
        val value22: Float = random.getRandomValue(xgridNext, ygrid).toFloat()
        val value21: Float = random.getRandomValue(xgridNext, ygridNext).toFloat()
        val value11: Float = random.getRandomValue(xgrid, ygridNext).toFloat()

        val w12 =
            (xEnd - x).toFloat() * (yEnd - y) / ((xEnd - xStart) * (yEnd - yStart))
        val w22 =
            (x - xStart).toFloat() * (yEnd - y) / ((xEnd - xStart) * (yEnd - yStart))
        val w21 =
            (x - xStart).toFloat() * (y - yStart) / ((xEnd - xStart) * (yEnd - yStart))
        val w11 =
            (xEnd - x).toFloat() * (y - yStart) / ((xEnd - xStart) * (yEnd - yStart))

        return value11 * w11 + value12 * w12 + value21 * w21 + value22 * w22
    }
}