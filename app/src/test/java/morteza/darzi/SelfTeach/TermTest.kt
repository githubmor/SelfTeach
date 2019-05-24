package morteza.darzi.SelfTeach

import BL.Term
import DAL.Termdb
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by M on 19/04/05.
 */
class TermTest{

    @Test
    fun term_Create_IsOk() {

        val name = termType.nimsalAvl.name
        val startDate = PersianCalendar().apply {  add(PersianCalendar.MONTH,-1)}.persianShortDate
        val endDate = PersianCalendar().apply { add(PersianCalendar.MONTH,2)}.persianShortDate

        val db = Termdb(1,name,startDate,endDate)

        val term = Term(db)

        assertEquals(name,term.type)
        assertEquals(startDate,term.startDate)
        assertEquals(endDate,term.endDate)
        assertEquals(92,term.dayCount) // ممكنه 91 روز هم بشه در شش ماهه دوم
        assertEquals(31,term.dayPast)
        assertEquals(61,term.dayRemind)
        assertEquals(92,term.getTermDaysList().size)
        assertEquals("( 31/92 ) روز",term.termDateState)
    }

    @Test
    fun term_Edit_IsOk() {

        val name = termType.nimsalAvl.name
        val startDate = PersianCalendar().apply {  add(PersianCalendar.MONTH,-1)}.persianShortDate
        val endDate = PersianCalendar().apply { add(PersianCalendar.MONTH,2)}.persianShortDate

        val db = Termdb(1,name,startDate,endDate)

        val term = Term(db)

        val cname = termType.termTabestan.name
        val cstartDate = PersianCalendar().apply {  add(PersianCalendar.MONTH,-2)}.persianShortDate
        val cendDate = PersianCalendar().apply { add(PersianCalendar.MONTH,3)}.persianShortDate

        term.type = cname
        term.startDate = cstartDate
        term.endDate = cendDate

        val changedDb = term.db

        assertEquals(cname,changedDb.name)
        assertEquals(cstartDate,term.startDate)
        assertEquals(cendDate,term.endDate)
        assertEquals(154,term.dayCount) // ممكنه 91 روز هم بشه در شش ماهه دوم
        assertEquals(62,term.dayPast)
        assertEquals(92,term.dayRemind)
        assertEquals(154,term.getTermDaysList().size)
        assertEquals("( 62/154 ) روز",term.termDateState)
    }

}