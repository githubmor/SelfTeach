package DAL

import androidx.room.Embedded

data class BookSumReadDataTable(
        @Embedded
        val bookDataTable: BookDataTable,
        var readSum: Int
)