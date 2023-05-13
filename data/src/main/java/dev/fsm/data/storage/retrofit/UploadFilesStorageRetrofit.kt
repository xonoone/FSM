package dev.fsm.data.storage.retrofit

import dev.fsm.data.network.api.ApiService
import dev.fsm.data.network.api.models.global.FileData
import dev.fsm.data.storage.IUploadFilesStorage
import dev.fsm.data.storage.models.params.files.UploadFilesParams
import dev.fsm.data.utils.Request
import dev.fsm.data.utils.Response
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

const val POST_UPLOAD_FILES_TAG = "files"

internal class UploadFilesStorageRetrofit @Inject constructor(
    private val api: ApiService,
    private val request: Request
): IUploadFilesStorage {

    override suspend fun upload(params: UploadFilesParams): Response<List<FileData>> =
        request.safeApiCallWithAuth {
            val partList = params.files.map { file ->
                file.toPart(partName = POST_UPLOAD_FILES_TAG)
            }
            api.uploadFiles(files = partList)
        }

    private fun File.toPart(partName: String): MultipartBody.Part {
        val requestFile = this.asRequestBody(this.extension.toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(partName, this.name, requestFile)
    }
}