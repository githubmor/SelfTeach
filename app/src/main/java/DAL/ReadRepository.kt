package DAL

import androidx.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class ReadRepository(private val dao: ReadDAO) {

    @WorkerThread
    suspend fun insert(read: Readdb) {
        withContext(Dispatchers.IO) {
            dao.insert(read)
        }
    }
    @WorkerThread
    suspend fun delete(read: Readdb) {
        withContext(Dispatchers.IO) {
            dao.delete(read)
        }
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
//    suspend fun getAllReads(): List<Readdb>? {
//        return withContext(Dispatchers.IO) {
//            val y = async {
//                dao.getAllReads()
//            }
//            withContext(Dispatchers.Main){
//                y.await()
//            }
//        }
//    }

    @WorkerThread
    suspend fun getAllReadsWithBookName(): List<ReadBookNamedb>? {
        return withContext(Dispatchers.IO) {
            val y = async {
                dao.getAllReadsWithBookName()
            }
            withContext(Dispatchers.Main){
                y.await()
            }
        }
    }
}