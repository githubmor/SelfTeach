package BL

import DAL.TermDataTable
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianDateParser
import morteza.darzi.SelfTeach2.TermType

class Term(val termDataTable: TermDataTable) {

    init {
        if (daysDiffCalculate(termDataTable.startDate, termDataTable.endDate) <= 0)
            throw ArithmeticException("تاريخ هاي ترم درست تنظيم نشده")
    }
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

//    val termDateState get() = "( $dayPast/$dayCount ) روز"

    val dayCount get() = daysDiffCalculate(startDate, endDate)

    val dayPast: Int
        get() {
            val difnow_Start = daysDiffCalculate(startDate, now)
            return when {
                difnow_Start < 0 -> 0
                difnow_Start > dayCount -> dayCount
                else -> difnow_Start
            }
        }

    val dayPastPercent: Int
        get() {
            return if (dayCount > 0) {
                dayPast * 100 / dayCount
            } else {
                0
            }
        }


    private fun daysDiffCalculate(startDate: String, endDate: String): Int {

        val start = (PersianDateParser(startDate).persianDate.timeInMillis / (24 * 60 * 60 * 1000)).toInt()
        val end = (PersianDateParser(endDate).persianDate.timeInMillis / (24 * 60 * 60 * 1000)).toInt()

        return end - start
    }

    fun getCalenderActiveDaysList(): Array<PersianCalendar> {
        return Ultility.arrayOfPersianCalendars(startDate, endDate)
    }


}

