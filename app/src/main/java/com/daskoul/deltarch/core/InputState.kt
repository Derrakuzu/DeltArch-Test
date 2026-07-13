package com.daskoul.deltarch.core

import java.util.concurrent.atomic.AtomicIntegerArray

// Adjust this constant value if your local layout requires a different player count boundary
const val MAX_PLAYERS = 4

class InputState {
    /** Per-player bitmask of HELD buttons (merged in before every frame) */
    private val holdState = AtomicIntegerArray(MAX_PLAYERS)

    // --- RetroPad button constants (matching libretro.h) ---
    companion object {
        const val BTN_B = 0
    }
}
