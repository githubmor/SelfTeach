package BL

class TermPerformance (val term : Term,books : List<Book>) :
        PerformanceCalculator(term.dayCount,term.dayPast,books.sumBy { it.pageCount },books.sumBy { it.pageCount } )