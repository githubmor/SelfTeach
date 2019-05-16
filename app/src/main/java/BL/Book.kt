package BL

import DAL.BookReadsdb

class Book(val dbDto: BookReadsdb) {

    val pageCount = dbDto.book.pageCount
    val name = dbDto.book.name

    fun PageReadPercent(): Int {
        return ((PageWasReaded()*100)/ pageCount)
    }

    fun BookPerformancePercent(termDayPas : Int, termDayCount : Int): Int {
        return if (termDayPas > 0 && avgPagePerDayShouldRead(termDayCount) > 0) {
            PageWasReaded() * 100 / (termDayPas * avgPagePerDayShouldRead(termDayCount))
        } else {
            0
        }
    }
    fun BookPageRemindToGet100Percent(termDayPas : Int, termDayCount : Int): Int {
        val pageNotReadToAvreg = termDayPas * avgPagePerDayShouldRead(termDayCount) - PageWasReaded()
        return if (pageNotReadToAvreg >= 10) {
            if (pageNotReadToAvreg * 4 < avgPageCountWasReadedPerEveryRead()) {
                0
            } else {
                pageNotReadToAvreg
            }
        } else if (avgPageCountWasReadedPerEveryRead() >= 0) {
            pageNotReadToAvreg
        } else {
            0
        }
    }
    fun avgPageCountWasReadedPerEveryRead(): Int {
        return if (dbDto.reads.size > 0) {
            PageWasReaded() / dbDto.reads.size
        } else {
            0
        }
    }

    private fun avgPagePerDayShouldRead(termDayCount : Int): Int {
        return if (termDayCount > 0) {
            pageCount / termDayCount
        } else {
            0
        }
    }

    fun PageWasReaded(): Int {
        return dbDto.reads.sumBy { it.pageRead }
    }

    fun PageRemind(): Int {
        return pageCount - PageWasReaded()
    }

    fun needHighPriorityRead(termDayPas : Int, termDayCount : Int): Boolean {
        return BookPageRemindToGet100Percent(termDayPas,termDayCount) * 3 > avgPageCountWasReadedPerEveryRead()
    }
}