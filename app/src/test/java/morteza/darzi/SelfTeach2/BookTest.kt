package morteza.darzi.SelfTeach2

import BL.Book
import DAL.Book_db
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
        val readCount = 50

        val db = Book_db(1,name,page,priority)

        val book = Book(db,readCount)

        assertEquals(name,book.name)
        assertEquals(page,book.pageCount)
        assertEquals(priority,book.priority)
        assertEquals(readCount,book.readSum)
        assertEquals(db,book.getDto())
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

        val book = Book(db,0)
        book.name = changeName
        book.priority = changePriority
        book.pageCount = changePage

        val editedDb = book.getDto()

        assertEquals(changeName,editedDb.name)
        assertEquals(changePage,editedDb.pageCount)
        assertEquals(changePriority,editedDb.priority)
        assertEquals(db,book.getDto())
    }

    @Test
    fun book_readList_IsOk() {

        val book = Book()

        assertEquals("",book.name)
        assertEquals(0,book.pageCount)
        assertEquals(0,book.priority)
        assertEquals(0,book.readSum)
    }

//    private fun getReadList(page_read:Int): List<Read_db> {
//        val re : MutableList<Read_db> = mutableListOf()
//
//        re.add(Read_db(1,1,page_read,PersianCalendar().persianShortDate))
//
//
//        return re
//    }


}