package BL

import DAL.Read_db


 open class Read(bookId:Int) {

    private var dbDto: Read_db = Read_db(0,bookId,0,"")

    constructor(dbDto : Read_db) :this(dbDto.bookId){
        this.dbDto=dbDto
    }
    var pageReadCount
        get() = dbDto.pageRead
        set(value) {
            dbDto.pageRead = value

        }
    var readDate
        get() = dbDto.readDate
        set(value) {
            dbDto.readDate = value

        }

    fun getDto(): Read_db {
        return dbDto
    }
}