package DAL

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class BookDataTable(
        @PrimaryKey(autoGenerate = true)
        var id : Int,
        var name: String,
        var pageCount: Int,
        var priority: Int
)