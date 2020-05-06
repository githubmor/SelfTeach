package morteza.darzi.SelfTeach2

import BL.Book
import BL.BookPlan
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
class BookPalnTest{

    @Test
    fun book_Create_IsOk() {

        val plan = BookPlan(BookReads(Book(BookDataTable(1, "asd", 100, 0)), getReadList()))

        assertEquals(20, plan.avregPageReaded())
    }

//    @Test
//    fun book_edit_IsOk() {
//
//        val name = "Book 1"
//        val changeName = "Book 2"
//        val page = 1000
//        val changePage = 200
//        val priority = 3
//        val changePriority = 5
//
//        val termDataTable = BookDataTable(1,name,page,priority)
//
//        val getBookDataTable = Book(termDataTable,0)
//        getBookDataTable.name = changeName
//        getBookDataTable.priority = changePriority
//        getBookDataTable.pageCount = changePage
//
//        val editedDb = getBookDataTable.getReadDataTable()
//
//        assertEquals(changeName,editedDb.name)
//        assertEquals(changePage,editedDb.pageCount)
//        assertEquals(changePriority,editedDb.priority)
//
//    }
//
//    @Test
//    fun book_readList_IsOk() {
//
//        val getBookDataTable = Book()
//
//        assertEquals("",getBookDataTable.name)
//        assertEquals(0,getBookDataTable.pageCount)
//        assertEquals(0,getBookDataTable.priority)
//        assertEquals(0,getBookDataTable.readSum)
//
//    }

    private fun getReadList(): List<Read> {
        val re: MutableList<Read> = mutableListOf()

        re.add(Read(ReadDataTable(1, 1, 20, PersianCalendar().persianShortDate)))
        re.add(Read(ReadDataTable(1, 1, 30, PersianCalendar().persianShortDate)))
        re.add(Read(ReadDataTable(1, 1, 10, PersianCalendar().persianShortDate)))


        return re
    }


}