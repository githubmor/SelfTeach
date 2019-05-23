//package BL
//
//import com.activeandroid.Model
//import com.activeandroid.annotation.Column
//import com.activeandroid.annotation.Table
//import com.activeandroid.query.Select
//import java.util.*
//
///**
// * Created by M on 14/11/23.
// */
//
//@Table(type = "Books")
//class Book_Old : Model {
//
//    @Column(type = "type")
//    var type: String? = null
//        internal set
//
//    @Column(type = "pageCount")
//    var pageCount: Int = 0
//        internal set
//
//    @Column(type = "free")
//    private var free: Boolean = false
//
//    var reads: List<Read_Old> = ArrayList()
//        private set
//
//
//    constructor(type: String, pageCount: Int, free: Boolean) : super() {
//        if ("" != type && pageCount > 0) {
//            this.type = type
//            this.pageCount = pageCount
//            this.setFree(free)
//        } else {
//            throw MyException("لطفا اطلاعات را درست وارد کنید")
//        }
//
//    }
//
//    fun LoadReads() {
//        if (reads.isEmpty()) {
//            reads = Select()
//                    .from(Read_Old::class.java)
//                    .where("Book_Old = ?", id!!)
//                    .execute()
//        }
//        for (r in reads) {
//            r.bookOld = this
//        }
//    }
//
//    constructor() : super()
//
//    fun DeleteReads() {
//        for (r in reads) {
//            r.delete()
//        }
//    }
//
//
//    fun pageWasReaded(): Int {
//        if (reads.size > 0) {
//            var sum = 0
//            for (r in reads) {
//                sum += r.pageReadCount
//            }
//            return sum
//        } else {
//            return 0
//        }
//    }
//
//    fun PageRemind(): Int {
//        return pageCount - pageWasReaded()
//    }
//
//
//    fun pageReadPercent(): Int {
//        return if (pageCount > 0) {
//            pageWasReaded() * 100 / pageCount
//        } else {
//            0
//        }
//    }
//
//    override fun toString(): String {
//        return type + " - " + PageRemind() + " صفحه"
//    }
//
//    fun NotFree(): Boolean {
//        return !free
//    }
//
//    fun setFree(free: Boolean) {
//        this.free = free
//    }
//}
