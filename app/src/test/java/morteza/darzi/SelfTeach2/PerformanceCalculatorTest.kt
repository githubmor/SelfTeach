package morteza.darzi.SelfTeach2

import BL.PerformanceCalculator
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by M on 19/04/05.
 */
class PerformanceCalculatorTest{

    @Test
    fun performance_calculator_ISOK() {

        val daycount = 100
        val pasDay = 20
        val pageCount = 500
        val pageRead = 50
        val performanceBook = PerformanceCalculator(daycount,pasDay,pageCount,pageRead)

        val pageRemind = 500-50
        val dayRemind = 100-20
        val avgPageEveryday = 500/100
        val pageShouldBeReadTillToday = 20*5

        assertEquals(5,performanceBook.avgPagePerDayRemind)
        assertEquals(50,performanceBook.pageReadTo100Percent)
        assertEquals(50F,performanceBook.performance)
    }
    @Test
    fun performance_NotClearNumber_ISOK() {

        val daycount = 105
        val pasDay = 30
        val pageCount = 274
        val pageRead = 84
        val performanceBook = PerformanceCalculator(daycount,pasDay,pageCount,pageRead)

        val pageRemind = pageCount - pageRead
        val dayRemind = daycount - pasDay
        val avgPageEveryday = pageCount.toFloat() / daycount.toFloat()
        val pageShouldBeReadTillToday = (pasDay * avgPageEveryday).toInt()

        assertEquals(pageRemind/dayRemind,performanceBook.avgPagePerDayRemind)
        assertEquals(pageShouldBeReadTillToday-pageRead,performanceBook.pageReadTo100Percent)
        assertEquals(((pageRead*100)/pageShouldBeReadTillToday).toFloat(),performanceBook.performance)
    }
    @Test
    fun performance_ZeroNumber_ISOK() {

        val daycount = 100
        val pasDay = 30
        val pageCount = 200
        val pageRead = 0
        val performanceBook = PerformanceCalculator(daycount,pasDay,pageCount,pageRead)

        val pageRemind = 200 - 0
        val dayRemind = 100 - 30
        val avgPageEveryday = 200 / 100
        val pageShouldBeReadTillToday = 30 * 2

        assertEquals(200/70,performanceBook.avgPagePerDayRemind)
        assertEquals(60-0,performanceBook.pageReadTo100Percent)
        assertEquals(0,performanceBook.performance)
    }
    @Test
    fun performance_ZeroPageCountNumber_ISOK() {

        val daycount = 105
        val pasDay = 30
        val pageCount = 3
        val pageRead = 1
        val performanceBook = PerformanceCalculator(daycount,pasDay,pageCount,pageRead)

        val pageRemind = pageCount - pageRead
        val dayRemind = daycount - pasDay
        val avgPageEveryday = pageCount.toFloat() / daycount.toFloat()
        val pageShouldBeReadTillToday = (pasDay * avgPageEveryday).toInt()

        assertEquals(pageRemind/dayRemind,performanceBook.avgPagePerDayRemind)
        assertEquals(pageShouldBeReadTillToday-pageRead,performanceBook.pageReadTo100Percent)
        assertEquals(0F,performanceBook.performance)
    }
    @Test
    fun performance_ReadAllPage() {

        val daycount = 100
        val pasDay = 20
        val pageCount = 50
        val pageRead = 50
        val performanceBook = PerformanceCalculator(daycount,pasDay,pageCount,pageRead)

        val pageRemind = pageCount - pageRead
        val dayRemind = daycount - pasDay
        val avgPageEveryday = pageCount.toFloat() / daycount.toFloat()
        val pageShouldBeReadTillToday = (pasDay * avgPageEveryday).toInt()

        assertEquals(pageRemind/dayRemind,performanceBook.avgPagePerDayRemind)
        assertEquals(pageShouldBeReadTillToday-pageRead,performanceBook.pageReadTo100Percent)
        assertEquals(((pageRead*100)/pageShouldBeReadTillToday).toFloat(),performanceBook.performance)
    }
    @Test
    fun performance_error_pageRead_IsOver() {

        val daycount = 100
        val pasDay = 20
        val pageCount = 50
        val pageRead = 60
        val performanceBook = PerformanceCalculator(daycount,pasDay,pageCount,pageRead)

        val pageRemind = pageCount - pageRead
        val dayRemind = daycount - pasDay
        val avgPageEveryday = pageCount.toFloat() / daycount.toFloat()
        val pageShouldBeReadTillToday = (pasDay * avgPageEveryday).toInt()

        assertEquals(pageRemind/dayRemind,performanceBook.avgPagePerDayRemind)
        assertEquals(pageShouldBeReadTillToday-pageRead,performanceBook.pageReadTo100Percent)
        assertEquals(((pageRead*100)/pageShouldBeReadTillToday).toFloat(),performanceBook.performance)
    }

    @Test
    fun performance_error_pasDay_IsOver() {

        val daycount = 100
        val pasDay = 110
        val pageCount = 50
        val pageRead = 30
        val performanceBook = PerformanceCalculator(daycount,pasDay,pageCount,pageRead)

        val pageRemind = pageCount - pageRead
        val dayRemind = daycount - pasDay
        val avgPageEveryday = pageCount.toFloat() / daycount.toFloat()
        val pageShouldBeReadTillToday = (pasDay * avgPageEveryday).toInt()

        assertEquals(pageRemind/dayRemind,performanceBook.avgPagePerDayRemind)
        assertEquals(pageShouldBeReadTillToday-pageRead,performanceBook.pageReadTo100Percent)
        assertEquals(((pageRead*100)/pageShouldBeReadTillToday).toFloat(),performanceBook.performance)
    }

    @Test
    fun performance_by_Constant_Number() {

        val daycount = 100
        val pasDay = 20
        val pageCount = 200
        val pageRead = 120
        val performanceBook = PerformanceCalculator(daycount,pasDay,pageCount,pageRead)

        val pageRemind = 80
        val dayRemind = 80
        val pageShouldBeReadTillToday = 40

        assertEquals(pageRemind/dayRemind,performanceBook.avgPagePerDayRemind)
        assertEquals(pageShouldBeReadTillToday-pageRead,performanceBook.pageReadTo100Percent)
        assertEquals(((pageRead*100)/pageShouldBeReadTillToday).toFloat(),performanceBook.performance)
    }
}