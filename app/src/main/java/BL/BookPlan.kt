package BL

class BookPlan (val book:Book,val reads:List<Read>){
    fun MaxPageReaded(): Int {
        return reads.maxBy { it.pageReadCount }!!.pageReadCount
    }
}