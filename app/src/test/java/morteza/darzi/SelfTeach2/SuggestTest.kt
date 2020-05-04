package morteza.darzi.SelfTeach2

import BL.*
import DAL.Book_db
import DAL.Read_db
import DAL.Termdb
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by M on 19/04/05.
 */
class SuggestTest{

    @Test
    fun book_Create_IsOk() {

        val book = getBook()

        val term = getTerm()


        val performanceBook = BookPerformance(term,book)
        val pageReadTo100Percent = performanceBook.pageReadTo100Percent

        val plan = BookPlan(BookReads(book.getDto(),getReadList()))
        val MaxPageReaded = plan.MaxPageReaded()


        val ss = Suggestion(plan,performanceBook.pageReadTo100Percent)

        assertEquals(221,ss.suggestRead())
        assertEquals(book.name,ss.bookName)
        assert(ss.isOverPlan())
        assert(!ss.HasSuggest(10))
    }



//    @Test
//    fun book_readList_IsOk() {
//
//        val book = Read(0)
//
//        assertEquals("",book.readDate)
//        assertEquals(0,book.pageReadCount)
//
//    }

//    private fun getReadList(page_read:Int): List<Read_db> {
//        val re : MutableList<Read_db> = mutableListOf()
//
//        re.add(Read_db(1,1,page_read,PersianCalendar().persianShortDate))
//
//
//        return re
//    }
    private fun getReadList(): List<Read_db> {
        val re : MutableList<Read_db> = mutableListOf()

        re.add(Read_db(1,1,20,PersianCalendar().persianShortDate))
        re.add(Read_db(1,1,10,PersianCalendar().persianShortDate))
        re.add(Read_db(1,1,10,PersianCalendar().persianShortDate))


        return re
    }

    fun getBook():Book{
        val name = "Book 1"
        val page = 500
        val priority = 3
        val readList = getReadList()

        val db = Book_db(1,name,page,priority)

        return Book(db,readList.sumBy { it.pageRead })
    }
    private fun getTerm(): Term {
        val tname = termType.nimsalAvl.name
        val startDate = PersianCalendar().apply { add(PersianCalendar.DAY_OF_WEEK, -10) }.persianShortDate
        val endDate = PersianCalendar().apply { add(PersianCalendar.DAY_OF_WEEK, 10) }.persianShortDate

        val tdb = Termdb(1, tname, startDate, endDate)

        val term = Term(tdb)
        return term
    }
}