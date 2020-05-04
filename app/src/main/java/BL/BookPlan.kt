package BL

class BookPlan (private val bookReads:BookReads):Book(bookReads.getDto(),bookReads.readSum){
    fun MaxPageReaded(): Int {
        return bookReads.read_dbs.maxBy { it.pageRead }!!.pageRead
    }
}