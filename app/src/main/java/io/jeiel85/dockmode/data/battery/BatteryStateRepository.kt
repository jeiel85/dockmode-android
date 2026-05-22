package io.jeiel85.dockmode.data.battery

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import io.jeiel85.dockmode.domain.model.ChargingState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class BatteryStateRepository(private val context: Context) {
    fun observeChargingState(): Flow<ChargingState> = callbackFlow {
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(ctx: Context?, intent: Intent?) {
                trySend(intent.toChargingState())
            }
        }
        val sticky = context.registerReceiver(receiver, filter)
        trySend(sticky.toChargingState())
        awaitClose { runCatching { context.unregisterReceiver(receiver) } }
    }.distinctUntilChanged()

    fun currentChargingState(): ChargingState {
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val sticky = context.registerReceiver(null, filter)
        return sticky.toChargingState()
    }
}

internal fun Intent?.toChargingState(): ChargingState {
    if (this == null) return ChargingState.Unknown
    val status = getIntExtra(BatteryManager.EXTRA_STATUS, -1)
    return BatteryStatusMapper.map(status)
}

internal object BatteryStatusMapper {
    fun map(status: Int): ChargingState = when (status) {
        BatteryManager.BATTERY_STATUS_CHARGING -> ChargingState.Charging
        BatteryManager.BATTERY_STATUS_FULL -> ChargingState.Full
        BatteryManager.BATTERY_STATUS_DISCHARGING,
        BatteryManager.BATTERY_STATUS_NOT_CHARGING,
        -> ChargingState.Discharging
        else -> ChargingState.Unknown
    }
}
