package core.services

import data.AppDatabase
import data.ReadRepository
import android.content.Context
import core.Read
import core.ReadBook

class ReadService(context: Context) {
    private var repository = ReadRepository(AppDatabase.getInstance(context).readDatabase())
    suspend fun insert(read: Read): Boolean {
       return repository.insert(read.getReadDataTable())
    }

    suspend fun delete(read: Read): Boolean {
        return  repository.delete(read.getReadDataTable())
    }

    suspend fun getAllReadsWithBookName(): List<ReadBook> {
        return repository.getAllReadsWithBookName()?.map { ReadBook(it.readDataTable, it.bookName) }?: listOf()
    }

}