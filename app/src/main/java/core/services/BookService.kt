package core.services

import data.AppDatabase
import data.BookRepository
import android.content.Context
import core.Book
import core.BookReads
import core.Read

class BookService(context: Context) {
    private var repository: BookRepository = BookRepository(AppDatabase.getInstance(context).bookDatabase())
    suspend fun insert(book: Book): Boolean {
        return repository.insert(book.getBookDataTable())
    }

    suspend fun delete(book: Book): Boolean {
        return repository.delete(book.getBookDataTable())
    }

    suspend fun deleteAll(): Boolean {
        return repository.deleteAll()
    }

    suspend fun anyBooksExist(): Boolean {
        return repository.isBooksExist()
    }

    suspend fun getAllBookWithSumRead(): List<Book> {
        val o = repository.getAllBookWithSumRead()
        return if (o != null) repository.getAllBookWithSumRead()!!.map { Book(it.bookDataTable, it.readSum) } else listOf()
    }

    suspend fun getAllBookWithListRead(): List<BookReads> {
        return repository.getAllBookWithListReads()?.map { bookReadsDataTable ->
            BookReads(Book(bookReadsDataTable.bookDataTable), bookReadsDataTable.readDataTableLists.map { Read(it) })
        }?: listOf()

    }
}