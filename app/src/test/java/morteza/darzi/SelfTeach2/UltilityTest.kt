package morteza.darzi.SelfTeach2

import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import core.Ultility
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by M on 19/04/05.
 */
class UltilityTest{

    @Test
    fun getStartDate_IsOk() {

        val aval = Ultility.getStartDate(TermType.NimsalAvl)
        val dovom = Ultility.getStartDate(TermType.NimsalDovom)
        val manual = Ultility.getStartDate(TermType.TermManual)
        val tabestan = Ultility.getStartDate(TermType.TermTabestan)

        assertEquals("1399/07/01", aval)
        assertEquals("1399/11/01", dovom)
        assertEquals("", manual)
        assertEquals("1399/04/01", tabestan)

    }

    @Test
    fun getEndDate_IsOk() {

        val aval = Ultility.getEndDate(TermType.NimsalAvl)
        val dovom = Ultility.getEndDate(TermType.NimsalDovom)
        val manual = Ultility.getEndDate(TermType.TermManual)
        val tabestan = Ultility.getEndDate(TermType.TermTabestan)

        assertEquals("1399/10/30", aval)
        assertEquals("1400/03/31", dovom)
        assertEquals("", manual)
        assertEquals("1399/06/31", tabestan)

    }


    @Test
    fun persionDate_IsOk() {
        var c = MyPersia()
        c.parse("1399/11/12")

        assertEquals(1399, c.persianYear)
        assertEquals(11, c.persianMonth)
        assertEquals(12, c.persianDay)
        assertEquals("1399/11/12", c.persianShortDate)
    }

}

class MyPersia : PersianCalendar() {
    private fun formatToMilitary(i: Int): String {
        return if (i < 9) "0$i" else i.toString()
    }

    override fun getPersianShortDate(): String {


        // calculatePersianDate();
        return "" + formatToMilitary(persianYear) + delimiter + formatToMilitary(persianMonth) + delimiter + formatToMilitary(persianDay)
    }
}