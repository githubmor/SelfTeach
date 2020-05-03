package DAL

import BL.BookRead
import androidx.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class BookRepository(private val dao: BookDAO) {

    @WorkerThread
    suspend fun insert(book: Bookdb) {
        withContext(Dispatchers.IO) {
            dao.insert(book)
        }
    }
    @WorkerThread
    suspend fun delete(book: Bookdb) {
        withContext(Dispatchers.IO) {
            dao.delete(book)
        }
    }
//    @WorkerThread
//    suspend fun update(book: Bookdb) {
//        withContext(Dispatchers.IO) {
//            dao.update(book)
//        }
//    }
    @WorkerThread
    suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            dao.deleteAll()
        }
    }
    @WorkerThread
    suspend fun isBooksExist(): Boolean {
        return withContext(Dispatchers.IO) {
            val y = async {
                dao.existBook()
            }
            withContext(Dispatchers.Main){
                y.await()>0
            }
        }
    }
//    @WorkerThread
//    suspend fun getAllBook(): List<Bookdb>? {
//        return withContext(Dispatchers.IO) {
//            val y = async {
//                dao.getAllBook()
//            }
//            withContext(Dispatchers.Main){
//                y.await()
//            }
//        }
//    }

    @WorkerThread
    suspend fun getAllBookWithRead(): List<BookReadsdb>? {
        return withContext(Dispatchers.IO) {
            val y = async {
                dao.getAllBookWithReads()
            }
            withContext(Dispatchers.Main){
                y.await()
            }
        }
    }

    @WorkerThread
    suspend fun getAllNewBook(): List<BookSumReaddb>? {
        return withContext(Dispatchers.IO) {
            val y = async {
                dao.getAllNewBook()
            }
            withContext(Dispatchers.Main){
                y.await()
            }
        }
    }
}