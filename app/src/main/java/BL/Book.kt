package BL

import DAL.BookDataTable

open class Book(override var readSum: Int = 0) : IBook {

    private var bookDataTable = BookDataTable(0, "", 0, 0)

    constructor(bookDataTable: BookDataTable, readSum: Int = 0) : this(readSum) {
        this.bookDataTable = bookDataTable
    }

    override var name
        get() = bookDataTable.name
        set(value) {
            bookDataTable.name = value

        }
    override var pageCount
        get() = bookDataTable.pageCount
        set(value) {
            bookDataTable.pageCount = value

        }
    override var priority
        get() = bookDataTable.priority
        set(value) {
            bookDataTable.priority = value

        }

    override fun getBookDataTable(): BookDataTable = bookDataTable
}