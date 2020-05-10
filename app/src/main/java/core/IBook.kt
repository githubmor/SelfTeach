package core

import data.BookDataTable

interface IBook {
    var name: String
    var pageCount: Int
    var priority: Int
    var readSum: Int
    fun getBookDataTable(): BookDataTable
}