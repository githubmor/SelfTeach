package DAL

import androidx.room.Embedded

data class Book_SumRead_db (
        @Embedded
        val book: Book_db,
        var readSum: Int
)