package com.daskoul.deltarch.core

import android.util.Log
import java.io.File

class LibretroCoreLoader(private val libDir: File) {
    companion object {
        private const val TAG = "LibretroCoreLoader"
    }

    fun findCorePath(candidates: List<String>): String? {
        for (name in candidates) {
            // Optimization: Unroll the inner loop to avoid allocating a temporary
            // List collection (listOf) on every single outer loop iteration.

            // 1. Try 'lib' prefix first (standard Android extracted-library path)
            val libPrefixedFile = File(libDir, "lib${name}.so")
            if (libPrefixedFile.exists()) {
                Log.i(TAG, "Core found: ${libPrefixedFile.absolutePath}")
                return libPrefixedFile.absolutePath
            }
            Log.d(TAG, "Not found: ${libPrefixedFile.absolutePath}")

            // 2. Try standard name without the prefix
            val standardFile = File(libDir, "${name}.so")
            if (standardFile.exists()) {
                Log.i(TAG, "Core found: ${standardFile.absolutePath}")
                return standardFile.absolutePath
            }
            Log.d(TAG, "Not found: ${standardFile.absolutePath}")
        }
        return null
    }
}
