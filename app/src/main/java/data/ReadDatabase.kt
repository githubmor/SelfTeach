package data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReadDatabase {
    @Insert
     fun insertRead(readDataTable: ReadDataTable):Long

    @Delete
     fun deleteRead(readDataTable: ReadDataTable):Int

    @Query("SELECT readdatatable.*,bookdatatable.name FROM readdatatable LEFT JOIN bookdatatable ON readdatatable.bookId = bookdatatable.id")
     fun getAllReadsWithBookName(): List<ReadBookNameDataTable>?


}