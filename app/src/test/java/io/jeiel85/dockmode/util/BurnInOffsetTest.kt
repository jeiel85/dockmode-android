package io.jeiel85.dockmode.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.math.sqrt

class BurnInOffsetTest {

    @Test
    fun `calculate returns zero offset when radius is zero`() {
        assertEquals(0 to 0, BurnInOffset.calculate(nowMillis = 1_000L, radiusPx = 0))
    }

    @Test
    fun `calculate stays within configured radius`() {
        val radius = 20
        val samples = (0L..120_000L step 1_000L).map { now ->
            BurnInOffset.calculate(nowMillis = now, radiusPx = radius)
        }
        samples.forEach { (x, y) ->
            val distance = sqrt((x * x + y * y).toDouble())
            assertTrue("offset $x,$y outside radius", distance <= radius + 1)
        }
    }
}
