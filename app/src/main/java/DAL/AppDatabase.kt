package DAL

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Bookdb::class,Readdb::class,Termdb::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDAO
    abstract fun readDao(): ReadDAO
    abstract fun termDao(): TermDAO

}