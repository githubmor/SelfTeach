package BL

import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table

/**
 * Created by M on 14/11/23.
 */

@Table(name = "Reads")
class Read : Model {


    @Column(name = "pageReadCount")
    var pageReadCount: Int = 0
        internal set

    @Column(name = "readDate")
    var readDate: String? = null
        internal set

    @Column(name = "Book_Old")
    var bookOld: Book_Old? = null

    @Throws(MyException::class)
    constructor(pageReadCount: Int, readDate: String) : super() {// must check date with term date and before today
        if (pageReadCount > 0) {
            this.pageReadCount = pageReadCount
        } else {
            throw MyException("لطفا تعداد صفحه خوانده شده را وارد کنید")
        }
        if ("" != readDate) {
            this.readDate = readDate
        } else {
            throw MyException("لطفا تاریخ خواندن را وارد کنید")
        }
    }

    constructor() : super()
}


