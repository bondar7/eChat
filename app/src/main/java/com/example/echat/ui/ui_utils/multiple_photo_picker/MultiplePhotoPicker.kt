package com.example.echat.ui.ui_utils.multiple_photo_picker

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect

@Composable
fun MultiplePhotoPicker(
    onResult: (uris: List<Uri?>) -> Unit
) {
    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris ->
            onResult(uris)
        }
    )

    DisposableEffect(Unit) {
        multiplePhotoPickerLauncher.launch(
            // here we specified what we want to show in our photo picker
            // (photos and videos or maybe only photos or videos)
            PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly)
        )

        onDispose {
            // Clean up if needed
        }
    }
}