package BL

import DAL.BookReadsdb

class Book(val dbDto: BookReadsdb) {

    //يك اصل رو رعايت كن . اينجا فقط محاسبات عددي تصميمي نداريم


    val pageCount = dbDto.book.pageCount
    val name = dbDto.book.name
    val priority = dbDto.book.priority

    fun pageWasReaded(): Int {
        return dbDto.reads.sumBy { it.pageRead }
    }

    fun pageReadState(): String {
        return "( " + pageWasReaded() + "/" +  pageCount + " )" + " صفحه"
    }

    fun pageReadPercent(): Int {
        return ((pageWasReaded()*100)/ pageCount)
    }

    private fun pageShouldReadTillToday(termDayPas: Int, termDayCount: Int) =
            termDayPas * avgPagePerDay(termDayCount)

    fun performance(termDayPas : Int, termDayCount : Int): Int {
        val pageShouldRead = pageShouldReadTillToday(termDayPas, termDayCount)
        return if (pageShouldRead > 0) pageWasReaded() * 100 / pageShouldRead else 0
    }

    fun pageRemindToGet100Percent(termDayPas : Int, termDayCount : Int)=
            pageShouldReadTillToday(termDayPas, termDayCount) - pageWasReaded()


    fun avgPagePerEveryRead(): Int
        = when {
            dbDto.reads.isNotEmpty() -> pageWasReaded() / dbDto.reads.size
            else -> 0
        }


    private fun avgPagePerDay(termDayCount : Int): Int
        = when {
            termDayCount > 0 -> pageCount / termDayCount
            else -> 0
        }


}