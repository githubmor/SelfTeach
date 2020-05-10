package data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class BookRepository(private val database: BookDatabase) {

    @WorkerThread
    suspend fun insert(bookDataTable: BookDataTable): Boolean {
        return withContext(Dispatchers.IO) {
            val y = async {
                database.insertBook(bookDataTable)
            }
            withContext(Dispatchers.Main) {
                bookDataTable.id = y.await().toInt()
                bookDataTable.id>0
            }

        }
    }
    @WorkerThread
    suspend fun delete(book: BookDataTable): Boolean {
        return withContext(Dispatchers.IO) {
            val y = async {
                database.deleteBook(book)
            }
            withContext(Dispatchers.Main) {
                y.await() == 1
            }

        }
    }
    @WorkerThread
    suspend fun deleteAll(): Boolean {
        return withContext(Dispatchers.IO) {
            val y = async {
                database.deleteAllBook()
            }
            withContext(Dispatchers.Main) {
                y.await()> 0
            }

        }
    }
    @WorkerThread
    suspend fun isBooksExist(): Boolean {
        return withContext(Dispatchers.IO) {
            val y = async {
                database.existBook()
            }
            withContext(Dispatchers.Main){
                y.await()>0
            }
        }
    }
    @WorkerThread
    suspend fun getAllBookWithListReads(): List<BookReadsDataTable>? {
        return withContext(Dispatchers.IO) {
            val y = async {
                database.getAllBookWithListReads()
            }
            withContext(Dispatchers.Main){
                y.await()
            }
        }
    }

    @WorkerThread
    suspend fun getAllBookWithSumRead(): List<BookSumReadDataTable>? {
        return getAllBookWithListReads()?.map {
            BookSumReadDataTable(it.bookDataTable, it.readDataTableLists.sumBy { it.pageReadCount }) }?: listOf()
    }
}