package BL

import DAL.Book_db

open class Book (){
    private  var dbDto = Book_db(0,"",0,0)

    private var readSum  = 0

    constructor(dbDto : Book_db, readSum:Int):this(){
        this.dbDto = dbDto
        this.readSum = readSum
    }

    var name
        get() = dbDto.name
        set(value) {
            dbDto.name = value

        }
    var pageCount
        get() = dbDto.pageCount
        set(value) {
            dbDto.pageCount = value

        }
    var priority
        get() = dbDto.priority
        set(value) {
            dbDto.priority = value

        }
    val pageWasReaded: Int
        get() {
            return readSum
        }

    fun getDto(): Book_db {
        return dbDto
    }
}