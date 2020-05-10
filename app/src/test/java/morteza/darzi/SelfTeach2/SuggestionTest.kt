package morteza.darzi.SelfTeach2

import core.*
import data.BookDataTable
import data.ReadDataTable
import data.TermDataTable
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by M on 19/04/05.
 */
class SuggestionTest {

    @Test
    fun suggest_Avreg_IsBigger_PageRemind() {

        val books = getBook()

        val term = getTerm(4)
        val p = TermPerformance(term, books)

        val ss = Suggestion(term, books).getBookSuggestList(p)

        assertEquals(3, ss.count())
    }


//    @Test
//    fun suggest_Avreg_IsBigger_PageRemind3() {
//
//        val books = getBook()
//
//        val term = getTerm(3)
//        val p = TermPerformance(term,books)
//
//        val ss = Suggestion(term, books).getBookSuggestList(p)
//
//        assertEquals(2, ss.count())
//    }
//
//    @Test
//    fun suggest_Book_NotStarToRead() {
//
//        val books = getBookNoRead()
//
//        val term = getTerm(3)
//        val p = TermPerformance(term,books)
//
//        val ss = Suggestion(term, books).getBookSuggestList(p)
//
//        assertEquals(2, ss.count())
//    }

//    @Test
//    fun suggest_PageRemind_IsBiger_Avg() {
//
//        val book = getBook(false)
//
//        val term = getTerm(3)
//
//        val performanceBook = BookPerformance(term,book)
//        //For Debug
//        //pageRemind = 150
//        //dayPas = 3
//        //PageShouldRead = 10
//        //pageReadCount = 50
//        //PageCount = 200
//
//        val plan = BookPlan(book)
//        //AvregPageRead = 7.4
//
//        val ss = BookSuggestion(plan,performanceBook.pageReadTo100Percent)
//
//        assertEquals(10,ss.readSuggest())// 7.4 !> 10 --- > Avg > PageShouldRead ---> 10
//        assertEquals(book.name,ss.name)
//        assert(ss.needFoghPlan())
//        assert(ss.hasSuggest(10))
//        assert(!ss.hasSuggest(4))
//    }


    private fun get50ReadPageWithAvrag12(): List<Read> {
        val re: MutableList<Read> = mutableListOf()

        re.add(Read(ReadDataTable(1, 1, 8, PersianCalendar().persianShortDate)))
        re.add(Read(ReadDataTable(1, 1, 10, PersianCalendar().persianShortDate)))
        re.add(Read(ReadDataTable(1, 1, 17, PersianCalendar().persianShortDate)))
        return re
    }

    private fun get50ReadWithAvrag7(): List<Read> {
        val re: MutableList<Read> = mutableListOf()

        re.add(Read(ReadDataTable(1, 1, 10, PersianCalendar().persianShortDate)))
        re.add(Read(ReadDataTable(1, 1, 8, PersianCalendar().persianShortDate)))
        re.add(Read(ReadDataTable(1, 1, 4, PersianCalendar().persianShortDate)))
        re.add(Read(ReadDataTable(1, 1, 5, PersianCalendar().persianShortDate)))
        re.add(Read(ReadDataTable(1, 1, 6, PersianCalendar().persianShortDate)))

        return re
    }

    private fun getEmptyReadList(): List<Read> {

        return mutableListOf()
    }

    fun getBookNoRead(): MutableList<BookReads> {
        val re = mutableListOf<BookReads>()

        val name = "Book 1"
        val page = 200
        val priority = 3

        val db = Book(BookDataTable(1, name, page, priority))
        re.add(BookReads(db, getEmptyReadList()))
        re.add(BookReads(db, getEmptyReadList()))
        re.add(BookReads(db, getEmptyReadList()))

        return re
    }

    fun getBook(): MutableList<BookReads> {
        val re = mutableListOf<BookReads>()

        val name = "Book 1"
        val page = 200
        val priority = 3

        val db = Book(BookDataTable(1, name, page, priority))
        re.add(BookReads(db, get50ReadPageWithAvrag12()))
        re.add(BookReads(db, get50ReadWithAvrag7()))
        re.add(BookReads(db, get50ReadWithAvrag7()))

        return re
    }

    private fun getTerm(dayPas: Int): Term {
        val daycount = 5
        val tname = TermType.NimsalAvl.name
        val startDate = PersianCalendar().apply { add(PersianCalendar.DAY_OF_WEEK, -dayPas) }.persianShortDate
        val endDate = PersianCalendar().apply { add(PersianCalendar.DAY_OF_WEEK, daycount - dayPas) }.persianShortDate

        val tdb = TermDataTable(1, tname, startDate, endDate)

        val term = Term(tdb)
        return term
    }
}