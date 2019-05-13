package DAL

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TermDAO {

    @Query("SELECT * FROM termdb")
    fun getTerm(): Termdb

    @Query("SELECT COUNT(*) FROM termdb")
    fun existTerm(): Int

    @Insert
    fun insert(term: Termdb)

    @Delete
    fun delete(term: Termdb)

}