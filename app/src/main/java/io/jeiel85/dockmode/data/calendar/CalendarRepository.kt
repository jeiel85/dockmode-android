package io.jeiel85.dockmode.data.calendar

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.provider.CalendarContract
import androidx.core.content.ContextCompat
import io.jeiel85.dockmode.domain.model.CalendarEventSummary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.TimeZone

class CalendarRepository(private val context: Context) {
    fun hasReadPermission(): Boolean =
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_CALENDAR,
        ) == PackageManager.PERMISSION_GRANTED

    suspend fun loadTodayEvents(nowMillis: Long = System.currentTimeMillis()): Result<List<CalendarEventSummary>> =
        withContext(Dispatchers.IO) {
            runCatching {
                if (!hasReadPermission()) {
                    return@runCatching emptyList()
                }
                val (start, end) = todayRangeMillis(nowMillis)
                queryEvents(start, end)
            }
        }

    private fun queryEvents(rangeStart: Long, rangeEnd: Long): List<CalendarEventSummary> {
        val projection = arrayOf(
            CalendarContract.Events._ID,
            CalendarContract.Events.TITLE,
            CalendarContract.Events.DTSTART,
            CalendarContract.Events.DTEND,
            CalendarContract.Events.ALL_DAY,
            CalendarContract.Events.DURATION,
        )
        val selection = "(${CalendarContract.Events.DTSTART} < ?) AND " +
            "((${CalendarContract.Events.DTEND} > ?) OR ${CalendarContract.Events.ALL_DAY} = 1) AND " +
            "${CalendarContract.Events.DELETED} = 0"
        val args = arrayOf(rangeEnd.toString(), rangeStart.toString())
        val sort = "${CalendarContract.Events.DTSTART} ASC"
        return context.contentResolver.query(
            CalendarContract.Events.CONTENT_URI,
            projection,
            selection,
            args,
            sort,
        )?.use(::mapCursor).orEmpty()
    }

    private fun mapCursor(cursor: Cursor): List<CalendarEventSummary> {
        val idIndex = cursor.getColumnIndexOrThrow(CalendarContract.Events._ID)
        val titleIndex = cursor.getColumnIndexOrThrow(CalendarContract.Events.TITLE)
        val startIndex = cursor.getColumnIndexOrThrow(CalendarContract.Events.DTSTART)
        val endIndex = cursor.getColumnIndexOrThrow(CalendarContract.Events.DTEND)
        val allDayIndex = cursor.getColumnIndexOrThrow(CalendarContract.Events.ALL_DAY)

        val results = mutableListOf<CalendarEventSummary>()
        while (cursor.moveToNext()) {
            val start = cursor.getLong(startIndex)
            val rawEnd = if (cursor.isNull(endIndex)) 0L else cursor.getLong(endIndex)
            val end = if (rawEnd > 0L) rawEnd else start
            results += CalendarEventSummary(
                id = cursor.getLong(idIndex),
                title = cursor.getString(titleIndex).orEmpty(),
                startsAtMillis = start,
                endsAtMillis = end,
                allDay = cursor.getInt(allDayIndex) == 1,
            )
        }
        return results
    }

    companion object {
        internal fun todayRangeMillis(
            nowMillis: Long,
            timeZone: TimeZone = TimeZone.getDefault(),
        ): Pair<Long, Long> {
            val calendar = Calendar.getInstance(timeZone)
            calendar.timeInMillis = nowMillis
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            val start = calendar.timeInMillis
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            val end = calendar.timeInMillis
            return start to end
        }
    }
}
