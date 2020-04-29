package morteza.darzi.SelfTeach2

import BL.PerformanceCalculator
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by M on 19/04/05.
 */
class PerformanceCalculatorTest{

    @Test
    fun performance_manual_calculator_ISOK() {

        val daycount = 100
        val pasDay = 20
        val pageCount = 500
        val pageRead = 50
        val performanceBook = PerformanceCalculator(daycount,pasDay,pageCount,pageRead)

        val pageRemind = 450 //pageCount - pageRead
        val dayRemind = 80 //daycount - pasDay
        val avgPageEveryday = 5 //pageCount.toFloat() / daycount.toFloat()
        val pageShouldBeReadTillToday = 100 //(pasDay * avgPageEveryday).toInt()

        assertEquals(6,performanceBook.avgPagePerDayRemind)
        assertEquals(50,performanceBook.pageReadTo100Percent)
        assertEquals(50.0F,performanceBook.performance)
    }

    @Test
    fun performance_pageRead_Zero() {

        val daycount = 100
        val pasDay = 30
        val pageCount = 200
        val pageRead = 0 // هنوز شروع به خواندن نکرده
        val performanceBook = PerformanceCalculator(daycount,pasDay,pageCount,pageRead)

        val pageRemind = 200 //pageCount - pageRead
        val dayRemind = 70 //daycount - pasDay
        val avgPageEveryday = 2 //pageCount.toFloat() / daycount.toFloat()
        val pageShouldBeReadTillToday = 60 //(pasDay * avgPageEveryday).toInt()

        assertEquals(3,performanceBook.avgPagePerDayRemind)
        assertEquals(60,performanceBook.pageReadTo100Percent)
        assertEquals(0F,performanceBook.performance)
    }
    @Test
    fun performance_pageShouldBeReadTillToday_zero() {

        val daycount = 105
        val pasDay = 30
        val pageCount = 3
        val pageRead = 1
        val performanceBook = PerformanceCalculator(daycount,pasDay,pageCount,pageRead)

        val pageRemind = 32 //pageCount - pageRead
        val dayRemind = 75 //daycount - pasDay
        val avgPageEveryday = 0.028 //pageCount.toFloat() / daycount.toFloat()
        val pageShouldBeReadTillToday = 0 //(pasDay * avgPageEveryday).toInt()

        assertEquals(0,performanceBook.avgPagePerDayRemind)
        assertEquals(0,performanceBook.pageReadTo100Percent)
        assertEquals(100F,performanceBook.performance)
    }
    @Test
    fun performance_pageRemind_zero() {

        val daycount = 100
        val pasDay = 20
        val pageCount = 50
        val pageRead = 50
        val performanceBook = PerformanceCalculator(daycount,pasDay,pageCount,pageRead)

        val pageRemind = 0 //pageCount - pageRead
        val dayRemind = 80 // daycount - pasDay
        val avgPageEveryday = 0.5 // pageCount.toFloat() / daycount.toFloat()
        val pageShouldBeReadTillToday = 10 // (pasDay * avgPageEveryday).toInt()

        assertEquals(0,performanceBook.avgPagePerDayRemind)
        assertEquals(0,performanceBook.pageReadTo100Percent)
        assertEquals(100F,performanceBook.performance)
    }
    @Test
    fun performance_pasDay_zero() {

        val daycount = 100
        val pasDay = 0
        val pageCount = 500
        val pageRead = 50
        val performanceBook = PerformanceCalculator(daycount,pasDay,pageCount,pageRead)

        val pageRemind = 450 //pageCount - pageRead
        val dayRemind = 100 // daycount - pasDay
        val avgPageEveryday = 5 // pageCount.toFloat() / daycount.toFloat()
        val pageShouldBeReadTillToday = 0 // (pasDay * avgPageEveryday).toInt()

        assertEquals(5,performanceBook.avgPagePerDayRemind)
        assertEquals(0,performanceBook.pageReadTo100Percent)
        assertEquals(100F,performanceBook.performance)
    }

    @Test
    fun performance_dayRemind_zero() {

        val daycount = 100
        val pasDay = 100
        val pageCount = 50
        val pageRead = 30
        val performanceBook = PerformanceCalculator(daycount,pasDay,pageCount,pageRead)

        val pageRemind = 20 //pageCount - pageRead
        val dayRemind = 0 // daycount - pasDay
        val avgPageEveryday = 0.5 // pageCount.toFloat() / daycount.toFloat()
        val pageShouldBeReadTillToday = 50 // (pasDay * avgPageEveryday).toInt()

        assertEquals(20,performanceBook.avgPagePerDayRemind)
        assertEquals(20,performanceBook.pageReadTo100Percent)
        assertEquals(60F,performanceBook.performance)
    }

}