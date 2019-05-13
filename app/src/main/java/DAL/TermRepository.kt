package DAL

import androidx.annotation.WorkerThread

class TermRepository(private val termDAO: TermDAO) {
    val term = termDAO.getTerm()

    @WorkerThread
    suspend fun insert(term: Termdb) {
        termDAO.insert(term)
    }

    fun isTermexist(): Int {
        return termDAO.existTerm()
    }
}