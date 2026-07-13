package com.swordfish.lemuroid.app.mobile.feature.main

import java.io.File

object FileNameSanitizer {
    fun sanitize(fileName: String?): String {
        var finalName = fileName ?: "imported_game_${System.currentTimeMillis()}"

        finalName = File(finalName.replace('\\', '/')).name
        if (finalName.isBlank() || finalName == "." || finalName == "..") {
            throw IllegalArgumentException("Invalid filename after sanitization")
        }

        return finalName
    }
}
