package BL

import DAL.AppDatabase
import DAL.BookRepository
import DAL.TermRepository
import android.content.Context
import androidx.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class TermService(context: Context) {
    var dao = TermRepository(AppDatabase.getInstance(context).termDao())
    @WorkerThread
    suspend fun insert(term: Term) {
//        withContext(Dispatchers.IO) {
            dao.insert(term.db)
//        }
    }
    @WorkerThread
    suspend fun delete(term: Term) {
//        withContext(Dispatchers.IO) {
            dao.delete(term.db)
//        }
    }
    @WorkerThread
    suspend fun update(term: Term) {
//        withContext(Dispatchers.IO) {
            dao.update(term.db)
//        }
    }
    @WorkerThread
    suspend fun isTermexist(): Boolean {
//        return withContext(Dispatchers.IO) {
//            val y = async {
             return dao.isTermexist()
//            }
//            withContext(Dispatchers.Main){
//                y.await()>0
//            }
//        }
    }
    @WorkerThread
    suspend fun getTerm(): Term? {
//        return withContext(Dispatchers.IO) {
//            val y = async {
               return Term(dao.getTerm()!!)
//            }
//            withContext(Dispatchers.Main){
//                val t = y.await()
//                return@withContext if (t == null)
//                    null
//                else
//                    Term(t)
//            }
//        }
    }
}