package core

import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianDateParser
import data.TermDataTable
import morteza.darzi.SelfTeach2.TermType

class Term() {


    private var termDataTable = TermDataTable(0, "", "", "")

    constructor(termDataTable: TermDataTable) : this() {
        this.termDataTable = termDataTable
        if (endDate.isNotEmpty()&& startDate.isNotEmpty())
            if (daysDiffCalculate(termDataTable.startDate, termDataTable.endDate) <= 0)
                throw ArithmeticException("تاريخ هاي ترم درست تنظيم نشده")
    }

//    fun setDate(start: String, end: String): Boolean {
//       return daysDiffCalculate(termDataTable.startDate, termDataTable.endDate) <= 0
//    }
    var endDate
        get() = termDataTable.endDate
        set(value) {
            termDataTable.endDate = value
        }
//    var endDate get()=  termDataTable.endDate
//        set(value) {
//            termDataTable.endDate = value
//            if (startDate.isNotEmpty())
//                if (daysDiffCalculate(termDataTable.startDate, termDataTable.endDate) <= 0)
//                    throw ArithmeticException("تاريخ هاي ترم درست تنظيم نشده")
//        }
var startDate
    get() = termDataTable.startDate
    set(value) {
        termDataTable.startDate = value
    }
//    var startDate = termDataTable.startDate
//        set(value) {
//            termDataTable.startDate = value
//            if (endDate.isNotEmpty())
//                if (daysDiffCalculate(termDataTable.startDate, termDataTable.endDate) <= 0)
//                    throw ArithmeticException("تاريخ هاي ترم درست تنظيم نشده")
//        }
    var name
        get() = TermType.valueOf(termDataTable.name).typeName
        set(value) {
            termDataTable.name = value

        }

    fun isSaved() = termDataTable.id > 0
    fun getTermDataTable(): TermDataTable = termDataTable
    private val now: String = PersianCalendar().persianShortDate

    val dayCount get() = daysDiffCalculate(startDate, endDate)

    val dayPast: Int
        get() {
            val difNowToStart = daysDiffCalculate(startDate, now)
            return when {
                difNowToStart < 0 -> 0
                difNowToStart > dayCount -> dayCount
                else -> difNowToStart
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

