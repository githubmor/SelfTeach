package DAL

import androidx.room.*


@Dao
interface BookDatabase {

    @Transaction
    @Query("SELECT * FROM bookdatatable")
    fun getAllBookWithListReads(): List<BookReadsDataTable>?

    @Query("SELECT bookdatatable.*,SUM(readdatatable.pageReadCount) as readSum FROM bookdatatable INNER JOIN readdatatable ON bookdatatable.id = readdatatable.bookId")
    fun getAllBookWithSumRead(): List<BookSumReadDataTable>?

    @Insert
    fun insertBook(bookDataTable: BookDataTable)

    @Query("DELETE FROM bookdatatable")
    fun deleteAllBook()

    @Delete
    fun deleteBook(bookDataTable: BookDataTable)

    @Query("SELECT COUNT(*) FROM bookdatatable")
    fun existBook(): Int
}