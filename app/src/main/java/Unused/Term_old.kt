//package BL
//
//import com.activeandroid.Model
//import com.activeandroid.annotation.Column
//import com.activeandroid.annotation.Table
//
///**
// * Created by M on 14/11/23.
// */
//
//@Table(type = "Terms")
//class Term_old : Model {
//
//    @Column(type = "type")
//    var type: String? = null
//
//    @Column(type = "startDate")
//    var startDate: String? = null
//
//    @Column(type = "endDate")
//    var endDate: String? = null
//
//
//    var now: String? = null
//
//
//    constructor() : super()
//
//    @Throws(MyException::class)
//    constructor(type: String, startDate: String, endDate: String) : super() {
//        if (startDate != "" && endDate != "") {
//            if (CheckDate(startDate, endDate)) {
//                throw MyException("تاریخ شروع بعد از تاریخ پایان است !لطفا اصلاح کنید")
//            } else {
//                this.type = type
//                this.startDate = startDate
//                this.endDate = endDate
//            }
//        } else {
//            throw MyException("لطفا تاریخ را وارد کنید")
//        }
//
//    }
//
//
//    fun dayCount(): Int {
//        return DaysDiffCalculate(startDate!!, endDate!!)
//    }
//
//
//    fun dayPast(): Int {
//        return DaysDiffCalculate(startDate!!, now!!)
//    }
//
//    fun dayRemind(): Int {
//        return dayCount() - dayPast()
//    }
//
//
//    // --Commented out by Inspection START (15/02/02 22:42):
//    //    public int DayPercentPast(){
//    //        if (dayCount()>0) {
//    //            return (dayPast() * 100) / dayCount();
//    //        }else {
//    //            return 0;
//    //        }
//    //    }
//    // --Commented out by Inspection STOP (15/02/02 22:42)
//
//
//    private fun DaysDiffCalculate(StartDate: String, EndDate: String): Int {
//
//        val Start = JDF()
//        val End = JDF()
//
//        Start.iranianDate = StartDate
//        End.iranianDate = EndDate
//
//        return End.dayCount - Start.dayCount + 1
//    }
//
//
//    private fun CheckDate(start: String, end: String): Boolean {
//        val s = JDF()
//        val e = JDF()
//        s.iranianDate = start
//        e.iranianDate = end
//        return e.dayCount - s.dayCount <= 0
//
//    }
//
//    @Throws(MyException::class)
//    fun setStartAndEndDate(startDate: String, endDate: String) {
//        if (startDate != "" && endDate != "") {
//            if (CheckDate(startDate, endDate)) {
//                throw MyException("تاریخ شروع بعد از پایان است ! لطفا اصلاح کنید")
//            } else {
//                this.startDate = startDate
//                this.endDate = endDate
//            }
//        } else {
//            throw MyException("لطفا تاریخ را وارد کنید")
//        }
//    }
//}
