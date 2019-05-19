package BL

import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianDateParser

class Performance(val term: Term, val books : List<Book>){

    private fun PageWasRead() = books.sumBy { it.pageWasReaded() }

    private fun PageCount()= books.sumBy { it.pageCount }

    private fun pageShouldReadTillToday() = term.DayPast() * avgPagePerDay()

    fun pagePerDayRemind()= term.DayRemind() * avgPagePerDay()

    fun bookReadedToday(): List<String> {
        val weekDay = PersianCalendar().persianWeekDayName
        return  books.filter { it.dbDto.reads.any { it1 -> PersianDateParser( it1.readDate).persianDate.persianWeekDayName==weekDay } }
                .map { it.name }
    }

    fun pageTo100Percent(): Int {
        return pageShouldReadTillToday() - PageWasRead()
    }

    private fun avgPagePerEveryRead(): Int {
        val c =books.sumBy { it.dbDto.reads.count() }
        return if (c>0) {
            PageWasRead() / c
        } else 0
    }

    private fun avgPagePerDay(): Int {
        return if (term.DayCount() > 0) {
            PageCount() / term.DayCount()
        } else {
            0
        }
    }

    fun performance(): Int {
        return if (term.DayPast() > 0 && avgPagePerDay() > 0) {
            PageWasRead() * 100 / pageShouldReadTillToday()
        } else {
            0
        }
    }

    fun readList(): List<String> {

        val bookCountReadToday = bookReadedToday().count()

        val needToRead = books.filter { it.pageRemindToGet100Percent(term.DayPast(),term.DayCount())>0 }

        val bookWithHighPriorityAndHightPageToRead = needToRead
                .sortedWith(
                        compareBy(
                                { it.priority },
                                { it.pageRemindToGet100Percent(term.DayPast(),term.DayCount()) }
                        ))
                .takeLast(bookCountReadToday)
        val booksNeedToRead : MutableList<String> = mutableListOf()
        var sum = 0
        for (book in bookWithHighPriorityAndHightPageToRead) {
            if (sum <= pageTo100Percent()) {
                sum += book.avgPagePerEveryRead()
                booksNeedToRead.add(book.name + " - " + book.avgPagePerEveryRead())
            }
        }
        return booksNeedToRead

    }


}
