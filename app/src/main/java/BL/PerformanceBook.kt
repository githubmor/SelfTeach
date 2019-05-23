package BL

class PerformanceBook(val term:Term,val book : Book) : Book(book.dbDto) {



    fun performance(): Int {
        val pageShouldRead = pageShouldReadTillToday()
        return if (pageShouldRead > 0) book.pageWasReaded() * 100 / pageShouldRead else 0
    }

    fun pageRemindToGet100Percent()=
            pageShouldReadTillToday() - book.pageWasReaded()


    fun avgPagePerEveryRead(): Int
            = when {
        book.dbDto.reads.isNotEmpty() -> book.pageWasReaded() / book.dbDto.reads.size
        else -> 0
    }

    private fun pageShouldReadTillToday() =
            term.dayPast() * avgPagePerDay()

    private fun avgPagePerDay(): Int
            = when {
        term.dayCount() > 0 -> book.pageCount / term.dayCount()
        else -> 0
    }

    fun biggestPageCanRead(): Int {
       return if (avgPagePerEveryRead()>pageShouldReadTillToday())
            avgPagePerEveryRead()
        else
            avgPagePerEveryRead() + (avgPagePerEveryRead()*0.1).toInt()
    }


}