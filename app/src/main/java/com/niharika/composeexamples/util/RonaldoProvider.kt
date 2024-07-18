package com.niharika.composeexamples.util

import android.app.Application
import java.io.File

class RonaldoProvider(
    private val application: Application,
) {

    fun provide(): File {
        val file = File(application.cacheDir, "img.png")
        file.createNewFile()
        file.outputStream().use { stream ->
            application.assets.open("image/img.png").copyTo(stream)
        }
        return file
    }
}