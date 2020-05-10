package core.services

import data.AppDatabase
import data.TermRepository
import android.content.Context
import core.Term

class TermService(context: Context) {
    private var repository = TermRepository(AppDatabase.getInstance(context).termDatabase())
    suspend fun insert(term: Term): Boolean {
       return repository.insert(term.getTermDataTable())
    }

    suspend fun delete(term: Term): Boolean {
        return repository.delete(term.getTermDataTable())
    }

    suspend fun update(term: Term): Boolean {
        return repository.update(term.getTermDataTable())
    }

    suspend fun isTermExist(): Boolean {
        return repository.isTermexist()
    }

    suspend fun getTerm(): Term {
        return repository.getTerm()?.let { Term(it) }?:Term()
    }
}