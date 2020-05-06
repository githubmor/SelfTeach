package BL

open class Performance(dayCount: Int, pasDay: Int, pageCount: Int, PageReaded: Int) {

    private val pageRemind = pageCount - PageReaded
    private val dayRemind = dayCount - pasDay
    private val avgPageEveryday = pageCount.toFloat() / dayCount.toFloat()
    private val readTillToday = (pasDay * avgPageEveryday).toInt()

    val avgPagePerDayRemind =
            if (dayRemind > 0)
                Math.round(pageRemind.toFloat() / dayRemind.toFloat())
            else
                pageRemind

    val pageReadTo100Percent =
            if (PageReaded < readTillToday)
                readTillToday - PageReaded
            else
                0


    val performance =
            if (PageReaded < readTillToday) {
                if (readTillToday > 0) {
                    ((PageReaded * 100) / readTillToday).toFloat()
                } else {
                    0F
                }
            } else
                100F
    val pageReadPercent = (PageReaded * 100) / pageCount.toFloat()

}