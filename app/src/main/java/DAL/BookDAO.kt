package DAL

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookDAO {

    @Query("SELECT * FROM Bookdb")
    fun getAllBookWithReads(): List<BookReads>

    @Insert
    fun insert(book: Bookdb)

    @Delete
    fun delete(todo: Bookdb)

    @Query("SELECT * FROM Bookdb")
    fun getAllBook() : List<Bookdb>

}