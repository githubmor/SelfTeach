package morteza.darzi.SelfTeach

import BL.Ultility
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by M on 19/04/05.
 */
class UltilityTest{

    @Test
    fun getStartDate_IsOk() {

        val aval = Ultility.getStartDate(termType.nimsalAvl)
        val dovom = Ultility.getStartDate(termType.nimsalDovom)
        val manual = Ultility.getStartDate(termType.termManual)
        val tabestan = Ultility.getStartDate(termType.termTabestan)

        assertEquals("1398/07/01",aval)
        assertEquals("1398/11/01",dovom)
        assertEquals("",manual)
        assertEquals("1398/04/01",tabestan)

    }

    @Test
    fun getEndDate_IsOk() {

        val aval = Ultility.getEndDate(termType.nimsalAvl)
        val dovom = Ultility.getEndDate(termType.nimsalDovom)
        val manual = Ultility.getEndDate(termType.termManual)
        val tabestan = Ultility.getEndDate(termType.termTabestan)

        assertEquals("1398/10/30",aval)
        assertEquals("1398/03/31",dovom)
        assertEquals("",manual)
        assertEquals("1398/06/31",tabestan)

    }



}