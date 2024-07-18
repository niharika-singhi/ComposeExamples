package com.niharika.composeexamples.data.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.niharika.composeexamples.common.data.model.Resource
import com.niharika.composeexamples.data.remote.api.FileApi
import com.niharika.composeexamples.data.remote.constant.FileApiConst
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException

class FileRepository(
    private val api: FileApi, //TODO whether injection is required
) {

    ///////////////////////////////////////////////////////////////////////////
    // UPLOAD
    ///////////////////////////////////////////////////////////////////////////

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7) //TODO
    suspend fun uploadImage(file: File): Resource<Boolean> {
        return withContext(Dispatchers.IO) {
            return@withContext try { //TODO get this explanation
                api.uploadImage(
                    MultipartBody.Part.createFormData(
                        FileApiConst.TYPE_IMAGE,
                        file.name,
                        file.asRequestBody()
                    )
                )
                Resource.Success(true)
            } catch (e: HttpException) {
                Resource.Error("Error ${e.message ?: "Unknown error"}")
            } catch (e: IOException) {
                Resource.Error("Error ${e.message ?: "Unknown error"}")
            }
        }
    }
}