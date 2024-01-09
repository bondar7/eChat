package com.example.echat.communication.audio.recorder

import java.io.File

interface AudioRecorder {
    fun start(outputFile: File)
    fun stop()
}