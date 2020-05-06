package BL

import DAL.ReadDataTable


open class Read(bookId: Int) {

    private var readDataTable: ReadDataTable = ReadDataTable(0, bookId, 0, "")

    constructor(readDataTable: ReadDataTable) : this(readDataTable.bookId) {
        this.readDataTable = readDataTable
    }

    var pageReadCount
        get() = readDataTable.pageReadCount
        set(value) {
            readDataTable.pageReadCount = value

        }
    var readDate
        get() = readDataTable.readDate
        set(value) {
            readDataTable.readDate = value

        }

    fun getReadDataTable() = readDataTable

}