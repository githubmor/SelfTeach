package core

class BookPerformance(val term: Term, val book: IBook) : IBook by book,
        Performance(term.dayCount, term.dayPast, book.pageCount, book.readSum)

