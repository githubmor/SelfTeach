package DAL

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReadDAO {
//    @Query("SELECT * FROM Readdb")
//    fun getAllReads(): List<Readdb>?

    @Insert
    fun insert(read: Readdb)

    @Delete
    fun delete(read: Readdb)

    @Query("SELECT Readdb.*,Bookdb.name FROM Readdb LEFT JOIN Bookdb ON Readdb.bookId = Bookdb.id")
    fun getAllReadsWithBookName(): List<ReadBookNamedb>?


}