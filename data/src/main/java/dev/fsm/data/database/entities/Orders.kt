package dev.fsm.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.fsm.data.utils.config.DatabaseConfig.ORDERS

@Entity(tableName = ORDERS)
data class Orders(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("order_id")     val id         : String = "",
    @ColumnInfo("name")         val name       : String = "",
    @ColumnInfo("type")         val type       : String = "",
    @ColumnInfo("statusCode")   val statusCode : String = "",
    @ColumnInfo("date")         val date       : String = "",
    @ColumnInfo("duration")     val duration   : String = "",
    @ColumnInfo("address")      val address    : String = ""
)
