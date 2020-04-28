package morteza.darzi.SelfTeach2

import BL.Book
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
        assertEquals(0F,book.pageReadPercent)
//        assertEquals("( 0/1000 ) صفحه",book.pageReadState)
        assertEquals(0,book.pageWasReaded)
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
        val page_read = 50
        val readList = getReadList(page_read)

        val db = Bookdb(1,name,page,priority)

        val book = Book(BookReadsdb(db,readList))

        assertEquals((page_read*100/page).toFloat(),book.pageReadPercent)
//        assertEquals("( 500/1000 ) صفحه",book.pageReadState)
        assertEquals(page_read,book.pageWasReaded)

    }

    private fun getReadList(page_read:Int): List<Readdb> {
        val re : MutableList<Readdb> = mutableListOf()

        re.add(Readdb(1,1,page_read,PersianCalendar().persianShortDate))


        return re
    }


}