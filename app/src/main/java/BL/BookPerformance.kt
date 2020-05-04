package BL

class BookPerformance (val term:Term, val book : Book): Performance(term.dayCount,term.dayPast,book.pageCount,book.readSum)