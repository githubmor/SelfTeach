package BL

open class Performance(private val dayCount: Int, private val pasDay: Int, private val pageCount: Int, pageReaded: Int) {

    private val pageRemind = if (termVaziat() >= 0) pageCount - pageReaded else pageCount
    private val dayRemind = if (termVaziat() == 0) dayCount - pasDay else 0
    private val avgPageEveryday = if (dayCount > 0) pageCount.toFloat() / dayCount.toFloat() else 0F
    private val shouldReadTillToday = if (termVaziat() == 0) (pasDay * avgPageEveryday).toInt() else if (termVaziat() == -1) 0 else pageCount

    private fun termVaziat(): Int {
        return if (pasDay in 0..dayCount) 0 else if (pasDay < 0) -1 else 1 // -1 : not start, 0 : start , 1 : after end
    }

    val avgPagePerDayRemind =
            if (termVaziat() == 0)
                if (dayRemind > 0)
                    Math.round(pageRemind.toFloat() / dayRemind.toFloat())
                else
                    pageRemind
            else if (termVaziat() == -1)
                0
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