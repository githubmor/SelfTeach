package BL

import DAL.*
import android.content.Context
import androidx.annotation.WorkerThread

class ReadService(context: Context) {
    var dao  = ReadRepository(AppDatabase.getInstance(context).readDao())
    @WorkerThread
    suspend fun insert(read: Read)  {
//        withContext(Dispatchers.IO) {
           dao.insert(read.getDto())
//        }
    }
    @WorkerThread
    suspend fun delete(read: Read) {
//        withContext(Dispatchers.IO) {
            dao.delete(read.getDto())
//        }
    }
    //    @WorkerThread
//    suspend fun update(term_old: Read_db) {
//        withContext(Dispatchers.IO) {
//            dao.update(term_old)
//        }
//    }
//    @WorkerThread
//    suspend fun isReadExist(): Int {
//        return withContext(Dispatchers.IO) {
//            val y = async {
//                dao.ex()
//            }
//            withContext(Dispatchers.Main){
//                y.await()
//            }
//        }
//    }
//    @WorkerThread
//    suspend fun getAllReads(): List<Read>? {
////        return withContext(Dispatchers.IO) {
////            val y = async {
//              return  dao.getAllReads()
////            }
////            withContext(Dispatchers.Main){
////                y.await()
////            }
////        }
//    }

    @WorkerThread
    suspend fun getAllReadsWithBookName(): List<ReadBook>? {
//        return withContext(Dispatchers.IO) {
//            val y = async {
              return   dao.getAllReadsWithBookName()!!.map { ReadBook(it.read,it.bookName) }
//            }
//            withContext(Dispatchers.Main){
//                y.await()
//            }
        }

}