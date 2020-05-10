package core

open class Performance(dayCount: Int, pasDay: Int, pageCount: Int, pageReaded: Int) {

    private val pageRemind = pageCount - pageReaded
    private val dayRemind = dayCount - pasDay
    private val avgPageEveryday = pageCount.toFloat() / dayCount.toFloat()
    private val shouldReadTillToday = if (pasDay > 0) (pasDay * avgPageEveryday).toInt() else pageRemind

    val avgPagePerDayRemind =
            when (dayRemind) {
                dayCount -> 0 //not start term
                0 -> pageRemind //end term
                else -> Math.round(pageRemind.toFloat() / dayRemind.toFloat())
            }

    val pageReadTo100Percent =
            when {
                dayRemind == dayCount -> 0
                dayRemind == 0 -> pageRemind
                pageReaded < shouldReadTillToday -> shouldReadTillToday - pageReaded
                else -> 0
            }

    val pageReadPercent: Float = (pageReaded * 100) / pageCount.toFloat()

    val performance =
            when {
                dayRemind == dayCount -> pageReadPercent
                dayRemind == 0 -> pageReadPercent
                pageReaded < shouldReadTillToday -> if (shouldReadTillToday > 0) {
                    (pageReaded * 100) / shouldReadTillToday.toFloat()
                } else {
                    0F
                }
                else -> 100F
            }


}