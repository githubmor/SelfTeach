package data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class TermRepository(private val database: TermDatabase) {

    @WorkerThread
    suspend fun insert(term: TermDataTable):Boolean {
        return withContext(Dispatchers.IO) {
            val y = async {
                database.insert(term)
            }
            withContext(Dispatchers.Main) {
                term.id = y.await().toInt()
                term.id > 0
            }

        }
    }
    @WorkerThread
    suspend fun delete(term: TermDataTable):Boolean {
        return withContext(Dispatchers.IO) {
            val y = async {
                database.delete(term)
            }
            withContext(Dispatchers.Main) {
                y.await() == 1
            }

        }
    }
    @WorkerThread
    suspend fun update(term: TermDataTable):Boolean {
        return withContext(Dispatchers.IO) {
            val y = async {
                database.update(term)
            }
            withContext(Dispatchers.Main) {
                y.await() == 1
            }

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
                y.await()
            }
        }
    }
}