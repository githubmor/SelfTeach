package BL

import DAL.AppDatabase
import DAL.ReadRepository
import android.content.Context

class ReadService(context: Context) {
    private var repository = ReadRepository(AppDatabase.getInstance(context).readDatabase())
    suspend fun insert(read: Read) {
        repository.insert(read.getReadDataTable())
    }

    suspend fun delete(read: Read) {
        repository.delete(read.getReadDataTable())
    }

    suspend fun getAllReadsWithBookName(): List<ReadBook>? {
        return repository.getAllReadsWithBookName()!!.map { ReadBook(it.readDataTable, it.bookName) }
    }

}