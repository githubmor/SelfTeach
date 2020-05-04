package morteza.darzi.SelfTeach2

import BL.Book
import BL.Read
import DAL.Book_Reads_db
import DAL.Book_db
import DAL.Read_db
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
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

        val db = Read_db(1,1,pageReadCount,readDate)

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

//    private fun getReadList(page_read:Int): List<Read_db> {
//        val re : MutableList<Read_db> = mutableListOf()
//
//        re.add(Read_db(1,1,page_read,PersianCalendar().persianShortDate))
//
//
//        return re
//    }


}