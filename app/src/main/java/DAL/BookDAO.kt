package DAL

import androidx.room.*

@Dao
interface BookDAO {

    @Transaction
    @Query("SELECT * FROM Book_db")
    fun getAllBookWithReads(): List<Book_Reads_db>?

    @Query("SELECT Book_db.*,SUM(Read_db.pageRead) as readSum FROM Book_db INNER JOIN Read_db ON Book_db.id = Read_db.bookId")
    fun getAllNewBook(): List<Book_SumRead_db>?

    @Insert
    fun insert(book: Book_db)

//    @Update
//    fun update(book: Book_db)

    @Query("DELETE FROM Book_db")
    fun deleteAll()

    @Delete
    fun delete(todo: Book_db)

//    @Query("SELECT * FROM Book_db")
//    fun getAllBookWithSumRead() : List<Book_db>?

    @Query("SELECT COUNT(*) FROM Book_db")
    fun existBook(): Int
}