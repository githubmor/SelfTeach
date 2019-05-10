package DAL

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Readdb(

        @PrimaryKey(autoGenerate = true)
        var id : Int,

        var bookId:Int,

        var pageRead : Int,

        var readDate : String

)