package BL

import DAL.AppDatabase
import DAL.TermRepository
import android.content.Context

class TermService(context: Context) {
    private var repository = TermRepository(AppDatabase.getInstance(context).termDatabase())
    suspend fun insert(term: Term) {
        repository.insert(term.termDataTable)
    }

    suspend fun delete(term: Term) {
        repository.delete(term.termDataTable)
    }

    suspend fun update(term: Term) {
        repository.update(term.termDataTable)
    }

    suspend fun isTermExist(): Boolean {
        return repository.isTermexist()
    }

    suspend fun getTerm(): Term? {
        return Term(repository.getTerm()!!)
    }
}