package morteza.darzi.SelfTeach2

import core.Book
import core.Term
import core.TermPerformance
import data.BookDataTable
import data.TermDataTable
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by M on 19/04/05.
 */
class TermPerformanceTest{

    @Test
    fun bookPerformances_Create_IsOk() {

        val bookPerformances = getBookList()

        val term = getTerm()

        val bookP = TermPerformance(term,bookPerformances)

        assertEquals(50F, bookP.performance)
        assertEquals(10, bookP.avgPagePerDayRemind)
        assertEquals(10, bookP.pageReadTo100Percent)
    }

    @Test
    fun bookPerformances_term_Pas_IsOk() {

        val bookPerformances = getBookList()

        val term = getTermPased()

        val bookP = TermPerformance(term, bookPerformances)

        assertEquals(33.333332F, bookP.performance)
        assertEquals(20, bookP.avgPagePerDayRemind)
        assertEquals(20, bookP.pageReadTo100Percent)
    }

    @Test
    fun bookPerformances_term_NotBigin_IsOk() {

        val bookPerformances = getBookList()

        val term = getTermNotBigin()

        val bookP = TermPerformance(term, bookPerformances)

        assertEquals(33.333332F, bookP.performance)
        assertEquals(0, bookP.avgPagePerDayRemind)
        assertEquals(0, bookP.pageReadTo100Percent)
    }

    fun getBookList(): MutableList<Book> {
        val name = "Book"
        val page = 10
        val re : MutableList<Book> = mutableListOf()

        re.add(Book(BookDataTable(1, name, page, 1), 5))
        re.add(Book(BookDataTable(1, name, page, 1), 3))
        re.add(Book(BookDataTable(1, name, page, 1), 2))
        return re
    }
    private fun getTerm(): Term {
        val tname = TermType.NimsalAvl.name
        val startDate = PersianCalendar().apply { add(PersianCalendar.DAY_OF_WEEK, -4) }.persianShortDate
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