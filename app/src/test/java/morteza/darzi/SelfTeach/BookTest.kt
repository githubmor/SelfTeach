package morteza.darzi.SelfTeach

import BL.Book
import BL.Ultility
import DAL.BookReadsdb
import DAL.Bookdb
import DAL.Readdb
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by M on 19/04/05.
 */
class BookTest{

    @Test
    fun book_Create_IsOk() {

        val name = "Book 1"
        val page = 1000
        val priority = 3

        val db = Bookdb(1,name,page,priority)

        val book = Book(BookReadsdb(db))

        assertEquals(name,book.name)
        assertEquals(page,book.pageCount)
        assertEquals(priority,book.priority)
        assertEquals(0,book.pageReadPercent())
        assertEquals("( 0/1000 ) صفحه",book.pageReadState())
        assertEquals(0,book.pageWasReaded())
    }

    @Test
    fun book_edit_IsOk() {

        val name = "Book 1"
        val changeName = "Book 2"
        val page = 1000
        val changePage = 200
        val priority = 3
        val changePriority = 5

        val db = Bookdb(1,name,page,priority)

        val book = Book(BookReadsdb(db))
        book.name = changeName
        book.priority = changePriority
        book.pageCount = changePage

        val editedDb = book.dbDto.book

        assertEquals(changeName,editedDb.name)
        assertEquals(changePage,editedDb.pageCount)
        assertEquals(changePriority,editedDb.priority)

    }

    @Test
    fun book_readList_IsOk() {

        val name = "Book 1"
        val page = 1000
        val priority = 3
        val readList = getReadList()

        val db = Bookdb(1,name,page,priority)

        val book = Book(BookReadsdb(db,readList))

        assertEquals(50,book.pageReadPercent())
        assertEquals("( 500/1000 ) صفحه",book.pageReadState())
        assertEquals(500,book.pageWasReaded())

    }

    private fun getReadList(): List<Readdb> {
        val re : MutableList<Readdb> = mutableListOf()
        for (i in 1..10){
            re.add(Readdb(i,i,50,PersianCalendar().persianShortDate))
        }

        return re
    }


}