package data

import androidx.room.*


@Dao
interface BookDatabase {

    @Transaction
    @Query("SELECT * FROM bookdatatable")
     fun getAllBookWithListReads(): List<BookReadsDataTable>?

    @Query("SELECT bookdatatable.*,SUM(readdatatable.pageReadCount) as readSum FROM bookdatatable INNER JOIN readdatatable ON bookdatatable.id = readdatatable.bookId")
     fun getAllBookWithSumRead(): List<BookSumReadDataTable>?

    @Insert
     fun insertBook(bookDataTable: BookDataTable): Long

    @Query("DELETE FROM bookdatatable")
     fun deleteAllBook():Int

    @Delete
     fun deleteBook(bookDataTable: BookDataTable):Int

    @Query("SELECT COUNT(*) FROM bookdatatable")
     fun existBook(): Int
}