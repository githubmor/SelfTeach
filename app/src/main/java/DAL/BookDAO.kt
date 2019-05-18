package DAL

import androidx.room.*

@Dao
interface BookDAO {

    @Query("SELECT * FROM Bookdb")
    fun getAllBookWithReads(): List<BookReadsdb>?

    @Insert
    fun insert(book: Bookdb)

    @Update
    fun update(book: Bookdb)

    @Delete
    fun delete(todo: Bookdb)

    @Query("SELECT * FROM Bookdb")
    fun getAllBook() : List<Bookdb>?

    @Query("SELECT COUNT(*) FROM Bookdb")
    fun existBook(): Int
}