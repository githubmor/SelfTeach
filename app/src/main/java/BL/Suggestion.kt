package BL

class Suggestion(val term: Term, val books: List<BookReads>) {

    init {
        books.sortedBy { it.priority }
    }

    fun getBookSuggestList(pageShouldBeRead: TermPerformance): List<ReadSuggestion> {
        var suggest = listOf<ReadSuggestion>()
        var remindPage = pageShouldBeRead.pageReadTo100Percent
        books.forEach { book ->
            val suggestion = BookSuggestion(BookPlan(book), BookPerformance(term, book).pageReadTo100Percent)
            if (suggestion.hasSuggest(remindPage)) {
                suggest = suggest + ReadSuggestion(book, suggestion.readSuggest(), suggestion.needFoghPlan())
                remindPage -= suggestion.readSuggest()
            }
        }
        if (remindPage > 0) {
            val o = suggest.minBy { it.readSuggest }
            if (o != null)
                o.readSuggest = remindPage + o.readSuggest
            else
                suggest = suggest + ReadSuggestion(books[0], remindPage, false)
        }

        return suggest
    }
}

class ReadSuggestion(book: IBook, var readSuggest: Int, val isFoghBarname: Boolean = false) : IBook by book