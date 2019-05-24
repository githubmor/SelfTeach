package BL

import kotlin.math.round

class PerformanceBook(val term:Term,val book : Book) : Book(book.dbDto) {


    val performance: Float
    get(){
        val pageShouldRead = pageShouldReadTillToday
        return if (pageShouldRead > 0) book.pageWasReaded * 100 / pageShouldRead.toFloat() else 0F
    }

    val pageRemindToGet100Percent get()= pageShouldReadTillToday - book.pageWasReaded



    val avgPagePerEveryRead: Int
            get()= when {
        book.dbDto.reads.isNotEmpty() -> book.pageWasReaded / book.dbDto.reads.size
        else -> 0
    }

    private val pageShouldReadTillToday = (term.dayPast * avgPagePerDay).toInt()

    private val avgPagePerDay: Float
           get() = when {
        term.dayCount > 0 -> book.pageCount / term.dayCount.toFloat()
        else -> 0F
    }

    val biggestPageCanRead: Int get(){
        return when {
            avgPagePerEveryRead>pageShouldReadTillToday -> avgPagePerEveryRead
            avgPagePerEveryRead==0 -> return pageShouldReadTillToday
            else -> (avgPagePerEveryRead + round(avgPagePerEveryRead*0.5F).toInt())
        }
    }


}