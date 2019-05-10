package BL

import com.activeandroid.query.Select
import java.util.*

/**
 * Created by M on 14/12/31.
 */
class Teacher {

    val term: Term_old? = Select().from(Term_old::class.java).executeSingle()

    val bookOlds: List<Book_Old> = Select().from(Book_Old::class.java).where("free = " + 0).execute()
    private val reads: MutableList<Read>
    private var now: String? = null

    init {
        reads = ArrayList()

        if (IsBooksSet()!!) {
            SetReadsToBooks()
        }
    }

    fun BookPerformance(bookOld: Book_Old): Int {
        return if (term!!.DayPast() > 0 && PPDBook(bookOld) > 0) {
            bookOld.PageWasReaded() * 100 / (term.DayPast() * PPDBook(bookOld))
        } else {
            0
        }
    }

    fun BookPageTo100Percent(bookOld: Book_Old): Int {
        val i = term!!.DayPast() * PPDBook(bookOld) - bookOld.PageWasReaded()
        return if (i >= 10) {
            if (i * 4 < UserPPDReadBook(bookOld)) {
                0
            } else {
                i
            }
        } else if (UserPPDReadBook(bookOld) >= 0) {
            i
        } else {
            0
        }
    }

    private fun UserPPDReadBook(bookOld: Book_Old): Int {
        return if (bookOld.reads.size > 0) {
            bookOld.PageWasReaded() / bookOld.reads.size
        } else {
            0
        }
    }


    fun PageTo100Percent(): Int {
        if (Performance() < 100) {
            val i = term!!.DayPast() * PPD() - PageWasReaded()
            return if (i > 10) {//Less than 10 = Nothing to Show
                if (i * 4 < UserPPDRead()) {//Less than 4 plus UserPagePerDay = Nothing to Show
                    0
                } else if (i > UserPPDRead() * 3) {//Biger than 3 plus UserPagePerDay = User Shuld Have Plane To Read , its very big
                    i + 1000//just for realse
                } else {//ok Read i Page To reach 100 percent
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


    private fun PPDBook(bookOld: Book_Old): Int {
        return if (term!!.DayCount() > 0) {
            bookOld.pageCount / term.DayCount()
        } else {
            0
        }
    }

    fun BookNameForRead(): String? {
        var max = 0
        var re: String? = ""
        if (!bookOlds.isEmpty()) {
            for (b in bookOlds) {
                //if page to 100 less than userppd for that bookOld , no bookOld to show
                if (BookPageTo100Percent(b) * 3 > UserPPDReadBook(b) && BookPageTo100Percent(b) > max) {
                    re = b.name
                    max = BookPageTo100Percent(b)
                }
            }
        }

        return re
    }

    internal fun UserPPDRead(): Int {
        return if (!reads.isEmpty()) {
            PageWasReaded() / reads.size
        } else 0
    }

    private fun PPD(): Int {
        return if (term!!.DayCount() > 0) {
            PageCount() / term.DayCount()
        } else {
            0
        }
    }

    fun Performance(): Int {
        return if (term!!.DayPast() > 0 && PPD() > 0) {
            PageWasReaded() * 100 / (term.DayPast() * PPD())
        } else {
            0
        }
    }

    private fun PageWasReaded(): Int {
        var result = 0
        if (!reads.isEmpty()) {
            for (b in reads) {
                result += b.pageReadCount
            }
        }
        return result
    }

    fun IsBooksSet(): Boolean? {
        return bookOlds.size > 0
    }

    fun IsTermSet(): Boolean? {
        return term != null
    }


    private fun SetReadsToBooks() {
        for (b in bookOlds) {
            b.LoadReads()
            reads.addAll(b.reads)
        }

    }

    private fun PageCount(): Int {
        var result = 0
        if (!bookOlds.isEmpty()) {
            for (b in bookOlds) {
                result += b.pageCount
            }
        }
        return result
    }

    fun OtherBookForRead(): String? {
        var max = 0
        val f1 = BookNameForRead()
        var re: String? = ""
        if ("" != f1) {
            for (b in bookOlds) {
                if (b.name != f1) {
                    if (BookPageTo100Percent(b) * 3 > UserPPDReadBook(b) && BookPageTo100Percent(b) > max) {
                        re = b.name
                        max = BookPageTo100Percent(b)
                    }
                }
            }
        }

        return re

    }

    fun TermDayPercent(): Int {
        return if (term!!.DayCount() > 0) {
            term.DayPast() * 100 / term.DayCount()
        } else {
            0
        }
    }

    fun PagePerDayRemind(): Int {
        val i: Int
        if (term!!.DayRemind() > 0) {
            i = (PageCount() - PageWasReaded()) / term.DayRemind()// this i is PagePerDay should read till term endDate
        } else {
            i = 0
        }
        return if (i > 5 * UserPPDRead() && TermDayPercent() > 60) {
            i + 1000//means it is too big , make a plan for read ..
        } else i
    }

    fun BooksNeedPlane(): String? {
        var re: String? = ""
        if (term!!.DayRemind() > 0) {
            for (book in bookOlds) {
                val i = book.PageRemind() / term.DayRemind()// this i is PagePerDay should read till term endDate
                if (i > 5 * UserPPDReadBook(book) && TermDayPercent() > 60) {
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

    //    public void setBooks(List<Book_Old> bookOlds) {
    //        this.bookOlds = bookOlds;
    //    }

    // --Commented out by Inspection START (15/02/02 22:42):
    //    public List<Read> getReads() {
    //        return reads;
    //    }
    // --Commented out by Inspection STOP (15/02/02 22:42)

    //    public void setReads(List<Read> reads) {
    //        this.reads = reads;
    //    }

    // --Commented out by Inspection START (15/02/02 22:42):
    //    public String getNow() {
    //        return now;
    //    }
    // --Commented out by Inspection STOP (15/02/02 22:42)

    fun setNow(now: String) {
        //        if (!now.equals("")) {
        ////            if (DaysDiffCalculate(term.getStartDate(), now) > 0 && DaysDiffCalculate(now, term.getEndDate()) > 0) {
        this.now = now
        term!!.now = now
        //            } else {
        //                this.now = now;
        //                term.setNow(now);
        //                throw new MyException("تاریخ در بازه ترم قرار ندارد");
        ////            }
        //        }else {
        //            throw new MyException("تاریخ را مشخص کنید");
        //        }


    }

    fun IsInTermRange(now: String): Boolean? {
        return if (now != "") {
            DaysDiffCalculate(term!!.startDate!!, now) > 0 && DaysDiffCalculate(now, term.endDate!!) > 0
        } else {
            false
        }
    }


}
