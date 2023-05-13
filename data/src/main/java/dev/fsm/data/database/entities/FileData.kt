package dev.fsm.data.database.entities

import androidx.room.ColumnInfo

data class FileData(
    @ColumnInfo("initial_name")     val initialName : String? = null,
    @ColumnInfo("actual_name")      val actualName  : String  = "",
    @ColumnInfo("size")             val size        : Long?   = null,
    @ColumnInfo("url")              val url         : String  = ""
)
