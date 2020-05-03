package DAL

import BL.Term
import androidx.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class TermRepository(private val dao: TermDAO) {

    @WorkerThread
    suspend fun insert(term: Termdb) {
        withContext(Dispatchers.IO) {
            dao.insert(term)
        }
    }
    @WorkerThread
    suspend fun delete(term: Termdb) {
        withContext(Dispatchers.IO) {
            dao.delete(term)
        }
    }
    @WorkerThread
    suspend fun update(term: Termdb) {
        withContext(Dispatchers.IO) {
            dao.update(term)
        }
    }
    @WorkerThread
    suspend fun isTermexist(): Boolean {
        return withContext(Dispatchers.IO) {
            val y = async {
                dao.existTerm()
            }
            withContext(Dispatchers.Main){
                y.await()>0
            }
        }
    }
    @WorkerThread
    suspend fun getTerm(): Termdb? {
        return withContext(Dispatchers.IO) {
            val y = async {
                dao.getTerm()
            }
            withContext(Dispatchers.Main){
                val t = y.await()
                return@withContext if (t == null)
                    null
                else
                    t
            }
        }
    }
}