package io.jeiel85.dockmode.util

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

object BurnInOffset {
    /**
     * Returns a small pixel offset that walks slowly around the origin on a circle so static
     * elements do not stay on the exact same pixels for long stretches.
     */
    fun calculate(
        nowMillis: Long,
        periodMillis: Long = 60_000L,
        radiusPx: Int = 12,
    ): Pair<Int, Int> {
        if (periodMillis <= 0L) return 0 to 0
        val safeRadius = radiusPx.coerceAtLeast(0)
        if (safeRadius == 0) return 0 to 0
        val phase = (nowMillis % periodMillis).toDouble() / periodMillis.toDouble()
        val angle = phase * 2.0 * PI
        val x = (cos(angle) * safeRadius).roundToInt()
        val y = (sin(angle) * safeRadius).roundToInt()
        return x to y
    }
}
