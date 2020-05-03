package BL

open class PerformanceCalculator(dayCount:Int, pasDay:Int,private val pageCount:Int,private val PageReaded:Int) {

    private val pageRemind = pageCount - PageReaded
    private val dayRemind = dayCount - pasDay
    private val avgPageEveryday = pageCount.toFloat() / dayCount.toFloat()
    private val pageShouldBeReadTillToday = (pasDay * avgPageEveryday).toInt()

    val avgPagePerDayRemind: Int get() =
        if (dayRemind > 0)
            Math.round(pageRemind.toFloat()/dayRemind.toFloat())
        else
            pageRemind

    val pageReadTo100Percent: Int get()=
        if (PageReaded < pageShouldBeReadTillToday)
           pageShouldBeReadTillToday - PageReaded
        else
            0


    val performance : Float get() =
        if (PageReaded<pageShouldBeReadTillToday) {
            if (pageShouldBeReadTillToday > 0) {
                ((PageReaded * 100) / pageShouldBeReadTillToday).toFloat()
            } else {
                0F
            }
        }else
            100F
    val pageReadPercent: Float
        get() {
            return (PageReaded * 100) / pageCount.toFloat()
        }
}