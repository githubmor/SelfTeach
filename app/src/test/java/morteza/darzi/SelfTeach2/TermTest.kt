package morteza.darzi.SelfTeach2

import BL.Term
import DAL.TermDataTable
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianDateParser
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by M on 19/04/05.
 */
class TermTest{

    @Test
    fun CreatTermWithStartAndEndDate() {

        val name = TermType.NimsalAvl
        val startDate = PersianCalendar().apply {  add(PersianCalendar.MONTH,-1)}
        val endDate =  PersianCalendar().apply { add(PersianCalendar.MONTH,2)}

        val dayCount = daysDiffCalculate(startDate.persianShortDate,endDate.persianShortDate)
        val pas = daysDiffCalculate(startDate.persianShortDate,PersianCalendar().persianShortDate)
        val remi = dayCount-pas

        val db = TermDataTable(1, name.name, startDate.persianShortDate, endDate.persianShortDate)

        val term = Term(db)

        assertEquals(name.typeName,term.type)
        assertEquals(startDate.persianShortDate,term.startDate)
        assertEquals(endDate.persianShortDate,term.endDate)
        assertEquals(dayCount,term.dayCount)
        assertEquals(pas,term.dayPast)
        assertEquals(remi,term.dayRemind)
        assertEquals(dayCount, term.getCalenderActiveDaysList().size)
        assertEquals("( "+pas+"/"+dayCount+" ) روز",term.termDateState)
    }

    private fun daysDiffCalculate(s: String, e: String): Int {

        val start = PersianDateParser(s).persianDate.timeInMillis
        val end = PersianDateParser(e).persianDate.timeInMillis

        val re = end - start

        return ((re/(1000*60*60*24))+1).toInt()
    }

    @Test
    fun term_Edit_IsOk() {

        val name = TermType.NimsalAvl.name
        val startDate = PersianCalendar().apply {  add(PersianCalendar.MONTH,-1)}
        val endDate = PersianCalendar().apply { add(PersianCalendar.MONTH,2)}


        val db = TermDataTable(1, name, startDate.persianShortDate, endDate.persianShortDate)

        val term = Term(db)

        val cname = TermType.TermTabestan.name
        val cstartDate = PersianCalendar().apply {  add(PersianCalendar.MONTH,-2)}
        val cendDate = PersianCalendar().apply { add(PersianCalendar.MONTH,3)}

        val dayCount = daysDiffCalculate(cstartDate.persianShortDate,cendDate.persianShortDate)
        val pas = daysDiffCalculate(cstartDate.persianShortDate,PersianCalendar().persianShortDate)
        val remi = dayCount-pas

        term.type = cname
        term.startDate = cstartDate.persianShortDate
        term.endDate = cendDate.persianShortDate

        val changedDb = term.termDataTable

        assertEquals(cname,changedDb.name)
        assertEquals(cstartDate.persianShortDate,term.startDate)
        assertEquals(cendDate.persianShortDate,term.endDate)
        assertEquals(dayCount,term.dayCount) // ممكنه 91 روز هم بشه در شش ماهه دوم
        assertEquals(pas,term.dayPast)
        assertEquals(remi,term.dayRemind)
        assertEquals(dayCount, term.getCalenderActiveDaysList().size)
        assertEquals("( "+pas+"/"+dayCount+" ) روز",term.termDateState)
    }

}