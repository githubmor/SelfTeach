package BL

import DAL.ReadBookdb


class Read(val dbDto: ReadBookdb) {

    val pageReadCount = dbDto.read.pageRead

    val readDate = dbDto.read.readDate

    val book = dbDto.bookName
}