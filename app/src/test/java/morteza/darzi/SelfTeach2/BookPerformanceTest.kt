package morteza.darzi.SelfTeach2

import BL.Book
import BL.BookPerformance
import BL.Term
import DAL.BookDataTable
import DAL.ReadDataTable
import DAL.TermDataTable
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by M on 19/04/05.
 */
class BookPerformanceTest{

    @Test
    fun bookPerformance_IsOk() {

        val book = getBook()

        val term = getTerm()

        val performanceBook = BookPerformance(term,book)

        assertEquals(40,performanceBook.avgPagePerDayRemind)
        assertEquals(30, performanceBook.pageReadTo100Percent)
        assertEquals(40F, performanceBook.performance)
    }

    @Test
    fun bookPerformance_term_pas_IsOk() {

        val book = getBook()

        val term = getTermPased()

        val performanceBook = BookPerformance(term, book)

        assertEquals(80, performanceBook.avgPagePerDayRemind)
        assertEquals(80, performanceBook.pageReadTo100Percent)
        assertEquals(20F, performanceBook.performance)
    }

    @Test
    fun bookPerformance_term_not_bigin_IsOk() {

        val book = getBook()

        val term = getTermNotBigin()

        val performanceBook = BookPerformance(term, book)

        assertEquals(0, performanceBook.avgPagePerDayRemind)
        assertEquals(0, performanceBook.pageReadTo100Percent)
        assertEquals(20F, performanceBook.performance)
    }

    @Test
    fun bookPerformance_book_read_allPage_IsOk() {

        val book = getBookReadAll()

        val term = getTerm()

        val performanceBook = BookPerformance(term, book)

        assertEquals(0, performanceBook.avgPagePerDayRemind)
        assertEquals(0, performanceBook.pageReadTo100Percent)
        assertEquals(100F, performanceBook.performance)
    }

    @Test
    fun bookPerformance_book_NotRead_IsOk() {

        val book = getBookNotRead()

        val term = getTerm()

        val performanceBook = BookPerformance(term, book)

        assertEquals(50, performanceBook.avgPagePerDayRemind)
        assertEquals(50, performanceBook.pageReadTo100Percent)
        assertEquals(0F, performanceBook.performance)
    }

    private fun getReadList(): List<ReadDataTable> {
        val re: MutableList<ReadDataTable> = mutableListOf()

        re.add(ReadDataTable(1, 1, 10, PersianCalendar().persianShortDate))
        re.add(ReadDataTable(1, 1, 10, PersianCalendar().persianShortDate))

        return re
    }

    fun getBook():Book{
        val name = "Book 1"
        val page = 100
        val priority = 3
        val readList = getReadList()

        val db = BookDataTable(1, name, page, priority)

        return Book(db, readList.sumBy { it.pageReadCount })
    }

    private fun getAllReadList(allPage: Int): List<ReadDataTable> {
        val re: MutableList<ReadDataTable> = mutableListOf()
        re.add(ReadDataTable(1, 1, allPage, PersianCalendar().apply { add(PersianCalendar.DAY_OF_WEEK, -1) }.persianShortDate))

        return re
    }

    fun getBookReadAll(): Book {
        val name = "Book 1"
        val page = 100
        val priority = 3
        val readList = getAllReadList(page)

        val db = BookDataTable(1, name, page, priority)

        return Book(db, readList.sumBy { it.pageReadCount })
    }

    private fun getNotReadList(): List<ReadDataTable> {
        return mutableListOf()
    }

    fun getBookNotRead(): Book {
        val name = "Book 1"
        val page = 100
        val priority = 3
        val readList = getNotReadList()

        val db = BookDataTable(1, name, page, priority)

        return Book(db, readList.sumBy { it.pageReadCount })
    }

    private fun getTerm(): Term {
        val tname = TermType.NimsalAvl.name
        val startDate = PersianCalendar().apply { add(PersianCalendar.DAY_OF_WEEK, -2) }.persianShortDate
        val endDate = PersianCalendar().apply { add(PersianCalendar.DAY_OF_WEEK, 2) }.persianShortDate

        val tdb = TermDataTable(1, tname, startDate, endDate)

        val term = Term(tdb)
        return term
    }

    private fun getTermPased(): Term {
        val tname = TermType.NimsalAvl.name
        val startDate = PersianCalendar().apply { add(PersianCalendar.DAY_OF_WEEK, -8) }.persianShortDate
        val endDate = PersianCalendar().apply { add(PersianCalendar.DAY_OF_WEEK, -2) }.persianShortDate

        val tdb = TermDataTable(1, tname, startDate, endDate)

        val term = Term(tdb)
        return term
    }

    private fun getTermNotBigin(): Term {
        val tname = TermType.NimsalAvl.name
        val startDate = PersianCalendar().apply { add(PersianCalendar.DAY_OF_WEEK, 2) }.persianShortDate
        val endDate = PersianCalendar().apply { add(PersianCalendar.DAY_OF_WEEK, 7) }.persianShortDate

        val tdb = TermDataTable(1, tname, startDate, endDate)

        val term = Term(tdb)
        return term
    }
}