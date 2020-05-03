package BL

import DAL.ReadBookNamedb
import DAL.Readdb


class Read(val dbDto : ReadBookNamedb) {

    constructor(bookId:Int,bookName:String, pageReadCount:Int, readDate:String ) :
            this(ReadBookNamedb(Readdb(0,bookId,pageReadCount,readDate),bookName))

    var name
        get() = dbDto.bookName
        set(value) {
            dbDto.bookName = value

        }
    var pageReadCount
        get() = dbDto.read.pageRead
        set(value) {
            dbDto.read.pageRead= value

        }
    var readDate
        get() = dbDto.read.readDate
        set(value) {
            dbDto.read.readDate = value

        }
}