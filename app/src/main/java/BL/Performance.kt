package BL

import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendarConstants
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianDateParser

class Performance(private val term: Term,private val books : List<PerformanceBook>){

    private val pageWasRead get() = books.sumBy { it.pageWasReaded }

    private val pageCount get() = books.sumBy { it.pageCount }

    private val pageShouldReadTillToday get() = term.dayPast * avgPagePerDay

    val pagePerDayRemind get() = term.dayRemind * avgPagePerDay

    val avgBookReadPerDay: Float
        get() {
            val bookCountPerDay: MutableList<Int> = mutableListOf()
            for (weekdayName in PersianCalendarConstants.persianWeekDays) {
                bookCountPerDay.add(books.count { it ->
                    it.book.dbDto.reads.any { dot ->
                        PersianDateParser(dot.readDate).persianDate.persianWeekDayName == weekdayName
                    }
                })
            }
            return bookCountPerDay.average().toFloat()
        }

    val pageTo100Percent: Float
        get() {
            return pageShouldReadTillToday - pageWasRead
        }

//    private val avgPagePerEveryRead: Int
//        get() {
//            val c = books.sumBy { it.dbDto.reads.count() }
//            return if (c > 0) {
//                pageWasRead / c
//            } else 0
//        }

    private val avgPagePerDay: Float
        get() {
            return if (term.dayCount > 0) {
                pageCount / term.dayCount.toFloat()
            } else {
                0F
            }
        }

    val performance: Float
        get() {
            return if (term.dayPast > 0 && avgPagePerDay > 0) {
                pageWasRead * 100 / pageShouldReadTillToday
            } else {
                0F
            }
        }

    fun readList(): List<String> {


        val needToRead = getBookHasPageToReadToday()

        val books = sortBookByPriorityAndPageNeedToRead(needToRead)

        val avgBookRead = avgBookReadPerDay
        val pageTo100 = pageTo100Percent

        val re : MutableList<String> = mutableListOf()
        var sum = 0
        for (b in books) {
            if (sum <= pageTo100 || re.size <= avgBookRead) {
                sum += b.biggestPageCanRead
                re.add(b.name + " - " + b.biggestPageCanRead)
            }else
                return re
        }

        return re

    }

    private fun sortBookByPriorityAndPageNeedToRead(needToRead: List<PerformanceBook>): List<PerformanceBook> {
        val bookWithHighPriorityAndHightPageToRead = needToRead
                .sortedWith(
                        compareBy(
                                { it.priority },
                                { it.pageRemindToGet100Percent }
                        ))
        return bookWithHighPriorityAndHightPageToRead
    }

    private fun getBookHasPageToReadToday(): List<PerformanceBook> {
        val needToRead = books.filter { it.pageRemindToGet100Percent > 0 }
        return needToRead
    }


}
