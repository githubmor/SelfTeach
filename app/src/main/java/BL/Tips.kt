package BL

class Tips(val performance: Performance){




//    fun pageToReadToday(): Int {
//        if (performancePercent() < 100) {
//            val pageRemindToAvreg = pageShouldReadTillToday() - allPageWasRead()
//            return when {
//                pageRemindToAvreg > 10 -> //Less than 10 = Nothing to Show
//                    when {
//                        pageRemindToAvreg * 4 < avgAllPageCountWasReadPerEveryRead() -> //Less than 4 plus UserPagePerDay = Nothing to Show
//                            0
//                        pageRemindToAvreg > avgAllPageCountWasReadPerEveryRead() * 3 -> //Biger than 3 plus UserPagePerDay = User Shuld Have Plane To Read_Old , its very big
//                            pageRemindToAvreg + 1000//just for realse
//                        else -> //ok Read_Old i Page To reach 100 percent
//                            pageRemindToAvreg
//                    }
//                avgAllPageCountWasReadPerEveryRead() <= 10 -> //if page to reach is below 10 but My User also very Slow ...
//                    pageRemindToAvreg
//                else -> //this pageRemindToAvreg is very bit = nothing to show ...
//                    0
//            }
//        } else {
//            return 0//nothing show
//        }
//    }
//
//
//
//
//
//    private fun avgAllPageCountWasReadPerEveryRead(): Int {
//        val c =books.sumBy { it.dbDto.reads.count() }
//        return if (c>0) {
//            allPageWasRead() / c
//        } else 0
//    }
//
//    private fun avgAllPagePerDayInTerm(): Int {
//        return if (term.DayCount() > 0) {
//            allPageCount() / term.DayCount()
//        } else {
//            0
//        }
//    }
//
//    fun performancePercent(): Int {
//        return if (term.DayPast(now) > 0 && avgAllPagePerDayInTerm() > 0) {
//            allPageWasRead() * 100 / pageShouldReadTillToday()
//        } else {
//            0
//        }
//    }
//
//    private fun pageShouldReadTillToday() = term.DayPast(now) * avgAllPagePerDayInTerm()
//
//    private fun allPageWasRead() = books.sumBy { it.pageWasReaded() }
//
//    private fun allPageCount()= books.sumBy { it.pageCount }
//
//    fun pagePerDayRemind(): Int {
//        val i: Int = if (term.DayRemind(now) > 0) {
//            (allPageCount() - allPageWasRead()) / term.DayRemind(now)// this i is PagePerDay should read till term_old endDate
//        } else {
//            0
//        }
//        return if (i > 5 * avgAllPageCountWasReadPerEveryRead() && term.dayPastPercent(now) > 60) {
//            i + 1000//means it is too big , make a plan for read ..
//        } else i
//    }

}
