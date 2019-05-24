package BL

import DAL.BookReadsdb

open class Book(val dbDto: BookReadsdb) {

    //يك اصل رو رعايت كن . اينجا فقط محاسبات عددي تصميمي نداريم

    var pageCount
        get() = dbDto.book.pageCount
        set(value) {
            dbDto.book.pageCount = value
        }

    var name
        get() = dbDto.book.name
        set(value) {
            dbDto.book.name = value
        }

    var priority
        get() = dbDto.book.priority
        set(value) {
            dbDto.book.priority = value
        }

    val pageWasReaded: Int
        get() {
            return dbDto.reads.sumBy { it.pageRead }
        }

    val pageReadState: String
        get() {
            return "( $pageWasReaded/$pageCount ) صفحه"
        }

    val pageReadPercent: Float
        get() {
            return (pageWasReaded * 100) / pageCount.toFloat()
        }
}