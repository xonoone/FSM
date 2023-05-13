package dev.fsm.data.database.entities

import androidx.room.ColumnInfo

data class User2(
    @ColumnInfo(name = "user_id")       val id         : String             = "",
    @ColumnInfo(name = "first_name")    val firstName  : String             = "",
    @ColumnInfo(name = "last_name")     val lastName   : String             = "",
    @ColumnInfo(name = "middle_name")   val middleName : String             = "",
    @ColumnInfo(name = "phone")         val phone      : String             = "",
    @ColumnInfo(name = "email")         val email      : String             = "",
    @ColumnInfo(name = "photo")         val photo      : FileData?          = null,
    @ColumnInfo(name = "skills")        val skills     : ArrayList<String>? = arrayListOf(),
    @ColumnInfo(name = "regions")       val regions    : ArrayList<Region>? = arrayListOf()
)