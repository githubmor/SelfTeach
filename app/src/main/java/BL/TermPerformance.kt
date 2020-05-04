package BL

class TermPerformance (val term : Term,books : List<Book>) :
        Performance(term.dayCount,term.dayPast,books.sumBy { it.pageCount },books.sumBy { it.readSum } )