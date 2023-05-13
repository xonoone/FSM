package dev.fsm.data.database.entities

import androidx.room.ColumnInfo

data class OrderField(
    @ColumnInfo("id"                     )  val id       : String?  = null,
    @ColumnInfo("field_name"             )  val name     : String?  = null,
    @ColumnInfo("type"                   )  val type     : String?  = null,
    @ColumnInfo("owner"                  )  val owner    : String?  = null,
    @ColumnInfo("_value"                 )  val value    : Any?     = null,
    @ColumnInfo("is_visible_to_executor" )  val visible  : Boolean? = null,
    @ColumnInfo("required"               )  val required : Boolean? = null
)
