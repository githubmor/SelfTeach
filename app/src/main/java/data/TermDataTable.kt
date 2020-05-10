package data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class TermDataTable(
        @PrimaryKey(autoGenerate = true)
        var id : Int,
        var name: String,
        var startDate: String,
        var endDate : String
)