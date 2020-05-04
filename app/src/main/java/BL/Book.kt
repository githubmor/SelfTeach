package BL

import DAL.Book_db

open class Book (){

    private var bookdb = Book_db(0,"",0,0)
    var readSum  = 0

    constructor(dto : Book_db, readSum:Int):this(){
        this.bookdb = dto
        this.readSum = readSum
    }

    var name
        get() = bookdb.name
        set(value) {
            bookdb.name = value

        }
    var pageCount
        get() = bookdb.pageCount
        set(value) {
            bookdb.pageCount = value

        }
    var priority
        get() = bookdb.priority
        set(value) {
            bookdb.priority = value

        }

    fun getDto(): Book_db = bookdb
}