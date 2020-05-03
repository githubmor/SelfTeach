package BL

import DAL.*
import android.content.Context
import androidx.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class ReadService(context: Context) {
    var dao  = ReadRepository(AppDatabase.getInstance(context).readDao())
    @WorkerThread
    suspend fun insert(read: Read) {
//        withContext(Dispatchers.IO) {
            dao.insert(read.dbDto.read)
//        }
    }
    @WorkerThread
    suspend fun delete(read: Read) {
//        withContext(Dispatchers.IO) {
            dao.delete(read.dbDto.read)
//        }
    }
    //    @WorkerThread
//    suspend fun update(term_old: Readdb) {
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
    suspend fun getAllReadsWithBookName(): MutableList<Read>? {
//        return withContext(Dispatchers.IO) {
//            val y = async {
              return   dao.getAllReadsWithBookName()!!.map { Read(it) }.toMutableList()
//            }
//            withContext(Dispatchers.Main){
//                y.await()
//            }
        }

}