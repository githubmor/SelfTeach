package data

import androidx.room.*

@Dao
interface TermDatabase {

    @Query("SELECT * FROM termdatatable")
     fun getTerm(): TermDataTable?

    @Query("SELECT COUNT(*) FROM termdatatable")
     fun existTerm(): Int

    @Insert
     fun insert(term: TermDataTable):Long

    @Update
     fun update(term: TermDataTable):Int

    @Delete
     fun delete(term: TermDataTable):Int

}