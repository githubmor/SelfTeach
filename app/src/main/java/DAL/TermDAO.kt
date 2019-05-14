package DAL

import androidx.room.*

@Dao
interface TermDAO {

    @Query("SELECT * FROM termdb")
    fun getTerm(): Termdb

    @Query("SELECT COUNT(*) FROM termdb")
    fun existTerm(): Int

    @Insert
    fun insert(term: Termdb)

    @Update
    fun update(term: Termdb)

    @Delete
    fun delete(term: Termdb)

}