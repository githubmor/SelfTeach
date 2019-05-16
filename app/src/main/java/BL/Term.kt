package BL

import DAL.Termdb
import android.text.Html

class Term(db : Termdb) {

    var endDate = db.endDate
    var startDate = db.startDate
    var termName = db.name


    fun termDateState(now : String): String {
        return "( " + DayPast(now) + "/" + DayCount() + " )"
    }
    fun DayCount(): Int {
        return DaysDiffCalculate(startDate, endDate)
    }

    fun DayPast(now : String): Int {
        return DaysDiffCalculate(startDate, now)
    }

    fun DayRemind(now : String): Int {
        return DayCount() - DayPast(now)
    }

    fun dayPastPercent(now : String): Int {
        return if (DayCount() > 0) {
            DayPast(now) * 100 / DayCount()
        } else {
            0
        }
    }
    fun isInTermRange(now : String): Boolean {
        return if (now != "") {
            DaysDiffCalculate(startDate, now) > 0 && DaysDiffCalculate(now, endDate) > 0
        } else {
            false
        }
    }

    private fun DaysDiffCalculate(StartDate: String, EndDate: String): Int {

        val Start = JDF()
        val End = JDF()

        Start.iranianDate = StartDate
        End.iranianDate = EndDate

        return End.dayCount - Start.dayCount + 1
    }

}