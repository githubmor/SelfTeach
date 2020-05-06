package BL

class Suggestion(val term: Term, val books: List<BookReads>) {

    init {
        books.sortedBy { it.priority }
    }

    fun getBookSuggestList(pageShouldBeRead: Int): List<BookSuggestion> {
        var suggest = listOf<BookSuggestion>()
        var remindPage = pageShouldBeRead
        books.forEach { book ->
            val suggestion = BookSuggestion(BookPlan(book), BookPerformance(term, book).pageReadTo100Percent)
            if (suggestion.hasSuggest(remindPage)) {
                suggest = suggest + suggestion
                remindPage -= suggestion.readSuggest()
            }
        }

        return suggest
    }
}