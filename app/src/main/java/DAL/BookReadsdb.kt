package DAL

import androidx.room.Embedded
import androidx.room.Relation

class BookReadsdb(
        @Embedded
        var book: Bookdb,

        @Relation(
                parentColumn = "id",
                entityColumn = "bookId",
                entity = Readdb::class)
        var reads: List<Readdb> = listOf()
)