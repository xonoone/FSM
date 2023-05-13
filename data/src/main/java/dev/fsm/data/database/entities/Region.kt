package dev.fsm.data.database.entities

import androidx.room.ColumnInfo

data class Region(
    @ColumnInfo("parent_region")     val parentRegion : String             = "",
    @ColumnInfo("child_regions")     val childRegions : ArrayList<String>? = arrayListOf()
)
