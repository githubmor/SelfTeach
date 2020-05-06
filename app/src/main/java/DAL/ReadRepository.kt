package DAL

import androidx.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class ReadRepository(private val database: ReadDatabase) {

    @WorkerThread
    suspend fun insert(read: ReadDataTable) {
        withContext(Dispatchers.IO) {
            database.insertRead(read)
        }
    }
    @WorkerThread
    suspend fun delete(read: ReadDataTable) {
        withContext(Dispatchers.IO) {
            database.deleteRead(read)
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