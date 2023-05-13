package dev.fsm.presentation.model.file

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import dev.fsm.domain.models.files.FileData as DomainFileData

@Parcelize
data class FileData(
    val initialName: String? = null,
    val actualName: String,
    val size: Long? = null,
    val url: String,
) : Parcelable {
    companion object {
        fun FileData.toFileData(): DomainFileData = DomainFileData(
            initialName = initialName,
            actualName = actualName,
            size = size,
            url = url
        )

        fun DomainFileData.toFileData(): FileData = FileData(
            initialName = initialName,
            actualName = actualName,
            size = size,
            url = url
        )
    }
}