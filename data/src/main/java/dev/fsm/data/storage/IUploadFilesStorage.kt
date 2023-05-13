package dev.fsm.data.storage

import dev.fsm.data.network.api.models.global.FileData
import dev.fsm.data.storage.models.params.files.UploadFilesParams
import dev.fsm.data.utils.Response

interface IUploadFilesStorage {

    suspend fun upload(params: UploadFilesParams): Response<List<FileData>>
}