package BL

import DAL.Termdb

class Term(db : Termdb) {
    fun DayCount(): Int {
        return DaysDiffCalculate(startDate, endDate)
    }


    fun DayPast(now : String): Int {
        return if isInTermRange(now) DaysDiffCalculate(startDate, now)
    }

    fun DayRemind(now : String): Int {
        return DayCount() - DayPast(now)
    }

    private fun DaysDiffCalculate(StartDate: String, EndDate: String): Int {

        val Start = JDF()
        val End = JDF()

        Start.iranianDate = StartDate
        End.iranianDate = EndDate

        return End.dayCount - Start.dayCount + 1
    }
    fun isInTermRange(now: String): Boolean? {
        return if (now != "") {
            DaysDiffCalculate(startDate, now) > 0 && DaysDiffCalculate(now, endDate) > 0
        } else {
            false
        }
    }
    var endDate = db.endDate
    var startDate = db.startDate
    var termName = db.name
}