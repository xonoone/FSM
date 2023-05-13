package dev.fsm.domain.usecase.files

import dev.fsm.domain.models.files.FileData
import dev.fsm.domain.models.files.UploadFilesParams
import dev.fsm.domain.repository.IUploadFilesRepository
import dev.fsm.domain.utils.Response

class UploadFiles(
    private val repository: IUploadFilesRepository
) {
    suspend fun upload(params: UploadFilesParams): Response<List<FileData>> =
        repository.upload(params = params)
}