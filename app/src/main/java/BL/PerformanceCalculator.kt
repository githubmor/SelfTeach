package BL

open class PerformanceCalculator(dayCount:Int, pasDay:Int, pageCount:Int,private val PageReaded:Int) {

    private val pageRemind = pageCount - PageReaded
    private val dayRemind = dayCount - pasDay
    private val avgPageEveryday = pageCount.toFloat() / dayCount.toFloat()
    private val pageShouldBeReadTillToday = (pasDay * avgPageEveryday).toInt()

    val avgPagePerDayRemind: Int get() = pageRemind/dayRemind
    val pageReadTo100Percent: Int get()= pageShouldBeReadTillToday-PageReaded

    val performance : Float get() =
        if(pageShouldBeReadTillToday>0) {
            ((PageReaded * 100) / pageShouldBeReadTillToday).toFloat()
        }
        else {
            0F
        }
}