package core

class BookSuggestion(private val paln: BookPlan, private val to100PercentBook: Int) : IBook by paln {

    fun readSuggest(): Int {
        var result = 0
        if (to100PercentBook > 0) {
            result = Math.max(to100PercentBook, paln.avregPageReaded())
        }
        return result
    }

    fun needFoghPlan(): Boolean {
        return to100PercentBook > paln.avregPageReaded()
    }

    fun hasSuggest(remindPage: Int): Boolean {

        return to100PercentBook > 0 && to100PercentBook <= remindPage * 2
    }
}