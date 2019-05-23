package BL

class Performance(private val term: Term,private val books : List<PerformanceBook>){

    private fun PageWasRead() = books.sumBy { it.pageWasReaded() }

    private fun PageCount()= books.sumBy { it.pageCount }

    private fun pageShouldReadTillToday() = term.dayPast() * avgPagePerDay()

    fun pagePerDayRemind()= term.dayRemind() * avgPagePerDay()

    fun avgBookReadPerDay(): Int{
        return  0
    }

    fun pageTo100Percent(): Int {
        return pageShouldReadTillToday() - PageWasRead()
    }

    private fun avgPagePerEveryRead(): Int {
        val c =books.sumBy { it.dbDto.reads.count() }
        return if (c>0) {
            PageWasRead() / c
        } else 0
    }

    private fun avgPagePerDay(): Int {
        return if (term.dayCount() > 0) {
            PageCount() / term.dayCount()
        } else {
            0
        }
    }

    fun performance(): Int {
        return if (term.dayPast() > 0 && avgPagePerDay() > 0) {
            PageWasRead() * 100 / pageShouldReadTillToday()
        } else {
            0
        }
    }

    fun readList(): List<String> {


        val needToRead = getBookHasPageToReadToday()

        val books = sortBookByPriorityAndPageNeedToRead(needToRead)

        val avgBookRead = avgBookReadPerDay()
        val pageTo100 = pageTo100Percent()
        val re : MutableList<String> = mutableListOf()
        var sum = 0
        var i = 0
            while (sum <= pageTo100 || re.size <= avgBookRead) {
                val b = books[i]
                sum += b.biggestPageCanRead()
                re.add(b.name + " - " + b.biggestPageCanRead())
                i++
            }

        return re

    }

    private fun sortBookByPriorityAndPageNeedToRead(needToRead: List<PerformanceBook>): List<PerformanceBook> {
        val bookWithHighPriorityAndHightPageToRead = needToRead
                .sortedWith(
                        compareBy(
                                { it.priority },
                                { it.pageRemindToGet100Percent() }
                        ))
        return bookWithHighPriorityAndHightPageToRead
    }

    private fun getBookHasPageToReadToday(): List<PerformanceBook> {
        val needToRead = books.filter { it.pageRemindToGet100Percent() > 0 }
        return needToRead
    }


}
