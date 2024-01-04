package com.example.echat.ui.photo_picker

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.echat.MainViewModel
import com.example.echat.server.auth.AuthViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.URI

@Composable
fun PhotoPicker(
    onResult: (uri: Uri?) -> Unit
) {
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            onResult(uri)
        }
    )

    DisposableEffect(Unit) {
        photoPickerLauncher.launch(
            // here we specified what we want to show in our photo picker
            // (photos and videos or maybe only photos or videos)
            PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly)
        )

        onDispose {
            // Clean up if needed
        }
    }
}

// Функція, яка конвертує URI в байти
 suspend fun uriToByteArray(context: Context, uri: Uri): ByteArray? {
    return withContext(Dispatchers.IO) {
        try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            if (inputStream != null) {
                val outputStream = ByteArrayOutputStream()
                val buffer = ByteArray(1024)
                var bytesRead: Int
                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                }
                return@withContext outputStream.toByteArray()
            }
        } catch (e: IOException) {
            // Обробити виняток, якщо не вдається прочитати зображення
            e.printStackTrace()
        }
        return@withContext null
    }
}