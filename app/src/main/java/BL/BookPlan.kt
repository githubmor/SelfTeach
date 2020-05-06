package BL

class BookPlan(private val bookReads: BookReads) : IBook by bookReads {
    fun avregPageReaded(): Int {
        return bookReads.reads.sumBy { it.pageReadCount } / bookReads.reads.count()
    }
}