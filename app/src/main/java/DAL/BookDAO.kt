package DAL

import BL.BookRead
import androidx.room.*

@Dao
interface BookDAO {

    @Query("SELECT * FROM Bookdb")
    fun getAllBookWithReads(): List<BookReadsdb>?

    @Query("SELECT Bookdb.*,SUM(Readdb.pageRead) as readSum FROM Bookdb INNER JOIN Readdb ON Bookdb.id = Readdb.bookId")
    fun getAllNewBook(): List<BookSumReaddb>?

    @Insert
    fun insert(book: Bookdb)

//    @Update
//    fun update(book: Bookdb)

    @Query("DELETE FROM Bookdb")
    fun deleteAll()

    @Delete
    fun delete(todo: Bookdb)

    @Query("SELECT * FROM Bookdb")
    fun getAllBook() : List<Bookdb>?

    @Query("SELECT COUNT(*) FROM Bookdb")
    fun existBook(): Int
}