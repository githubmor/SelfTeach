package BL

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class Teacher(termRepository: TermRepository, bookRepository: BookRepository):
CoroutineScope by MainScope(){

    lateinit var term: Term
    lateinit var books : List<Book>

    init {
        launch {
            term = Term(termRepository.getTerm())
            books = bookRepository.getAllBookWithRead().map { Book(it) }
        }
    }

    fun PageTo100Percent_old(now: String): Int {
        if (Performance(now) < 100) {
            val i = term.DayPast(now) * PPD() - AllPageWasRead()
            return if (i > 10) {//Less than 10 = Nothing to Show
                if (i * 4 < UserPPDRead()) {//Less than 4 plus UserPagePerDay = Nothing to Show
                    0
                } else if (i > UserPPDRead() * 3) {//Biger than 3 plus UserPagePerDay = User Shuld Have Plane To Read_Old , its very big
                    i + 1000//just for realse
                } else {//ok Read_Old i Page To reach 100 percent
                    i
                }
            } else if (UserPPDRead() <= 10) {//if page to reach is below 10 but My User also very Slow ...
                i
            } else {//this i is very bit = nothing to show ...
                0
            }
        } else {
            return 0//nothing show
        }
    }

    fun BookNameForRead(now : String): String? {
        var max = 0
        var re: String? = ""
        if (!books.isEmpty()) {
            for (b in books) {
                //if page to 100 less than userppd for that bookOld , no bookOld to show
                if (b.needForRead(term.DayCount(),term.DayPast(now),max)>0) {
                    re = b.name
                    max = b.needForRead(term.DayCount(),term.DayPast(now),max)
                }
            }
        }

        return re
    }

    fun UserPPDRead(): Int {
        val c =books.sumBy { it.dbDto.reads.count() }
        return if (c>0) {
            AllPageWasRead() / c
        } else 0
    }

    private fun PPD(): Int {
        return if (term.DayCount() > 0) {
            AllPageCount() / term.DayCount()
        } else {
            0
        }
    }

    fun Performance(now :String): Int {
        return if (term.DayPast(now) > 0 && PPD() > 0) {
            AllPageWasRead() * 100 / (term.DayPast(now) * PPD())
        } else {
            0
        }
    }

    private fun AllPageWasRead(): Int {
        return books.sumBy { it.PageWasReaded() }
    }

    private fun AllPageCount(): Int {
        return books.sumBy { it.pageCount }
    }

    fun OtherBookForRead(now :String): String? {
        var max = 0
        val f1 = BookNameForRead(now)
        var re: String? = ""
        if ("" != f1) {
            for (b in books) {
                if (b.name != f1) {
                    if (b.needForRead(term.DayCount(),term.DayPast(now),max)>0) {
                        re = b.name
                        max = b.needForRead(term.DayCount(),term.DayPast(now),max)
                    }
                }
            }
        }

        return re

    }

    fun TermDayPercent(now :String): Int {
        return if (term.DayCount() > 0) {
            term.DayPast(now) * 100 / term.DayCount()
        } else {
            0
        }
    }

    fun PagePerDayRemind(now :String): Int {
        val i: Int
        if (term.DayRemind(now) > 0) {
            i = (AllPageCount() - AllPageWasRead()) / term.DayRemind(now)// this i is PagePerDay should read till term_old endDate
        } else {
            i = 0
        }
        return if (i > 5 * UserPPDRead() && TermDayPercent(now) > 60) {
            i + 1000//means it is too big , make a plan for read ..
        } else i
    }

    fun BooksNeedPlane(now :String): String? {
        var re: String? = ""
        if (term.DayRemind(now) > 0) {
            for (book in books) {
                val i = book.PageRemind() / term.DayRemind(now)// this i is PagePerDay should read till term_old endDate
                if (i > 5 * book.UserPPDReadBook() && TermDayPercent(now) > 60) {
                    if (re != "")
                        re = re + " و" + book.name//means it is too big , make a plan for read ..
                    else
                        re = book.name
                }
            }
        }
        return re
    }


    private fun DaysDiffCalculate(StartDate: String, EndDate: String): Int {

        val Start = JDF()
        val End = JDF()

        Start.iranianDate = StartDate
        End.iranianDate = EndDate

        return End.dayCount - Start.dayCount + 1
    }

}
