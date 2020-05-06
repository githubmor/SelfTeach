package DAL

import androidx.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class BookRepository(private val database: BookDatabase) {

    @WorkerThread
    suspend fun insert(bookDataTable: BookDataTable) {
        withContext(Dispatchers.IO) {
            database.insertBook(bookDataTable)
        }
    }
    @WorkerThread
    suspend fun delete(book: BookDataTable) {
        withContext(Dispatchers.IO) {
            database.deleteBook(book)
        }
    }
    @WorkerThread
    suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            database.deleteAllBook()
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
        return withContext(Dispatchers.IO) {
            val y = async {
                database.getAllBookWithSumRead()
            }
            withContext(Dispatchers.Main){
                y.await()
            }
        }
    }
}