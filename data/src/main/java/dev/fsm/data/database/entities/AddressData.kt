package dev.fsm.data.database.entities

import androidx.room.ColumnInfo

data class AddressData(
    @ColumnInfo("id"            ) val id            : String?   = null,
    @ColumnInfo("full_address"   ) val fullAddress   : String?   = null,
    @ColumnInfo("country"       ) val country       : String?   = null,
    @ColumnInfo("state_province" ) val stateProvince : String?   = null,
    @ColumnInfo("city"          ) val city          : String?   = null,
    @ColumnInfo("street"        ) val street        : String?   = null,
    @ColumnInfo("house"         ) val house         : String?   = null,
    @ColumnInfo("postal_code"    ) val postalCode    : String?   = null,
    @ColumnInfo("lat"           ) val lat           : Double?   = null,
    @ColumnInfo("lon"           ) val lon           : Double?   = null,
    @ColumnInfo("apartments"    ) val apartments    : String?   = null,
    @ColumnInfo("floor"         ) val floor         : String?   = null,
    @ColumnInfo("entrance"      ) val entrance      : String?   = null,
    @ColumnInfo("description"   ) val description   : String?   = null
)