package io.jeiel85.dockmode.data.calendar

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Calendar
import java.util.TimeZone

class CalendarRepositoryRangeTest {

    @Test
    fun `todayRangeMillis returns midnight bounds in the supplied zone`() {
        val timeZone = TimeZone.getTimeZone("Asia/Seoul")
        val noon = Calendar.getInstance(timeZone).apply {
            set(2026, Calendar.MAY, 22, 12, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val (start, end) = CalendarRepository.todayRangeMillis(noon, timeZone)

        val midnight = Calendar.getInstance(timeZone).apply {
            set(2026, Calendar.MAY, 22, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
        val nextMidnight = midnight + 24L * 60L * 60L * 1000L

        assertEquals(midnight, start)
        assertEquals(nextMidnight, end)
    }
}
