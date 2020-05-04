package DAL

import androidx.room.ColumnInfo
import androidx.room.Embedded

data class Read_BookName_db(
        @Embedded
        val read: Read_db,
        @ColumnInfo(name = "name")
        var bookName : String
)