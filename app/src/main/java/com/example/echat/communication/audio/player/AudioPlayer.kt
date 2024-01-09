package com.example.echat.communication.audio.player

import java.io.File

interface AudioPlayer {
    fun play(audioData: ByteArray)
    fun stop()
}