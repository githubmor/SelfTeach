package morteza.darzi.SelfTeach2

import BL.Performance
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by M on 19/04/05.
 */
class PerformanceTest{

    @Test
    fun performance_manual_calculator_ISOK() {

        val performanceBook = Performance(100, 20, 500, 50)

        assertEquals(6, performanceBook.avgPagePerDayRemind)//450/80 = 5.545 = 6
        assertEquals(50, performanceBook.pageReadTo100Percent) // 500/100 *20 - 50
        assertEquals(50.0F, performanceBook.performance)// 500/100*20 *100 / 50
    }

    @Test
    fun performance_termStart_readZero() {

        val performanceBook = Performance(100, 20, 500, 0)

        assertEquals(6, performanceBook.avgPagePerDayRemind)
        assertEquals(100, performanceBook.pageReadTo100Percent)
        assertEquals(0F,performanceBook.performance)
    }
    @Test
    fun performance_termStart_readBiggerThanAvgShouldBeRead() {

        val performanceBook = Performance(100, 20, 500, 300)

        assertEquals(3, performanceBook.avgPagePerDayRemind)
        assertEquals(0,performanceBook.pageReadTo100Percent)
        assertEquals(100F,performanceBook.performance)
    }

    @Test
    fun performance_termStart_reaAll() {

        val performanceBook = Performance(100, 20, 500, 500)

        assertEquals(0,performanceBook.avgPagePerDayRemind)
        assertEquals(0,performanceBook.pageReadTo100Percent)
        assertEquals(100F,performanceBook.performance)
    }

    @Test
    fun performance_termNotStart_notRead() {

        val performanceBook = Performance(100, 0, 500, 0)

        assertEquals(0, performanceBook.avgPagePerDayRemind)
        assertEquals(0, performanceBook.pageReadTo100Percent)
        assertEquals(0F, performanceBook.performance)
    }

    @Test
    fun performance_termNotStart_readBit() {

        val performanceBook = Performance(100, 0, 500, 100)

        assertEquals(0, performanceBook.avgPagePerDayRemind)
        assertEquals(0, performanceBook.pageReadTo100Percent)
        assertEquals(20F, performanceBook.performance)//نسبت خواندن به خواندن كل
    }

    @Test
    fun performance_termEnd_readAll() {

        val performanceBook = Performance(100, 100, 500, 500)

        assertEquals(0, performanceBook.avgPagePerDayRemind)
        assertEquals(0, performanceBook.pageReadTo100Percent)
        assertEquals(100F, performanceBook.performance)
    }
    @Test
    fun performance_termEnd_notRead() {

        val performanceBook = Performance(100, 100, 500, 0)

        assertEquals(500, performanceBook.avgPagePerDayRemind)
        assertEquals(500, performanceBook.pageReadTo100Percent)
        assertEquals(0F, performanceBook.performance)
    }

    @Test
    fun performance_termEnd_readBit() {

        val performanceBook = Performance(100, 100, 500, 100)

        assertEquals(400, performanceBook.avgPagePerDayRemind)
        assertEquals(400, performanceBook.pageReadTo100Percent)
        assertEquals(20F, performanceBook.performance)
    }


}