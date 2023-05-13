package dev.fsm.domain.models.files

data class FileData(
    val initialName : String? = null,
    val actualName  : String  = "",
    val size        : Long?   = null,
    val url         : String  = ""
)