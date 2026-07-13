package com.daskoul.deltarch.core

import android.util.Log
import java.io.File

class LibretroCoreLoader(private val libDir: File) {
    companion object {
        private const val TAG = "LibretroCoreLoader"
    }

    fun findCorePath(candidates: List<String>): String? {
        // Cache the local directory file list in memory exactly once.
        // This cuts down synchronous disk traversals and provides O(1) loop checks.
        val fileSet = libDir.list()?.toSet() ?: emptySet()

        for (name in candidates) {
            // Try lib prefix first (standard Android extracted-library path)
            for (fileName in listOf("lib${name}.so", "${name}.so")) {
                if (fileSet.contains(fileName)) {
                    val file = File(libDir, fileName)
                    Log.i(TAG, "Core found: ${file.absolutePath}")
                    return file.absolutePath
                }
                Log.d(TAG, "Not found: ${File(libDir, fileName).absolutePath}")
            }
        }
        return null
    }
}
