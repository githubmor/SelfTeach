package morteza.darzi.SelfTeach2

import BL.*
import DAL.BookReadsdb
import DAL.Bookdb
import DAL.Readdb
import DAL.Termdb
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by M on 19/04/05.
 */
class TermPerformanceTest{

    @Test
    fun book_Create_IsOk() {

        val books = getBookList()

        val term = getTerm()

        val bookP = TermPerformance(term,books)

        assertEquals(200.0F,bookP.performance)
        assertEquals(0,bookP.avgPagePerDayRemind)
        assertEquals(-75,bookP.pageReadTo100Percent)
    }


    private fun getReadList(bookId : Int,readCount:Int): List<Readdb> {
        val re : MutableList<Readdb> = mutableListOf()
        for (i in 1..readCount){
            re.add(Readdb(i,bookId,i*2,PersianCalendar().apply { add(PersianCalendar.DAY_OF_WEEK,i*2)}.persianShortDate))
        }

        return re
    }

    fun getBookList(): MutableList<Book> {
        val name = "Book"
        val page = 10
        val re : MutableList<Book> = mutableListOf()

        for (i in 1..5) {
            re.add(Book(BookReadsdb(Bookdb(i, name + i, page * i, i),getReadList(i,i))))
        }

        return re
    }
    private fun getTerm(): Term {
        val tname = termType.nimsalAvl.name
        val startDate = PersianCalendar().apply { add(PersianCalendar.MONTH, -2) }.persianShortDate
        val endDate = PersianCalendar().apply { add(PersianCalendar.MONTH, 2) }.persianShortDate

        val tdb = Termdb(1, tname, startDate, endDate)

        val term = Term(tdb)
        return term
    }

}