package dev.fsm.domain.models.files

import java.io.File

data class UploadFilesParams(
    val files:  List<File>
)