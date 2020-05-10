package morteza.darzi.SelfTeach2

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

        assertEquals("1399/07/01",aval)
        assertEquals("1399/11/01",dovom)
        assertEquals("",manual)
        assertEquals("1399/04/01",tabestan)

    }

    @Test
    fun getEndDate_IsOk() {

        val aval = Ultility.getEndDate(TermType.NimsalAvl)
        val dovom = Ultility.getEndDate(TermType.NimsalDovom)
        val manual = Ultility.getEndDate(TermType.TermManual)
        val tabestan = Ultility.getEndDate(TermType.TermTabestan)

        assertEquals("1399/10/30",aval)
        assertEquals("1399/03/31",dovom)
        assertEquals("",manual)
        assertEquals("1399/06/31",tabestan)

    }



}