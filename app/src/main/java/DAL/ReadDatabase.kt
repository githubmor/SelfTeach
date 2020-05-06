package DAL

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReadDatabase {
    @Insert
    fun insertRead(readDataTable: ReadDataTable)

    @Delete
    fun deleteRead(readDataTable: ReadDataTable)

    @Query("SELECT readdatatable.*,bookdatatable.name FROM readdatatable LEFT JOIN bookdatatable ON readdatatable.bookId = bookdatatable.id")
    fun getAllReadsWithBookName(): List<ReadBookNameDataTable>?


}