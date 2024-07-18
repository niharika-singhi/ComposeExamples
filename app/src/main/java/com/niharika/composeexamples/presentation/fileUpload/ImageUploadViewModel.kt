package com.niharika.composeexamples.presentation.fileUpload

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niharika.composeexamples.common.data.model.Resource
import com.niharika.composeexamples.data.repository.FileRepository
import com.niharika.composeexamples.util.RonaldoProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageUploadViewModel @Inject constructor(
    private val repository: FileRepository,
    private val ronaldo: RonaldoProvider,
) : ViewModel() {
    private val _uploadState = MutableStateFlow<Resource<Boolean>?>(null)
    val uploadState: StateFlow<Resource<Boolean>?> = _uploadState

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun onUpload() {
        viewModelScope.launch {
            _uploadState.value = Resource.Loading
            val result = repository.uploadImage(ronaldo.provide())
            _uploadState.value = result
        }
    }
}