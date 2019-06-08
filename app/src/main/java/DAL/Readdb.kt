package DAL

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(foreignKeys = (arrayOf(ForeignKey(
        entity = Bookdb::class,
        onDelete = ForeignKey.CASCADE,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("bookId")))),indices = (arrayOf(Index("bookId"))))
data class Readdb(
        @PrimaryKey(autoGenerate = true)
        var id : Int,

        var bookId:Int,

        var pageRead : Int,

        var readDate : String

)