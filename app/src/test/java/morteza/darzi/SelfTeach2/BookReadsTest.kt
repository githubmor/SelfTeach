package morteza.darzi.SelfTeach2

import BL.Book
import BL.BookReads
import BL.Read
import DAL.BookDataTable
import DAL.ReadDataTable
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by M on 19/04/05.
 */
class BookReadsTest{

    @Test
    fun book_Create_IsOk() {

        val name = "Book 1"
        val page = 1000
        val priority = 3
        val readCount = 50

        val db = Book(BookDataTable(1, name, page, priority))

        val book = BookReads(db,getReadList(readCount))

        assertEquals(name,book.name)
        assertEquals(page,book.pageCount)
        assertEquals(priority,book.priority)
        assertEquals(readCount,book.readSum)
        assertEquals(getReadList(readCount).count(), book.reads.count())
    }

    @Test
    fun book_edit_IsOk() {

        val name = "Book 1"
        val changeName = "Book 2"
        val page = 1000
        val changePage = 200
        val priority = 3
        val changePriority = 5

        val db = Book(BookDataTable(1, name, page, priority))

        val book = BookReads(db,getReadList(10))
        book.name = changeName
        book.priority = changePriority
        book.pageCount = changePage

        val editedDb = book.getBookDataTable()

        assertEquals(changeName,editedDb.name)
        assertEquals(changePage,editedDb.pageCount)
        assertEquals(changePriority,editedDb.priority)

    }

    @Test
    fun book_readAll_IsOk() {

        val name = "Book 1"
        val page = 100
        val priority = 3
        val db = Book(BookDataTable(1, name, page, priority))

        val book = BookReads(db, getReadAllList(page))

        assertEquals(name, book.name)
        assertEquals(page, book.pageCount)
        assertEquals(priority, book.priority)
        assertEquals(page, book.readSum)
        assertEquals(getReadList(page).count(), book.reads.count())
    }

    @Test
    fun book_NotRead_IsOk() {

        val name = "Book 1"
        val page = 100
        val priority = 3

        val db = Book(BookDataTable(1, name, page, priority))

        val book = BookReads(db, getNotReadList())

        assertEquals(name, book.name)
        assertEquals(page, book.pageCount)
        assertEquals(priority, book.priority)
        assertEquals(0, book.readSum)
        assertEquals(0, book.reads.count())
    }

    private fun getReadList(page_read: Int): List<Read> {
        val re: MutableList<Read> = mutableListOf()

        re.add(Read(ReadDataTable(1, 1, page_read, PersianCalendar().persianShortDate)))


        return re
    }

    private fun getReadAllList(page_read: Int): List<Read> {
        val re: MutableList<Read> = mutableListOf()

        re.add(Read(ReadDataTable(1, 1, page_read, PersianCalendar().persianShortDate)))


        return re
    }

    private fun getNotReadList(): List<Read> {
        return mutableListOf()
    }

}