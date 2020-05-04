package DAL

import androidx.room.Embedded
import androidx.room.Relation

class Book_Reads_db(
        @Embedded
        var book: Book_db,

        @Relation(
                parentColumn = "id",
                entityColumn = "bookId",
                entity = Read_db::class)
        var reads: List<Read_db> = listOf()
)

