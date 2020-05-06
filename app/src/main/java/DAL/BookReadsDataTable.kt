package DAL

import androidx.room.Embedded
import androidx.room.Relation

class BookReadsDataTable(
        @Embedded
        var bookDataTable: BookDataTable,

        @Relation(
                parentColumn = "id",
                entityColumn = "bookId",
                entity = ReadDataTable::class)
        var readDataTableLists: List<ReadDataTable> = listOf()
)

