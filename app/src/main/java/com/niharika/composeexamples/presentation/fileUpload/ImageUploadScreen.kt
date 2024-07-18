package com.niharika.composeexamples.presentation.fileUpload

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.niharika.composeexamples.R
import com.niharika.composeexamples.common.data.model.Resource
import kotlinx.coroutines.launch

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun ImageUploadScreen(
    viewModel: ImageUploadViewModel = hiltViewModel(),
) {
    val uploadState by viewModel.uploadState.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }) { contentPadding ->

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(contentPadding).fillMaxSize()
        ) {
            when (uploadState) {
                is Resource.Loading -> {
                    CircularProgressIndicator()
                }

                is Resource.Success<*> -> {
                    val result = (uploadState as Resource.Success<*>).data
                    Text(text = "Upload Successful: $result")

                }

                is Resource.Error -> {
                    val errorMessage = (uploadState as Resource.Error).errorMessage
                    scope.launch {
                        snackbarHostState.showSnackbar("Error: $errorMessage")
                    }

                    //Text(text = "Error: $errorMessage")
                }

                else -> {} //Dont do anything wait for the button to be clicke
            }

            Button(
                modifier = Modifier.align(Alignment.BottomCenter),
                onClick = viewModel::onUpload
            ) {
                Text(text = stringResource(id = R.string.upload_image))
            }
        }
    }
}