package BL

import DAL.TermDAO
import DAL.Termdb
import androidx.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class TermRepository(private val dao: TermDAO) {

    @WorkerThread
    suspend fun insert(term: Term) {
        withContext(Dispatchers.IO) {
            dao.insert(term.db)
        }
    }
    @WorkerThread
    suspend fun delete(term: Term) {
        withContext(Dispatchers.IO) {
            dao.delete(term.db)
        }
    }
    @WorkerThread
    suspend fun update(term: Term) {
        withContext(Dispatchers.IO) {
            dao.update(term.db)
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
    suspend fun getTerm(): Term? {
        return withContext(Dispatchers.IO) {
            val y = async {
                dao.getTerm()
            }
            withContext(Dispatchers.Main){
                val t = y.await()
                return@withContext if (t == null)
                    null
                else
                    Term(t)
            }
        }
    }
}