package morteza.darzi.SelfTeach2

import BL.Book
import BL.Term
import BL.TermPerformance
import DAL.BookDataTable
import DAL.TermDataTable
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

        assertEquals(19.0F,bookP.performance)
        assertEquals(2,bookP.avgPagePerDayRemind)
        assertEquals(61,bookP.pageReadTo100Percent)
    }

    @Test
    fun bookPerformances_term_Pas_IsOk() {

        val bookPerformances = getBookList()

        val term = getTermPased()

        val bookP = TermPerformance(term, bookPerformances)

        assertEquals(19.0F, bookP.performance)
        assertEquals(2, bookP.avgPagePerDayRemind)
        assertEquals(61, bookP.pageReadTo100Percent)
    }

    @Test
    fun bookPerformances_term_NotBigin_IsOk() {

        val bookPerformances = getBookList()

        val term = getTermNotBigin()

        val bookP = TermPerformance(term, bookPerformances)

        assertEquals(19.0F, bookP.performance)
        assertEquals(2, bookP.avgPagePerDayRemind)
        assertEquals(61, bookP.pageReadTo100Percent)
    }

    fun getBookList(): MutableList<Book> {
        val name = "Book"
        val page = 10
        val re : MutableList<Book> = mutableListOf()

        for (i in 1..5) {
            re.add(Book(BookDataTable(i, name + i, page * i, i), i))
        }

        return re
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