package dev.fsm.presentation.screens.user.info.models

import dev.fsm.domain.models.global.Region as DomainRegion

data class Region(
    val parentRegion : String         =     "",
    val childRegions : List<String>   =     listOf()
) {
    companion object {
        fun DomainRegion.toRegion(): Region = Region(
            parentRegion = parentRegion,
            childRegions = childRegions
        )
    }
}