package dev.fsm.data.repository

import dev.fsm.data.network.api.models.global.FileData.Companion.toFileData
import dev.fsm.data.storage.models.params.files.UploadFilesParams.Companion.toUploadFilesParams
import dev.fsm.data.storage.retrofit.UploadFilesStorageRetrofit
import dev.fsm.data.utils.Mappers.toResponse
import dev.fsm.domain.models.files.FileData
import dev.fsm.domain.models.files.UploadFilesParams
import dev.fsm.domain.repository.IUploadFilesRepository
import dev.fsm.domain.utils.Response

internal class UploadFilesRepositoryImpl(
    private val network: UploadFilesStorageRetrofit
) : IUploadFilesRepository {

    override suspend fun upload(params: UploadFilesParams): Response<List<FileData>> {
        val response = network.upload(params = params.toUploadFilesParams())
        return response.toResponse { it.toFileData().toList() }
    }
}