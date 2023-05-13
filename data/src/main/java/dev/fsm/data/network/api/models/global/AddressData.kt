package dev.fsm.data.network.api.models.global

import com.google.gson.annotations.SerializedName
import dev.fsm.domain.models.global.Address

data class AddressData(
    @SerializedName("id"            ) val id            : String?   = null,
    @SerializedName("fullAddress"   ) val fullAddress   : String?   = null,
    @SerializedName("country"       ) val country       : String?   = null,
    @SerializedName("stateProvince" ) val stateProvince : String?   = null,
    @SerializedName("city"          ) val city          : String?   = null,
    @SerializedName("street"        ) val street        : String?   = null,
    @SerializedName("house"         ) val house         : String?   = null,
    @SerializedName("postalCode"    ) val postalCode    : String?   = null,
    @SerializedName("lat"           ) val lat           : Double?   = null,
    @SerializedName("lon"           ) val lon           : Double?   = null,
    @SerializedName("apartments"    ) val apartments    : String?   = null,
    @SerializedName("floor"         ) val floor         : String?   = null,
    @SerializedName("entrance"      ) val entrance      : String?   = null,
    @SerializedName("description"   ) val description   : String?   = null
) {
    companion object {
        fun AddressData.toAddressData(): Address = Address(
            fullAddress = fullAddress,
            country = country,
            stateProvince = stateProvince,
            city = city,
            street = street,
            house = house,
            postalCode = postalCode,
            lat = lat,
            lon = lon,
            apartments = apartments,
            floor = floor,
            entrance = entrance,
            description = description
        )
    }
}
