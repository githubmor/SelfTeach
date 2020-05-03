package BL

import java.util.*

class Suggestion(val booksPaln:BookPlan, val booksPerformance:PerformanceBook) {
    val bookName
            get()=booksPaln.book.name

    fun suggestBookList(): Int {

        var re = 0
        if(getBookHasPageToReadToday()) {

//        val sortedNeekReadbooks = sortBookByPriorityAndPageNeedToRead(needToRead)


            //اينجا بايد شرط هاي ديگر مثل روزهايي كه اينو ميخواند و غيره را در نظر بگيرم
//        sortedNeekReadbooks.forEach { sbook ->
//            val plan = booksPaln.find { it.book.name==sbook.book.name }!!
            if (isOverPlan()) {
                re = booksPerformance.pageReadTo100Percent
            } else {
                re = booksPaln.MaxPageReaded()
            }
        }
//        }

        return re

    }

    fun isOverPlan(): Boolean {
        return booksPerformance.pageReadTo100Percent >= booksPaln.MaxPageReaded()
    }

//    private fun sortBookByPriorityAndPageNeedToRead(needToRead: List<PerformanceBook>): List<PerformanceBook> {
//        val bookWithHighPriorityAndHightPageToRead = needToRead
//                .sortedWith(
//                        compareBy(
//                                { it.book.priority },
//                                { it.pageReadTo100Percent }
//                        ))
//        return bookWithHighPriorityAndHightPageToRead
//    }

    private fun getBookHasPageToReadToday(): Boolean {
        return booksPerformance.pageReadTo100Percent>0
    }

    fun HasSuggest(remindPage:Int): Boolean {
        return booksPerformance.pageReadTo100Percent<=remindPage
    }

}