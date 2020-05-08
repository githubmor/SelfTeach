package morteza.darzi.SelfTeach2

import BL.*
import DAL.BookDataTable
import DAL.ReadDataTable
import DAL.TermDataTable
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by M on 19/04/05.
 */
class BookSuggestTest {

    @Test
    fun suggest_Avreg_IsBigger_PageRemind() {

        val book = getBook(true)

        val term = getTerm(3)

        val performanceBook = BookPerformance(term, book)
        //For Debug
        //pageRemind = 150
        //dayPas = 3
        //PageShouldRead = 10
        //pageReadCount = 50
        //PageCount = 200

        val plan = BookPlan(book)
        //AvregPageRead = 12.5


        val ss = BookSuggestion(plan, performanceBook.pageReadTo100Percent)

        assertEquals(12, ss.readSuggest()) // 12.5 > 10 --- > Avg > PageShouldRead ---> 12
        assertEquals(book.name, ss.name)
        assert(!ss.needFoghPlan())
        assert(ss.hasSuggest(10))
        assert(!ss.hasSuggest(4))
    }

    @Test
    fun suggest_PageRemind_IsBiger_Avg() {

        val book = getBook(false)

        val term = getTerm(3)

        val performanceBook = BookPerformance(term, book)
        //For Debug
        //pageRemind = 150
        //dayPas = 3
        //PageShouldRead = 10
        //pageReadCount = 50
        //PageCount = 200

        val plan = BookPlan(book)
        //AvregPageRead = 7.4

        val ss = BookSuggestion(plan, performanceBook.pageReadTo100Percent)

        assertEquals(10, ss.readSuggest())// 7.4 !> 10 --- > Avg > PageShouldRead ---> 10
        assertEquals(book.name, ss.name)
        assert(ss.needFoghPlan())
        assert(ss.hasSuggest(10))
        assert(!ss.hasSuggest(4))
    }


    private fun getReadListWithAvragBigger(): List<Read> {
        val re: MutableList<Read> = mutableListOf()

        re.add(Read(ReadDataTable(1, 1, 8, PersianCalendar().persianShortDate)))
        re.add(Read(ReadDataTable(1, 1, 15, PersianCalendar().persianShortDate)))
        re.add(Read(ReadDataTable(1, 1, 10, PersianCalendar().persianShortDate)))
        re.add(Read(ReadDataTable(1, 1, 17, PersianCalendar().persianShortDate)))
        return re
    }

    private fun getReadListWithAvragLower(): List<Read> {
        val re: MutableList<Read> = mutableListOf()

        re.add(Read(ReadDataTable(1, 1, 10, PersianCalendar().persianShortDate)))
        re.add(Read(ReadDataTable(1, 1, 8, PersianCalendar().persianShortDate)))
        re.add(Read(ReadDataTable(1, 1, 7, PersianCalendar().persianShortDate)))
        re.add(Read(ReadDataTable(1, 1, 4, PersianCalendar().persianShortDate)))
        re.add(Read(ReadDataTable(1, 1, 5, PersianCalendar().persianShortDate)))
        re.add(Read(ReadDataTable(1, 1, 10, PersianCalendar().persianShortDate)))
        re.add(Read(ReadDataTable(1, 1, 6, PersianCalendar().persianShortDate)))

        return re
    }

    private fun getBook(isBigger: Boolean): BookReads {
        val name = "Book 1"
        val page = 200
        val priority = 3

        val db = Book(BookDataTable(1, name, page, priority))
        return when {
            isBigger -> BookReads(db, getReadListWithAvragBigger())
            else -> BookReads(db, getReadListWithAvragLower())
        }

    }

    private fun getTerm(dayPas: Int): Term {
        val daycount = 10
        val tname = TermType.NimsalAvl.name
        val startDate = PersianCalendar().apply { add(PersianCalendar.DAY_OF_WEEK, -dayPas) }.persianShortDate
        val endDate = PersianCalendar().apply { add(PersianCalendar.DAY_OF_WEEK, daycount - dayPas) }.persianShortDate

        val tdb = TermDataTable(1, tname, startDate, endDate)

        val term = Term(tdb)
        return term
    }
}