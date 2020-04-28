package morteza.darzi.SelfTeach2

import BL.Book
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
class BookPerformanceTest{

    @Test
    fun book_Create_IsOk() {

        val book = getBook()

        val term = getTerm()

        val performanceBook = PerformanceBook(term,book)

        assertEquals(40,performanceBook.avgPagePerDayRemind)
        assertEquals(40,performanceBook.pageReadTo100Percent)
        assertEquals(33.0F,performanceBook.performance)
    }

    private var count = 2
    private var avgPPR = 0
    private fun getReadList(): List<Readdb> {
        val re : MutableList<Readdb> = mutableListOf()
        for (i in 1..count){
            val p = 10
            re.add(Readdb(i,1,p,PersianCalendar().apply { add(PersianCalendar.DAY_OF_WEEK,i*2)}.persianShortDate))
            avgPPR += p
        }

        return re
    }

    fun getBook():Book{
        val name = "Book 1"
        val page = 100
        val priority = 3
        val readList = getReadList()

        val db = Bookdb(1,name,page,priority)

        return Book(BookReadsdb(db,readList))
    }
    private fun getTerm(): Term {
        val tname = termType.nimsalAvl.name
        val startDate = PersianCalendar().apply { add(PersianCalendar.DAY_OF_WEEK, -2) }.persianShortDate
        val endDate = PersianCalendar().apply { add(PersianCalendar.DAY_OF_WEEK, 2) }.persianShortDate

        val tdb = Termdb(1, tname, startDate, endDate)

        val term = Term(tdb)
        return term
    }


}