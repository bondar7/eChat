package com.example.echat.communication.audio.player

import android.content.Context
import android.media.MediaPlayer
import androidx.core.net.toUri
import java.io.File

class AudioPlayerImpl(private val context: Context) : AudioPlayer {

    private var player: MediaPlayer? = null

    override fun play(audioData: ByteArray) {
        val tempFile = createTempFile("tempAudio", ".mp3", context.cacheDir)
        tempFile.writeBytes(audioData)

        val uri = tempFile.toUri()

        MediaPlayer.create(context, uri).apply {
            player = this
            start()
        }
    }

    override fun stop() {
        player?.stop()
        player?.release()
        player = null
    }
}