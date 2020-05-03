package BL
import DAL.Bookdb
import DAL.BookSumReaddb

open class Book (val dbDto : BookSumReaddb){
    constructor(name: String, pageCount: Int, priprity: Int) :
            this(BookSumReaddb(Bookdb(0,name,pageCount,priprity),0))

    var name
        get() = dbDto.book.name
        set(value) {
            dbDto.book.name = value

        }
    var pageCount
        get() = dbDto.book.pageCount
        set(value) {
            dbDto.book.pageCount = value

        }
    var priority
        get() = dbDto.book.priority
        set(value) {
            dbDto.book.priority = value

        }
    val pageWasReaded: Int
        get() {
            return dbDto.readSum
        }


    val pageReadPercent: Float
        get() {
            return (pageWasReaded * 100) / pageCount.toFloat()
        }

}