package BL

class Suggestion(val booksPaln:BookPlan, val pageReadTo100Percent:Int) {
    val bookName
            get()=booksPaln.name

    fun suggestRead(): Int {

        var re = 0
        if(getBookHasPageToReadToday()) {

            re = if (isOverPlan()) {
                pageReadTo100Percent
            } else {
                booksPaln.MaxPageReaded()
            }
        }

        return re

    }

    fun isOverPlan(): Boolean {
        return pageReadTo100Percent >= booksPaln.MaxPageReaded()
    }

    private fun getBookHasPageToReadToday(): Boolean {
        return pageReadTo100Percent>0
    }

    fun HasSuggest(remindPage:Int): Boolean {
        return pageReadTo100Percent<=remindPage
    }

}