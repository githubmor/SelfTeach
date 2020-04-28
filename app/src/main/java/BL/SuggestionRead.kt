package BL

class SuggestionRead(val books:List<PerformanceBook>) {
    fun readList(): List<String> {


//        val needToRead = getBookHasPageToReadToday()
//
//        val books = sortBookByPriorityAndPageNeedToRead(needToRead)
//
//        val avgBookRead = avgBookReadPerDay
//        val pageTo100 = pageTo100Percent
//
        val re : MutableList<String> = mutableListOf()
//        var sum = 0
//        for (b in books) {
//            if (sum <= pageTo100 || re.size <= avgBookRead) {
//                sum += b.biggestPageCanRead
//                re.add(b.name + " - " + b.biggestPageCanRead)
//            }else
//                return re
//        }
//
        return re

    }

    private fun sortBookByPriorityAndPageNeedToRead(needToRead: List<PerformanceBook>): List<PerformanceBook> {
        val bookWithHighPriorityAndHightPageToRead = needToRead
                .sortedWith(
                        compareBy(
                                { it.book.priority },
                                { it.pageReadTo100Percent }
                        ))
        return bookWithHighPriorityAndHightPageToRead
    }

    private fun getBookHasPageToReadToday(): List<PerformanceBook> {
        val needToRead = books.filter { it.pageReadTo100Percent > 0 }
        return needToRead
    }

}