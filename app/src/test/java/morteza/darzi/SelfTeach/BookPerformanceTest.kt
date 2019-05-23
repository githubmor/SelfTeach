package morteza.darzi.SelfTeach

import BL.Book
import BL.PerformanceBook
import BL.Term
import BL.Ultility
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

        val bookP = PerformanceBook(term,book)

        assertEquals(50,bookP.avgPagePerEveryRead())
        assertEquals(55,bookP.biggestPageCanRead())
        assertEquals(10,bookP.pageRemindToGet100Percent())
        assertEquals(10,bookP.performance())
    }



//    @Test
//    fun book_edit_IsOk() {
//
//        val name = "Book 1"
//        val changeName = "Book 2"
//        val page = 1000
//        val changePage = 200
//        val priority = 3
//        val changePriority = 5
//
//        val db = Bookdb(1,name,page,priority)
//
//        val book = Book(BookReadsdb(db))
//        book.name = changeName
//        book.priority = changePriority
//        book.pageCount = changePage
//
//        val editedDb = book.dbDto.book
//
//        assertEquals(changeName,editedDb.name)
//        assertEquals(changePage,editedDb.pageCount)
//        assertEquals(changePriority,editedDb.priority)
//
//    }
//
//    @Test
//    fun book_readList_IsOk() {
//
//        val name = "Book 1"
//        val page = 1000
//        val priority = 3
//        val readList = getReadList()
//
//        val db = Bookdb(1,name,page,priority)
//
//        val book = Book(BookReadsdb(db,readList))
//
//        assertEquals(50,book.pageReadPercent())
//        assertEquals("( 500/1000 ) صفحه",book.pageReadState())
//        assertEquals(500,book.pageWasReaded())
//
//    }

    private fun getReadList(): List<Readdb> {
        val re : MutableList<Readdb> = mutableListOf()
        for (i in 1..10){
            re.add(Readdb(i,i,50,PersianCalendar().persianShortDate))
        }

        return re
    }

    fun getBook():Book{
        val name = "Book 1"
        val page = 1000
        val priority = 3
        val readList = getReadList()

        val db = Bookdb(1,name,page,priority)

        return Book(BookReadsdb(db,readList))
    }
    private fun getTerm(): Term {
        val tname = termType.nimsalAvl.name
        val startDate = PersianCalendar().apply { add(PersianCalendar.MONTH, -1) }.persianShortDate
        val endDate = PersianCalendar().apply { add(PersianCalendar.MONTH, 2) }.persianShortDate

        val tdb = Termdb(1, tname, startDate, endDate)

        val term = Term(tdb)
        return term
    }

}