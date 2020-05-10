package core

class BookReads(book: IBook, val reads: List<Read>) : IBook by book {
    override var readSum = reads.sumBy { it.pageReadCount }
}