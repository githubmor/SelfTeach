package BL

import android.text.Html
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class Performance(termRepository: TermRepository, bookRepository: BookRepository, val now :String):
        CoroutineScope by MainScope(){

    lateinit var term: Term
    private lateinit var books : List<Book>

    init {
        launch {
            term = Term(termRepository.getTerm())
            books = bookRepository.getAllBookWithRead().map { Book(it) }
        }
    }



//    fun pageTo100PercentTips(): String {
//        val cout = pageCountRemindToGet100Percent()
//        return when {
//            cout <= 0 -> ""
//            cout > 1000 -> {
//                val pp2 = "<font color=\"#029789\">" + (cout - 1000).toString() + "</font>" + " صفحه امروز باید خوانده شود!. نیاز به فوق برنامه می باشد"
//                return Html.fromHtml(pp2).toString()
//            }
//            else -> {
//                val pp = "در کل امروز باید <font color=\"#029789\">$cout</font> صفحه خوانده شود . سهم هر کتاب در لیست مشخص شده"
//                return Html.fromHtml(pp).toString()
//            }
//        }
//    }

    fun pageToReadToday(): Int {
        if (performancePercent() < 100) {
            val pageRemindToAvreg = term.DayPast(now) * avgAllPagePerDayInTerm() - allPageWasRead()
            return when {
                pageRemindToAvreg > 10 -> //Less than 10 = Nothing to Show
                    when {
                        pageRemindToAvreg * 4 < avgAllPageCountWasReadPerEveryRead() -> //Less than 4 plus UserPagePerDay = Nothing to Show
                            0
                        pageRemindToAvreg > avgAllPageCountWasReadPerEveryRead() * 3 -> //Biger than 3 plus UserPagePerDay = User Shuld Have Plane To Read_Old , its very big
                            pageRemindToAvreg + 1000//just for realse
                        else -> //ok Read_Old i Page To reach 100 percent
                            pageRemindToAvreg
                    }
                avgAllPageCountWasReadPerEveryRead() <= 10 -> //if page to reach is below 10 but My User also very Slow ...
                    pageRemindToAvreg
                else -> //this i is very bit = nothing to show ...
                    0
            }
        } else {
            return 0//nothing show
        }
    }


    fun booksNeedToReadToday(): List<String> {
        return books.filter { it.needHighPriorityRead(term.DayPast(now),term.DayCount()) }
                .map { "كتاب " + it.name + " - " + it.BookPageRemindToGet100Percent(term.DayPast(now),term.DayCount()) + " صفحه" }
    }

    private fun avgAllPageCountWasReadPerEveryRead(): Int {
        val c =books.sumBy { it.dbDto.reads.count() }
        return if (c>0) {
            allPageWasRead() / c
        } else 0
    }

    private fun avgAllPagePerDayInTerm(): Int {
        return if (term.DayCount() > 0) {
            allPageCount() / term.DayCount()
        } else {
            0
        }
    }

    fun performancePercent(): Int {
        return if (term.DayPast(now) > 0 && avgAllPagePerDayInTerm() > 0) {
            allPageWasRead() * 100 / (term.DayPast(now) * avgAllPagePerDayInTerm())
        } else {
            0
        }
    }

    private fun allPageWasRead(): Int {
        return books.sumBy { it.PageWasReaded() }
    }

    private fun allPageCount(): Int {
        return books.sumBy { it.pageCount }
    }




    fun pagePerDayRemind(): Int {
        val i: Int = if (term.DayRemind(now) > 0) {
            (allPageCount() - allPageWasRead()) / term.DayRemind(now)// this i is PagePerDay should read till term_old endDate
        } else {
            0
        }
        return if (i > 5 * avgAllPageCountWasReadPerEveryRead() && term.dayPastPercent(now) > 60) {
            i + 1000//means it is too big , make a plan for read ..
        } else i
    }

}
