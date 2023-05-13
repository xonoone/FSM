package dev.fsm.data.database.entities

import androidx.room.ColumnInfo

data class ClientType(
    @ColumnInfo("type_id"   ) val id   : String? = null,
    @ColumnInfo("type_name" ) val name : String? = null
)