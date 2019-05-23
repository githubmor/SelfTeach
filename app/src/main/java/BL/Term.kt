package BL

import DAL.Termdb
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianDateParser
import morteza.darzi.SelfTeach.termType

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

    fun termDateState()= "( " + dayPast() + "/" + dayCount() + " )" + " روز"

    fun dayCount()= DaysDiffCalculate(startDate, endDate)

    fun dayPast()= DaysDiffCalculate(startDate, now)

    fun dayRemind()= dayCount() - dayPast()

    fun dayPastPercent(): Int {
        return if (dayCount() > 0) {
            dayPast() * 100 / dayCount()
        } else {
            0
        }
    }

//    fun isInTermRange(): Boolean {
//        return if (now != "") {
//            DaysDiffCalculate(startDate, now) > 0 && DaysDiffCalculate(now, endDate) > 0
//        } else {
//            false
//        }
//    }

    private fun DaysDiffCalculate(s: String, e: String): Int {

        val start = PersianDateParser(s).persianDate.timeInMillis
        val end = PersianDateParser(e).persianDate.timeInMillis

        val re = end - start

        return (re/(1000*60*60*24)).toInt()+1
    }

    fun getTermDaysList(): Array<PersianCalendar> {
        val re = mutableListOf<PersianCalendar>()
        val start = PersianDateParser(startDate).persianDate.timeInMillis
        val end = PersianDateParser(endDate).persianDate.timeInMillis

        for (b in start..end step (1000*60*60*24)){
            re.add(PersianCalendar(b))
        }

        return re.toTypedArray()
    }

}