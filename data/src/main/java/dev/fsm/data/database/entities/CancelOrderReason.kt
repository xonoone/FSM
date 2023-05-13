package dev.fsm.data.database.entities

import androidx.room.ColumnInfo

data class CancelOrderReason(
    @ColumnInfo("id")       val id   : String = "",
    @ColumnInfo("name")     val name : String = ""
)
