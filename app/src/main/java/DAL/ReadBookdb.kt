package DAL

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Relation

data class ReadBookdb(
        @Embedded
        val read: Readdb,

        @ColumnInfo(name = "name")
        val bookName : String
)