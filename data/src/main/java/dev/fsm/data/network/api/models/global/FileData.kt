package dev.fsm.data.network.api.models.global

import com.google.gson.annotations.SerializedName
import dev.fsm.domain.models.files.FileData as DomainFileData
data class FileData(
    @SerializedName("initialName" ) val initialName : String? = null,
    @SerializedName("name"        ) val actualName  : String? = null,
    @SerializedName("size"        ) val size        : Long?   = null,
    @SerializedName("fullPath"    ) val url         : String? = null
) {
    companion object {
        fun Collection<FileData>.toFileData(): Collection<DomainFileData> = map { it.toFileData() }
        fun FileData.toFileData(): DomainFileData = DomainFileData(
            initialName = initialName,
            actualName = actualName ?: "",
            size = size,
            url = url ?: ""
        )

        fun DomainFileData.toFileData(): FileData = FileData(
            initialName = initialName,
            actualName = actualName,
            size = size,
            url = url
        )
    }
}