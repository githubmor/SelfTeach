package BL

import DAL.BookReadsdb

class BookRead(val bookRead: BookReadsdb):Book(bookRead.book.name,bookRead.book.pageCount,bookRead.book.priority) {
    var reads = bookRead.reads.map { Read(it) }

}