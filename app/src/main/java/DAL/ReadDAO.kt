package DAL

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReadDAO {
//    @Query("SELECT * FROM Read_db")
//    fun getAllReads(): List<Read_db>?

    @Insert
    fun insert(read: Read_db)

    @Delete
    fun delete(read: Read_db)

    @Query("SELECT Read_db.*,Book_db.name FROM Read_db LEFT JOIN Book_db ON Read_db.bookId = Book_db.id")
    fun getAllReadsWithBookName(): List<Read_BookName_db>?


}