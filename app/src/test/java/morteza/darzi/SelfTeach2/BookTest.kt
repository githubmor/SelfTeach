package morteza.darzi.SelfTeach2

import BL.Book
import DAL.Book_Reads_db
import DAL.Book_db
import DAL.Read_db
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

        val db = Book_db(1,name,page,priority)

        val book = Book(Book_Reads_db(db))

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

        val db = Book_db(1,name,page,priority)

        val book = Book(Book_Reads_db(db))
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

        val db = Book_db(1,name,page,priority)

        val book = Book(Book_Reads_db(db,readList))

        assertEquals((page_read*100/page).toFloat(),book.pageReadPercent)
//        assertEquals("( 500/1000 ) صفحه",book.pageReadState)
        assertEquals(page_read,book.pageWasReaded)

    }

    private fun getReadList(page_read:Int): List<Read_db> {
        val re : MutableList<Read_db> = mutableListOf()

        re.add(Read_db(1,1,page_read,PersianCalendar().persianShortDate))


        return re
    }


}