package BL

import DAL.AppDatabase
import DAL.BookRepository
import DAL.Bookdb
import android.content.Context
import androidx.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class BookService(context:Context) {
    var dao : BookRepository = BookRepository(AppDatabase.getInstance(context).bookDao())
//    @WorkerThread
    suspend fun insert(book: Book) {
//        withContext(Dispatchers.IO) {
            dao.insert(book.dbDto.book)
//        }
    }
//    @WorkerThread
    suspend fun delete(book: Book) {
//        withContext(Dispatchers.IO) {
            dao.delete(book.dbDto.book)
//        }
    }
//    @WorkerThread
//    suspend fun update(book: Book) {
////        withContext(Dispatchers.IO) {
//            dao.update(book.dbDto.book)
////        }
//    }
//    @WorkerThread
    suspend fun deleteAll() {
//        withContext(Dispatchers.IO) {
            dao.deleteAll()
//        }
    }
//    @WorkerThread
    suspend fun isBooksExist(): Boolean {
//        return withContext(Dispatchers.IO) {
//            val y = async {
             return dao.isBooksExist()
//            }
//            withContext(Dispatchers.Main){
//                y.await()>0
//            }
//        }
    }
//    @WorkerThread
    suspend fun getAllBook(): List<Book>? {
//        return withContext(Dispatchers.IO) {
//            val y = async {
             return dao.getAllNewBook()!!.map { Book(it) }
//            }
//            withContext(Dispatchers.Main){
//                y.await()
//            }
//        }
    }

//    @WorkerThread
//    suspend fun getAllBookWithRead(): List<Book>? {
////        return withContext(Dispatchers.IO) {
////            val y = async {
//                dao.getAllBookWithReads()
////            }
////            withContext(Dispatchers.Main){
////                y.await()
////            }
////        }
//    }
}