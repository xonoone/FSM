package dev.fsm.data.database.entities

import androidx.room.ColumnInfo

data class Contact(
    @ColumnInfo("id")           val id         : String   = "",
    @ColumnInfo("client_name")  val clientName : String?  = null,
    @ColumnInfo("phone")        val phone      : String?  = null,
    @ColumnInfo("email")        val email      : String?  = null,
    @ColumnInfo("is_main")       val isMain     : Boolean  = false
)
