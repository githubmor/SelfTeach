package data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class ReadRepository(private val database: ReadDatabase) {

    @WorkerThread
    suspend fun insert(read: ReadDataTable):Boolean {
        return withContext(Dispatchers.IO) {
            val y = async {
                database.insertRead(read)
            }
            withContext(Dispatchers.Main){
                read.id = y.await().toInt()
                read.id>0
            }
        }
    }
    @WorkerThread
    suspend fun delete(read: ReadDataTable):Boolean {
        return withContext(Dispatchers.IO) {
            val y = async {
                database.deleteRead(read)
            }
            withContext(Dispatchers.Main) {
                y.await() == 1
            }

        }
    }

    @WorkerThread
    suspend fun getAllReadsWithBookName(): List<ReadBookNameDataTable>? {
        return withContext(Dispatchers.IO) {
            val y = async {
                database.getAllReadsWithBookName()
            }
            withContext(Dispatchers.Main){
                y.await()
            }
        }
    }
}