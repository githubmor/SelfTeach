package DAL

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Relation

class ReadBook(
        @Embedded
        val read: Readdb,

        @ColumnInfo(name = "name")
        val bookName : String
)