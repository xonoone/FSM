package dev.fsm.domain.repository

import dev.fsm.domain.models.files.FileData
import dev.fsm.domain.models.files.UploadFilesParams
import dev.fsm.domain.utils.Response

interface IUploadFilesRepository {
    suspend fun upload(params: UploadFilesParams): Response<List<FileData>>
}