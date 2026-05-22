package io.jeiel85.dockmode.util

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object TickerFlow {
    fun seconds(periodMillis: Long = 1_000L): Flow<Long> = flow {
        while (true) {
            emit(System.currentTimeMillis())
            delay(periodMillis)
        }
    }
}
