package dev.fsm.data.database.entities

import androidx.room.*
import dev.fsm.data.utils.config.DatabaseConfig.ORDER

@Entity(tableName = ORDER)
data class Order(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("id")                   val id:                 String                 = "",
    @ColumnInfo("name")                 val name:               String                 = "",
    @ColumnInfo("planed_date_time")     val plannedDateTime:    String                 = "",
    @ColumnInfo("road_time_start")      val roadTimeStart:      String?                = null,
    @ColumnInfo("road_time_stop")       val roadTimeStop:       String?                = null,
    @ColumnInfo("start_time")           val startTime:          String?                = null,
    @ColumnInfo("stop_time")            val stopTime:           String?                = null,
    @ColumnInfo("brigade_executor")     val brigadeExecutor:    String?                = null,
    @ColumnInfo("is_checked")           val isChecked:          Boolean                = false,
    @ColumnInfo("regions")              val regions:            ArrayList<Region>?     = arrayListOf(),
    @ColumnInfo("contacts")             val contacts:           ArrayList<Contact>?    = arrayListOf(),
    @ColumnInfo("fields")               val fields:             ArrayList<OrderField>? = arrayListOf(),
    @ColumnInfo("cancel_reason")        val cancelReason:       CancelOrderReason?     = null,
    @ColumnInfo("order_status")         val orderStatus:        OrderStatus            = OrderStatus(),
    @ColumnInfo("client")               val client:             Client?                = null,
    @ColumnInfo("executor")             val executor:           User?                  = null,
    @ColumnInfo("responsible")          val responsible:        User?                  = null,
    @ColumnInfo("address")              val address:            AddressData            = AddressData()
)
