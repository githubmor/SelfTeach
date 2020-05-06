package morteza.darzi.SelfTeach2

import BL.Book
import DAL.BookDataTable
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

        val db = BookDataTable(1, name, page, priority)

        val book = Book(db,readCount)

        assertEquals(name,book.name)
        assertEquals(page,book.pageCount)
        assertEquals(priority,book.priority)
        assertEquals(readCount,book.readSum)
        assertEquals(db, book.getBookDataTable())
    }

    @Test
    fun book_NotRead_IsOk() {

        val name = "Book 1"
        val page = 1000
        val priority = 3
        val readCount = 0

        val db = BookDataTable(1, name, page, priority)

        val book = Book(db, readCount)

        assertEquals(name, book.name)
        assertEquals(page, book.pageCount)
        assertEquals(priority, book.priority)
        assertEquals(readCount, book.readSum)
        assertEquals(db, book.getBookDataTable())
    }

    @Test
    fun book_edit_IsOk() {

        val name = "Book 1"
        val changeName = "Book 2"
        val page = 1000
        val changePage = 200
        val priority = 3
        val changePriority = 5

        val db = BookDataTable(1, name, page, priority)

        val book = Book(db)
        book.name = changeName
        book.priority = changePriority
        book.pageCount = changePage

        val editedDb = book.getBookDataTable()

        assertEquals(changeName,editedDb.name)
        assertEquals(changePage,editedDb.pageCount)
        assertEquals(changePriority,editedDb.priority)
        assertEquals(db, book.getBookDataTable())
    }

    @Test
    fun book_default_IsOk() {

        val book = Book()

        assertEquals("",book.name)
        assertEquals(0,book.pageCount)
        assertEquals(0,book.priority)
        assertEquals(0,book.readSum)
    }

}