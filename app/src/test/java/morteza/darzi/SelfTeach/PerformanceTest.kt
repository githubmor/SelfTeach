package morteza.darzi.SelfTeach

import BL.Book
import BL.Performance
import BL.PerformanceBook
import BL.Term
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
class PerformanceTest{

    @Test
    fun book_Create_IsOk() {

        val book = getBookList()

        val term = getTerm()

        val bookP = Performance(term,book)

        assertEquals(2,bookP.avgBookReadPerDay)
        assertEquals(61,bookP.pagePerDayRemind)
        assertEquals(-8,bookP.pageTo100Percent)
        assertEquals(112,bookP.performance)
        assertEquals(0,bookP.readList().size)
    }


    private fun getReadList(bookId : Int,readCount:Int): List<Readdb> {
        val re : MutableList<Readdb> = mutableListOf()
        for (i in 1..readCount){
            re.add(Readdb(i,bookId,i*2,PersianCalendar().apply { add(PersianCalendar.DAY_OF_WEEK,i*2)}.persianShortDate))
        }

        return re
    }

    fun getBookList(): MutableList<PerformanceBook> {
        val name = "Book"
        val page = 10
        val re : MutableList<PerformanceBook> = mutableListOf()

        for (i in 1..5) {
            re.add(PerformanceBook(getTerm(),
                    Book(BookReadsdb(Bookdb(i, name + i, page * i, i),getReadList(i,i)))))
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