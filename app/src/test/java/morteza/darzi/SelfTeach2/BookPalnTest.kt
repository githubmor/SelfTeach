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
    fun bookPlan_Create_IsOk() {

        val plan = BookPlan(BookReads(Book(BookDataTable(1, "asd", 100, 0)), getReadList()))

        assertEquals(20, plan.avregPageReaded())
    }

    @Test
    fun bookPlan_Create_empty_ReadList_IsOk() {

        val plan = BookPlan(BookReads(Book(BookDataTable(1, "asd", 100, 0)), getEmptyReadList()))

        assertEquals(0, plan.avregPageReaded())
    }

    @Test
    fun bookPlan_ReadAll_NotReturnAvg_IsOk() {

        val plan = BookPlan(BookReads(Book(BookDataTable(1, "asd", 100, 0)), getAllReadList(100)))

        assertEquals(0, plan.avregPageReaded())
    }

    private fun getReadList(): List<Read> {
        val re: MutableList<Read> = mutableListOf()

        re.add(Read(ReadDataTable(1, 1, 20, PersianCalendar().persianShortDate)))
        re.add(Read(ReadDataTable(1, 1, 30, PersianCalendar().persianShortDate)))
        re.add(Read(ReadDataTable(1, 1, 10, PersianCalendar().persianShortDate)))

        return re
    }

    private fun getEmptyReadList(): List<Read> {
        return mutableListOf()
    }

    private fun getAllReadList(page: Int): List<Read> {
        val re: MutableList<Read> = mutableListOf()

        re.add(Read(ReadDataTable(1, 1, page, PersianCalendar().persianShortDate)))

        return re
    }
}