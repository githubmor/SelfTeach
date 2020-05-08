package BL

import DAL.AppDatabase
import DAL.BookRepository
import android.content.Context

class BookService(context: Context) {
    private var repository: BookRepository = BookRepository(AppDatabase.getInstance(context).bookDatabase())
    suspend fun insert(book: Book) {
        repository.insert(book.getBookDataTable())
    }

    suspend fun delete(book: Book) {
        repository.delete(book.getBookDataTable())
    }

    suspend fun deleteAll() {
        repository.deleteAll()
    }

    suspend fun anyBooksExist(): Boolean {
        return repository.isBooksExist()
    }

    suspend fun getAllBookWithSumRead(): List<Book> {
        val o = repository.getAllBookWithSumRead()
        return if (o != null) repository.getAllBookWithSumRead()!!.map { Book(it.bookDataTable, it.readSum) } else listOf()
    }

    suspend fun getAllBookWithListRead(): List<BookReads>? {
        return repository.getAllBookWithListReads()!!.map { bookReadsDataTable ->
            BookReads(Book(bookReadsDataTable.bookDataTable), bookReadsDataTable.readDataTableLists.map { Read(it) })
        }

    }
}