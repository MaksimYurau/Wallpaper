package com.maksimyurau.android.wallpaper.noise

class Random(lineWidth: Int, size: Int) {
    private var table: IntArray = IntArray(size)
    private var lineWidth: Int
    fun getRandomValue(x: Int, y: Int): Int {
        return table[(x + y * lineWidth) % table.size]
    }

    init {
        for (i in 0 until size) {
            table[i] = if (Math.random() >= 0.5) 1 else 0
        }
        this.lineWidth = lineWidth
    }
}