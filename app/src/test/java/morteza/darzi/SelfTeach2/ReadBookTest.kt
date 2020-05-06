package morteza.darzi.SelfTeach2

import BL.Read
import BL.ReadBook
import DAL.ReadDataTable
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by M on 19/04/05.
 */
class ReadBookTest{

    @Test
    fun book_Create_IsOk() {

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
    fun book_readList_IsOk() {

        val book = Read(0)

        assertEquals("",book.readDate)
        assertEquals(0,book.pageReadCount)

    }

//    private fun getReadList(page_read:Int): List<ReadDataTable> {
//        val re : MutableList<ReadDataTable> = mutableListOf()
//
//        re.add(ReadDataTable(1,1,page_read,PersianCalendar().persianShortDate))
//
//
//        return re
//    }


}