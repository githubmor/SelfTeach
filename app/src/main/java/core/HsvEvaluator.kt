package core

import android.animation.TypeEvaluator
import android.graphics.Color

/**
 * Created by M on 15/01/03.
 */
class HsvEvaluator : TypeEvaluator<Int> {


    override fun evaluate(fraction: Float, startValue: Int?, endValue: Int?): Int? {

        val startHsv = FloatArray(3)
        val endHsv = FloatArray(3)
        val currentHsv = FloatArray(3)

        Color.colorToHSV(startValue!!, startHsv)
        Color.colorToHSV(endValue!!, endHsv)

        for (i in 0..2)
            currentHsv[i] = (1 - fraction) * startHsv[i] + fraction * endHsv[i]

        while (currentHsv[0] >= 360.0f) currentHsv[0] -= 360.0f
        while (currentHsv[0] < 0.0f) currentHsv[0] += 360.0f

        return Color.HSVToColor(currentHsv)
    }
}
