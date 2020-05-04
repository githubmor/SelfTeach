package BL

class BookPlan (private val bookReads:BookReads){
    fun MaxPageReaded(): Int {
        return bookReads.read_dbs.maxBy { it.pageRead }!!.pageRead
    }
}