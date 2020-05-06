package BL

import android.graphics.Color
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import morteza.darzi.SelfTeach2.TermType

class Ultility {
    companion object {
        fun getColorToGreen(power: Double): Int {
            val H = power * 80
            val floats = FloatArray(3)
            floats[0] = H.toFloat()
            floats[1] = 1.toFloat()
            floats[2] = 0.7.toFloat()


            return Color.HSVToColor(floats)
        }

        fun getStartDate(type: TermType): String {
            val year = PersianCalendar().persianYear
            return when (type) {
                TermType.NimsalAvl -> PersianCalendar().apply { setPersianDate(year, 6, 1) }.persianShortDate
                TermType.NimsalDovom -> PersianCalendar().apply { setPersianDate(year, 10, 1) }.persianShortDate
                TermType.TermTabestan -> PersianCalendar().apply { setPersianDate(year, 3, 1) }.persianShortDate
                TermType.TermManual -> ""
            }
        }

        fun getEndDate(type: TermType): String {
            val year = PersianCalendar().persianYear
            return when (type) {
                TermType.NimsalAvl -> PersianCalendar().apply { setPersianDate(year, 9, 30) }.persianShortDate
                TermType.NimsalDovom -> PersianCalendar().apply { setPersianDate(year, 2, 31) }.persianShortDate
                TermType.TermTabestan -> PersianCalendar().apply { setPersianDate(year, 5, 31) }.persianShortDate
                TermType.TermManual -> ""
            }
        }

        fun arrayOfPersianCalendars(startDate: String, endDate: String): Array<PersianCalendar> {
            val re = mutableListOf<PersianCalendar>()
            val start = PersianCalendar().apply { parse(startDate) }.timeInMillis
            val end = PersianCalendar().apply { parse(endDate) }.timeInMillis

            for (b in start..end step (1000 * 60 * 60 * 24)) {
                re.add(PersianCalendar(b))
            }

            return re.toTypedArray()

        }
    }
}