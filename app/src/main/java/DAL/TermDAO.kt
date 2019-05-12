package DAL

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TermDAO {

    @Query("SELECT * FROM termdb")
    fun getTerm(): List<Termdb>?

    @Insert
    suspend fun insert(term: Termdb)

    @Delete
    fun delete(term: Termdb)

}