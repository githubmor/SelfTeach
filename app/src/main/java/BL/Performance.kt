package BL

open class Performance(private val dayCount: Int, private val pasDay: Int, private val pageCount: Int, pageReaded: Int) {

    private val pageRemind = pageCount - pageReaded
    private val dayRemind = dayCount - pasDay
    private val avgPageEveryday = pageCount.toFloat() / dayCount.toFloat()
    private val shouldReadTillToday = if (pasDay > 0) (pasDay * avgPageEveryday).toInt() else pageRemind

    fun termVaziat(): Int {
        return 0 // 0 ... number ... daycount
    }

    val avgPagePerDayRemind =
                if (dayRemind > 0)
                    Math.round(pageRemind.toFloat() / dayRemind.toFloat())
                else
                    pageRemind

    val pageReadTo100Percent =
            if (pageReaded < shouldReadTillToday)
                shouldReadTillToday - pageReaded
            else
                0


    val performance =
            if (pageReaded < shouldReadTillToday) {
                if (shouldReadTillToday > 0) {
                    ((pageReaded * 100) / shouldReadTillToday).toFloat()
                } else {
                    0F
                }
            } else
                100F
    val pageReadPercent = (pageReaded * 100) / pageCount.toFloat()

}