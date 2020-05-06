package BL

import DAL.TermDataTable
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianDateParser
import morteza.darzi.SelfTeach2.TermType

class Term(val termDataTable: TermDataTable) {

    var endDate
        get() = termDataTable.endDate
        set(value) {
            termDataTable.endDate = value

        }

    var startDate
        get() = termDataTable.startDate
        set(value) {
            termDataTable.startDate = value

        }
    var type
        get() = TermType.valueOf(termDataTable.name).typeName
        set(value) {
            termDataTable.name = value

        }
    private val now: String = PersianCalendar().persianShortDate

    val termDateState get() = "( $dayPast/$dayCount ) روز"

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


    private fun daysDiffCalculate(startDate: String, endDate: String): Int {

        val start = PersianDateParser(startDate).persianDate.timeInMillis
        val end = PersianDateParser(endDate).persianDate.timeInMillis

        val re = end - start

        return ((re / (1000 * 60 * 60 * 24)) + 1).toInt()
    }

    fun getCalenderActiveDaysList(): Array<PersianCalendar> {
        return Ultility.arrayOfPersianCalendars(startDate, endDate)
    }

}