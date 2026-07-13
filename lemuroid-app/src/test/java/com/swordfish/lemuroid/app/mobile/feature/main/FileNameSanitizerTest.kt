package com.swordfish.lemuroid.app.mobile.feature.main

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import java.io.File

class FileNameSanitizerTest {

    @Test
    fun testValidNames() {
        assertEquals("game.rom", FileNameSanitizer.sanitize("game.rom"))
        assertEquals("super_mario.zip", FileNameSanitizer.sanitize("super_mario.zip"))
    }

    @Test
    fun testPathTraversal() {
        assertEquals("game.rom", FileNameSanitizer.sanitize("../../../game.rom"))
        assertEquals("game.rom", FileNameSanitizer.sanitize("some/path/game.rom"))
        assertEquals("game.rom", FileNameSanitizer.sanitize("..\\..\\game.rom"))
        assertEquals("game.rom", FileNameSanitizer.sanitize("C:\\Windows\\game.rom"))
    }

    @Test
    fun testInvalidNames() {
        assertThrows(IllegalArgumentException::class.java) {
            FileNameSanitizer.sanitize("")
        }
        assertThrows(IllegalArgumentException::class.java) {
            FileNameSanitizer.sanitize("   ")
        }
        assertThrows(IllegalArgumentException::class.java) {
            FileNameSanitizer.sanitize("../")
        }
        assertThrows(IllegalArgumentException::class.java) {
            FileNameSanitizer.sanitize("..\\")
        }
        assertThrows(IllegalArgumentException::class.java) {
            FileNameSanitizer.sanitize("/")
        }
    }
}
