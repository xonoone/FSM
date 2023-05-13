package dev.fsm.data.storage.models.params.files

import java.io.File
import dev.fsm.domain.models.files.UploadFilesParams as DomainUploadFilesParams
data class UploadFilesParams(
    val files   :   List<File>
) {
    companion object {
        fun DomainUploadFilesParams.toUploadFilesParams(): UploadFilesParams = UploadFilesParams(files = files)
    }
}