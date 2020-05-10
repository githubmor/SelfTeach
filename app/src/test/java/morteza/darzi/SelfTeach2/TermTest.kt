package morteza.darzi.SelfTeach2

import core.Term
import data.TermDataTable
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by M on 19/04/05.
 */
class TermTest{

    @Test
    fun Creat_Term_IsOk() {

        val name = TermType.NimsalAvl
        val startDate = PersianCalendar().apply { add(PersianCalendar.DAY_OF_WEEK, -10) }
        val endDate = PersianCalendar().apply { add(PersianCalendar.DAY_OF_WEEK, 15) }

        val db = TermDataTable(1, name.name, startDate.persianShortDate, endDate.persianShortDate)

        val term = Term(db)

        assertEquals(name.typeName, term.name)
        assertEquals(startDate.persianShortDate, term.startDate)
        assertEquals(endDate.persianShortDate, term.endDate)
        assertEquals(25, term.dayCount)
        assertEquals(10, term.dayPast)
        assertEquals(26, term.getCalenderActiveDaysList().size)
    }

    @Test
    fun Creat_Term_NotStart() {

        val name = TermType.NimsalAvl
        val startDate = PersianCalendar().apply { add(PersianCalendar.DAY_OF_WEEK, 10) }
        val endDate = PersianCalendar().apply { add(PersianCalendar.DAY_OF_WEEK, 15) }

        val db = TermDataTable(1, name.name, startDate.persianShortDate, endDate.persianShortDate)

        val term = Term(db)

        assertEquals(name.typeName, term.name)
        assertEquals(startDate.persianShortDate, term.startDate)
        assertEquals(endDate.persianShortDate, term.endDate)
        assertEquals(5, term.dayCount)
        assertEquals(0, term.dayPast)
        assertEquals(6, term.getCalenderActiveDaysList().size)
    }

    @Test
    fun Creat_Term_Ended() {

        val name = TermType.NimsalAvl
        val startDate = PersianCalendar().apply { add(PersianCalendar.DAY_OF_WEEK, -10) }
        val endDate = PersianCalendar().apply { add(PersianCalendar.DAY_OF_WEEK, -5) }

        val db = TermDataTable(1, name.name, startDate.persianShortDate, endDate.persianShortDate)

        val term = Term(db)

        assertEquals(name.typeName,term.name)
        assertEquals(startDate.persianShortDate,term.startDate)
        assertEquals(endDate.persianShortDate,term.endDate)
        assertEquals(5, term.dayCount)
        assertEquals(5, term.dayPast)
        assertEquals(6, term.getCalenderActiveDaysList().size)
    }

    @Test
    fun Creat_Term_NotStar_Today() {

        val name = TermType.NimsalAvl
        val startDate = PersianCalendar()
        val endDate = PersianCalendar().apply { add(PersianCalendar.DAY_OF_WEEK, 5) }

        val db = TermDataTable(1, name.name, startDate.persianShortDate, endDate.persianShortDate)

        val term = Term(db)

        assertEquals(name.typeName, term.name)
        assertEquals(startDate.persianShortDate, term.startDate)
        assertEquals(endDate.persianShortDate, term.endDate)
        assertEquals(5, term.dayCount)
        assertEquals(0, term.dayPast)
        assertEquals(6, term.getCalenderActiveDaysList().size)
    }

    @Test(expected = ArithmeticException::class)
    fun Creat_Term_Exception_Date_is_Reverse() {

        val name = TermType.NimsalAvl
        val startDate = PersianCalendar().apply { add(PersianCalendar.DAY_OF_WEEK, 10) }
        val endDate = PersianCalendar().apply { add(PersianCalendar.DAY_OF_WEEK, 5) }

        val db = TermDataTable(1, name.name, startDate.persianShortDate, endDate.persianShortDate)

        Term(db)

    }

    @Test(expected = ArithmeticException::class)
    fun Creat_Term_Exception_Date_is_equal() {

        val name = TermType.NimsalAvl

        val db = TermDataTable(1, name.name, "1399/01/02", "1399/01/02")

        Term(db)

    }


    @Test
    fun term_Edit_IsOk() {

        val name = TermType.NimsalAvl.name
        val startDate = PersianCalendar().apply {  add(PersianCalendar.MONTH,-1)}
        val endDate = PersianCalendar().apply { add(PersianCalendar.MONTH,2)}


        val db = TermDataTable(1, name, startDate.persianShortDate, endDate.persianShortDate)

        val term = Term(db)

        val cname = TermType.TermTabestan.name
        val cstartDate = PersianCalendar().apply { add(PersianCalendar.DAY_OF_WEEK, -2) }
        val cendDate = PersianCalendar().apply { add(PersianCalendar.DAY_OF_WEEK, 3) }

        term.name = cname
        term.startDate = cstartDate.persianShortDate
        term.endDate = cendDate.persianShortDate

        val changedDb = term.getTermDataTable()

        assertEquals(cname,changedDb.name)
        assertEquals(cstartDate.persianShortDate,term.startDate)
        assertEquals(cendDate.persianShortDate,term.endDate)
        assertEquals(5, term.dayCount)
        assertEquals(2, term.dayPast)
        assertEquals(6, term.getCalenderActiveDaysList().size)
    }

}