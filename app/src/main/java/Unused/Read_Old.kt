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
//@Table(type = "Reads")
//class Read_Old : Model {
//
//
//    @Column(type = "pageReadCount")
//    var pageReadCount: Int = 0
//        internal set
//
//    @Column(type = "readDate")
//    var readDate: String? = null
//        internal set
//
//    @Column(type = "Book_Old")
//    var bookOld: Book_Old? = null
//
//    @Throws(MyException::class)
//    constructor(pageReadCount: Int, readDate: String) : super() {// must check date with term_old date and before today
//        if (pageReadCount > 0) {
//            this.pageReadCount = pageReadCount
//        } else {
//            throw MyException("لطفا تعداد صفحه خوانده شده را وارد کنید")
//        }
//        if ("" != readDate) {
//            this.readDate = readDate
//        } else {
//            throw MyException("لطفا تاریخ خواندن را وارد کنید")
//        }
//    }
//
//    constructor() : super()
//}
//
//
