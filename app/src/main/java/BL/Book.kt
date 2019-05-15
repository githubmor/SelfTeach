package BL

import DAL.BookReadsdb

class Book(val dbDto: BookReadsdb) {
    fun PageReadPercent(): Int {
        val readSumm = dbDto.reads.sumBy { it.pageRead }
        return ((readSumm *100)/ pageCount)
    }

    val pageCount = dbDto.book.pageCount
    val name = dbDto.book.name

    fun BookPerformance(termDayPas : Int,termDayCount : Int): Int {
        return if (termDayPas > 0 && PPDBook(termDayCount) > 0) {
            PageWasReaded() * 100 / (termDayPas * PPDBook(termDayCount))
        } else {
            0
        }
    }
    fun BookPageTo100Percent(termDayPas : Int,termDayCount : Int): Int {
        val i = termDayPas * PPDBook(termDayCount) - PageWasReaded()
        return if (i >= 10) {
            if (i * 4 < UserPPDReadBook()) {
                0
            } else {
                i
            }
        } else if (UserPPDReadBook() >= 0) {
            i
        } else {
            0
        }
    }
    fun UserPPDReadBook(): Int {
        return if (dbDto.reads.size > 0) {
            PageWasReaded() / dbDto.reads.size
        } else {
            0
        }
    }

    private fun PPDBook(termDayCount : Int): Int {
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

    fun needForRead(termDayPas : Int,termDayCount : Int,max:Int): Int {
        return if (BookPageTo100Percent(termDayPas,termDayCount) * 3 > UserPPDReadBook() && BookPageTo100Percent(termDayPas,termDayCount) > max) {
            BookPageTo100Percent(termDayPas,termDayCount)
        }else
            0
    }
}