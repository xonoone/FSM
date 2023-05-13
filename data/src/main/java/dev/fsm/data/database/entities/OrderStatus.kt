package dev.fsm.data.database.entities

import androidx.room.ColumnInfo

data class OrderStatus(
    @ColumnInfo("status_code" ) val code : String? = null,
    @ColumnInfo("status_name" ) val name : String? = null
)
