package io.jeiel85.dockmode.domain.model

enum class ClockStyle {
    Minimal {
        override fun getTitle(isKo: Boolean): String = if (isKo) "미니멀 시계" else "Minimal Clock"

        override fun getPreview(timeHm: String, timeHms: String): String = timeHm
    },
    Digital {
        override fun getTitle(isKo: Boolean): String = if (isKo) "디지털 시계" else "Digital Clock"

        override fun getPreview(timeHm: String, timeHms: String): String = timeHms
    },
    CalendarFocus {
        override fun getTitle(isKo: Boolean): String = if (isKo) "캘린더 포커스" else "Calendar Focus"

        override fun getPreview(timeHm: String, timeHms: String): String = "$timeHm · Schedule"
    },
    WarmBedside {
        override fun getTitle(isKo: Boolean): String = if (isKo) "따뜻한 침실" else "Warm Bedside"

        override fun getPreview(timeHm: String, timeHms: String): String = "$timeHm · Warm"
    },
    OledNight {
        override fun getTitle(isKo: Boolean): String = if (isKo) "OLED 야간 시계" else "OLED Night Clock"

        override fun getPreview(timeHm: String, timeHms: String): String = "$timeHm · Dim"
    },
    SplitDashboard {
        override fun getTitle(isKo: Boolean): String = if (isKo) "스플릿 대시보드" else "Split Dashboard"

        override fun getPreview(timeHm: String, timeHms: String): String = "$timeHm | Info"
    },
    BatteryDock {
        override fun getTitle(isKo: Boolean): String = if (isKo) "배터리 도크" else "Battery Dock"

        override fun getPreview(timeHm: String, timeHms: String): String = "$timeHm | 82%"
    },
    PhotoFrame {
        override fun getTitle(isKo: Boolean): String = if (isKo) "포토 프레임" else "Photo Frame"

        override fun getPreview(timeHm: String, timeHms: String): String = "$timeHm · Photo"
    }, ;

    abstract fun getTitle(isKo: Boolean): String

    abstract fun getPreview(timeHm: String, timeHms: String): String
}
