package io.jeiel85.dockmode.data.battery

import android.os.BatteryManager
import io.jeiel85.dockmode.domain.model.ChargingState
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class BatteryChargingMapperTest(
    private val status: Int,
    private val expected: ChargingState,
) {

    @Test
    fun `status code maps to charging state`() {
        assertEquals(expected, BatteryStatusMapper.map(status))
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0} -> {1}")
        fun cases(): List<Array<Any>> =
            listOf(
                arrayOf(BatteryManager.BATTERY_STATUS_CHARGING, ChargingState.Charging),
                arrayOf(BatteryManager.BATTERY_STATUS_FULL, ChargingState.Full),
                arrayOf(BatteryManager.BATTERY_STATUS_DISCHARGING, ChargingState.Discharging),
                arrayOf(BatteryManager.BATTERY_STATUS_NOT_CHARGING, ChargingState.Discharging),
                arrayOf(BatteryManager.BATTERY_STATUS_UNKNOWN, ChargingState.Unknown),
                arrayOf(-1, ChargingState.Unknown),
            )
    }
}
