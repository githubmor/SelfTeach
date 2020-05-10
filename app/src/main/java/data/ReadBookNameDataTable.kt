package data

import androidx.room.ColumnInfo
import androidx.room.Embedded

data class ReadBookNameDataTable(
        @Embedded
        val readDataTable: ReadDataTable,
        @ColumnInfo(name = "name")
        var bookName : String
)