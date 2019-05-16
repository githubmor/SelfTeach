package BL

import android.graphics.Color

class Ultility{
    companion object {
        fun getColorToGreen(power: Double): Int {
            val H = power * 80
            val floats = FloatArray(3)
            floats[0] = H.toFloat()
            floats[1] = 1.toFloat()
            floats[2] = 0.7.toFloat()


            return Color.HSVToColor(floats)
        }
    }
}