package morteza.darzi.SelfTeach2

import BL.Read
import DAL.ReadDataTable
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by M on 19/04/05.
 */
class ReadTest{

    @Test
    fun book_Create_IsOk() {

        val readDate = "1399/02/01"
        val pageReadCount = 50

        val db = ReadDataTable(1, 1, pageReadCount, readDate)

        val book = Read(db)

        assertEquals(readDate,book.readDate)
        assertEquals(pageReadCount,book.pageReadCount)

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