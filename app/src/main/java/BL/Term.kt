package BL

import DAL.Termdb
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianDateParser

class Term(db : Termdb) {

    var endDate = db.endDate
    var startDate = db.startDate
    var termName = db.name
    val now = PersianCalendar().persianShortDate

    fun termDateState()= "( " + DayPast() + "/" + DayCount() + " )" + " روز"

    fun DayCount()= DaysDiffCalculate(startDate, endDate)

    fun DayPast()= DaysDiffCalculate(startDate, now)

    fun DayRemind()= DayCount() - DayPast()

    fun dayPastPercent(): Int {
        return if (DayCount() > 0) {
            DayPast() * 100 / DayCount()
        } else {
            0
        }
    }

    fun isInTermRange(): Boolean {
        return if (now != "") {
            DaysDiffCalculate(startDate, now) > 0 && DaysDiffCalculate(now, endDate) > 0
        } else {
            false
        }
    }

    private fun DaysDiffCalculate(s: String, e: String): Int {

        val start = PersianDateParser(s).persianDate.timeInMillis
        val end = PersianDateParser(e).persianDate.timeInMillis

        val re = end - start

        return PersianCalendar(re).persianDay
    }

}