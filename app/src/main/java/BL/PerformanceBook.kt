package BL

class PerformanceBook (val term:Term, val book : Book):
        PerformanceCalculator(term.dayCount,term.dayPast,book.pageCount,book.pageWasReaded)