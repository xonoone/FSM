package dev.fsm.data.network.api.models.global

import com.google.gson.annotations.SerializedName
import dev.fsm.domain.models.global.Region as DomainRegion
data class Region(
    @SerializedName("regionName"     ) val parentRegion : String?            = null,
    @SerializedName("selectedValues" ) val childRegions : List<String>       = listOf()
) {
    companion object {
        fun Collection<Region>.toRegion(): Collection<DomainRegion> = map { it.toRegion() }
        fun Region.toRegion(): DomainRegion = DomainRegion(
            parentRegion = parentRegion ?: "",
            childRegions = childRegions
        )
    }
}
