package BL

import DAL.Termdb
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianDateParser
import morteza.darzi.SelfTeach2.termType

class Term(val db : Termdb) {

    var endDate
        get() = db.endDate
        set(value) {
            db.endDate = value
        }

    var startDate
        get() = db.startDate
        set(value) {
            db.startDate = value
        }
    var type
        get() = termType.valueOf(db.name).typeName
        set(value) {
            db.name = value
        }
    private val now: String = PersianCalendar().persianShortDate

    val termDateState get() = "( " + dayPast + "/" + dayCount + " )" + " روز"

    val dayCount get() = daysDiffCalculate(startDate, endDate)

    val dayPast get() = daysDiffCalculate(startDate, now)

    val dayRemind get() = dayCount - dayPast

    val dayPastPercent: Int
        get() {
            return if (dayCount > 0) {
                dayPast * 100 / dayCount
            } else {
                0
            }
        }

//    fun isInTermRange(): Boolean {
//        return if (now != "") {
//            daysDiffCalculate(startDate, now) > 0 && daysDiffCalculate(now, endDate) > 0
//        } else {
//            false
//        }
//    }

    private fun daysDiffCalculate(s: String, e: String): Int {

        val start = PersianDateParser(s).persianDate.timeInMillis
        val end = PersianDateParser(e).persianDate.timeInMillis

        val re = end - start

        return ((re/(1000*60*60*24))+1).toInt()
    }

    fun getTermDaysList(): Array<PersianCalendar> {
        return Ultility.getTermabledays(startDate,endDate)
    }

}