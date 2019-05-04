package BL

import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table

/**
 * Created by M on 14/11/23.
 */

@Table(name = "Terms")
class Term : Model {

    @Column(name = "termName")
    var termName: String? = null

    @Column(name = "startDate")
    var startDate: String? = null

    @Column(name = "endDate")
    var endDate: String? = null


    var now: String? = null


    constructor() : super()

    @Throws(MyException::class)
    constructor(termName: String, startDate: String, endDate: String) : super() {
        if (startDate != "" && endDate != "") {
            if (CheckDate(startDate, endDate)) {
                throw MyException("تاریخ شروع بعد از تاریخ پایان است !لطفا اصلاح کنید")
            } else {
                this.termName = termName
                this.startDate = startDate
                this.endDate = endDate
            }
        } else {
            throw MyException("لطفا تاریخ را وارد کنید")
        }

    }


    fun DayCount(): Int {
        return DaysDiffCalculate(startDate!!, endDate!!)
    }


    fun DayPast(): Int {
        return DaysDiffCalculate(startDate!!, now!!)
    }

    fun DayRemind(): Int {
        return DayCount() - DayPast()
    }


    // --Commented out by Inspection START (15/02/02 22:42):
    //    public int DayPercentPast(){
    //        if (DayCount()>0) {
    //            return (DayPast() * 100) / DayCount();
    //        }else {
    //            return 0;
    //        }
    //    }
    // --Commented out by Inspection STOP (15/02/02 22:42)


    private fun DaysDiffCalculate(StartDate: String, EndDate: String): Int {

        val Start = JDF()
        val End = JDF()

        Start.iranianDate = StartDate
        End.iranianDate = EndDate

        return End.dayCount - Start.dayCount + 1
    }


    private fun CheckDate(start: String, end: String): Boolean {
        val s = JDF()
        val e = JDF()
        s.iranianDate = start
        e.iranianDate = end
        return e.dayCount - s.dayCount <= 0

    }

    @Throws(MyException::class)
    fun setStartAndEndDate(startDate: String, endDate: String) {
        if (startDate != "" && endDate != "") {
            if (CheckDate(startDate, endDate)) {
                throw MyException("تاریخ شروع بعد از پایان است ! لطفا اصلاح کنید")
            } else {
                this.startDate = startDate
                this.endDate = endDate
            }
        } else {
            throw MyException("لطفا تاریخ را وارد کنید")
        }
    }
}
