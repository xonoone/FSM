package dev.fsm.domain.models.global

data class Region(
    val parentRegion : String         =     "",
    val childRegions : List<String>   =     listOf()
)