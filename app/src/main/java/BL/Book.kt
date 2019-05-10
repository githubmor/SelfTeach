package BL

import DAL.BookReads

class Book(val dbDto: BookReads) {
    fun PageReadPercent(): Int {
        val readSumm = dbDto.reads.sumBy { it.pageRead }
        return ((readSumm *100)/ pageCount)
    }

    val pageCount = dbDto.book.pageCount
    val name = dbDto.book.name
}