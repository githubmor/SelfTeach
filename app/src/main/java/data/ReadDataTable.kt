package data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(foreignKeys = (arrayOf(ForeignKey(
        entity = BookDataTable::class,
        onDelete = ForeignKey.CASCADE,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("bookId")))),indices = (arrayOf(Index("bookId"))))
data class ReadDataTable(
        @PrimaryKey(autoGenerate = true)
        var id : Int,

        var bookId:Int,

        var pageReadCount: Int,

        var readDate : String

)