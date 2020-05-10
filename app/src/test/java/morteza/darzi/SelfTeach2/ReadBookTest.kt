package morteza.darzi.SelfTeach2

import core.ReadBook
import data.ReadDataTable
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by M on 19/04/05.
 */
class ReadBookTest{

    @Test
    fun ReadBook_Create_IsOk() {

        val readDate = "1399/02/01"
        val pageReadCount = 50
        val bookName = "dsfds"

        val db = ReadDataTable(1, 1, pageReadCount, readDate)

        val book = ReadBook(db,bookName)

        assertEquals(readDate,book.readDate)
        assertEquals(pageReadCount,book.pageReadCount)
        assertEquals(bookName,book.bookName)
        assertEquals(db, book.getReadDataTable())
    }
    @Test
    fun ReadBook_empty_BookName_IsOk() {

        val readDate = "1399/02/01"
        val pageReadCount = 50
        val bookName = ""

        val db = ReadDataTable(1, 1, pageReadCount, readDate)

        val book = ReadBook(db, bookName)

        assertEquals(readDate, book.readDate)
        assertEquals(pageReadCount, book.pageReadCount)
        assertEquals(bookName, book.bookName)
        assertEquals(db, book.getReadDataTable())
    }

}