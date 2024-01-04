package com.example.echat.ui.circular_avatar

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import coil.compose.rememberAsyncImagePainter
import com.example.echat.R

@Composable
fun CircularUserAvatar(
    avatar: ByteArray?,
    imageSize: Dp,
) {
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    Box(modifier = Modifier
        .clip(CircleShape)
        .size(imageSize)
    ) {
        Image(
            painter = if (avatar == null) {
                painterResource(id = R.drawable.avatar)
            } else {
                rememberAsyncImagePainter(
                    model = avatar
                )
            },
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offset.x,
                    translationY = offset.y
                )
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        // Apply changes to scale and offset on pan and zoom gestures
                        scale *= zoom
                        offset = if (scale > 1) {
                            Offset(
                                x = (offset.x + pan.x * scale).coerceIn(
                                    (1 - scale) * size.width,
                                    scale * size.width - size.width * scale
                                ),
                                y = (offset.y + pan.y * scale).coerceIn(
                                    (1 - scale) * size.height,
                                    scale * size.height - size.height * scale
                                )
                            )
                        } else {
                            Offset.Zero
                        }
                    }
                },
            contentScale = ContentScale.Crop // Add this line to crop the image
        )
    }
}