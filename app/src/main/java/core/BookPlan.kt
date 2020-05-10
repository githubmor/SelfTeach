package core

class BookPlan(private val bookReads: BookReads) : IBook by bookReads {
    fun avregPageReaded(): Int {
        return if (bookReads.reads.count() > 0)
            bookReads.reads.sumBy { it.pageReadCount } / bookReads.reads.count()
        else
            0
    }
}