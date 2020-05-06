package DAL

import androidx.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class TermRepository(private val database: TermDatabase) {

    @WorkerThread
    suspend fun insert(term: TermDataTable) {
        withContext(Dispatchers.IO) {
            database.insert(term)
        }
    }
    @WorkerThread
    suspend fun delete(term: TermDataTable) {
        withContext(Dispatchers.IO) {
            database.delete(term)
        }
    }
    @WorkerThread
    suspend fun update(term: TermDataTable) {
        withContext(Dispatchers.IO) {
            database.update(term)
        }
    }
    @WorkerThread
    suspend fun isTermexist(): Boolean {
        return withContext(Dispatchers.IO) {
            val y = async {
                database.existTerm()
            }
            withContext(Dispatchers.Main){
                y.await()>0
            }
        }
    }
    @WorkerThread
    suspend fun getTerm(): TermDataTable? {
        return withContext(Dispatchers.IO) {
            val y = async {
                database.getTerm()
            }
            withContext(Dispatchers.Main){
                val t = y.await()
                if (t == null)
                    return@withContext null
                else
                    return@withContext t
            }
        }
    }
}