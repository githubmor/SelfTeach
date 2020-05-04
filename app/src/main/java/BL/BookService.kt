package BL

import DAL.AppDatabase
import DAL.BookRepository
import android.content.Context
import androidx.annotation.WorkerThread

class BookService(context:Context) {
    var dao : BookRepository = BookRepository(AppDatabase.getInstance(context).bookDao())
//    @WorkerThread
    suspend fun insert(book: Book) {
//        withContext(Dispatchers.IO) {
            dao.insert(book.getDto())
//        }
    }
//    @WorkerThread
    suspend fun delete(book: Book) {
//        withContext(Dispatchers.IO) {
            dao.delete(book.getDto())
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
    suspend fun getAllBookWithSumRead(): List<Book>? {
//        return withContext(Dispatchers.IO) {
//            val y = async {
             return dao.getAllNewBook()!!.map { Book(it.book,it.readSum) }
//            }
//            withContext(Dispatchers.Main){
//                y.await()
//            }
//        }
    }

    @WorkerThread
    suspend fun getAllBookWithListRead(): List<BookReads>? {
//        return withContext(Dispatchers.IO) {
//            val y = async {
              return  dao.getAllBookWithRead()!!.map { BookReads(it.book,it.reads) }
//            }
//            withContext(Dispatchers.Main){
//                y.await()
//            }
//        }
    }
}