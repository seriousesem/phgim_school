package com.serioussem.phgim.school.utils

object URL {
    const val BASE_URL = "https://phgim.e-schools.info"
}

object Endpoints {
    const val LOGIN_ENDPOINT = "/login_"
    const val CLASS_SCHEDULE_ENDPOINT = "/pupil/{pupilId}/dnevnik"
}

object LocalStorageKeys {
    const val LOGIN_KEY = "login_key"
    const val PASSWORD_KEY = "password_key"
    const val PUPIL_ID = "pupil_id"
    const val QUARTER_ID = "quarter_id"
    const val WEEK_ID = "week_id"
}
object RoomConstants {
    const val DATABASE_NAME = "phgim_school_db"
    const val DATABASE_VERSION = 1
    const val CLASS_SCHEDULE_TABLE_NAME = "class_schedule_table"
}
object ActionOnWeek {
    const val NEXT_WEEK = "next_week"
    const val PREVIOUS_WEEK = "previous_week"
}
object MapKeys {
    const val NAV_CONTROLLER_MAP_KEY = "navController_map_key"
    const val DAY_INDEX_MAP_KEY = "day_index_map_key"
    const val LESSON_INDEX_MAP_KEY = "lesson_index_map_key"
}

object DateRangeNavigationIconButtonKey {
    const val NEXT_WEEK_KEY = "next_week_key"
    const val PREVIOUS_WEEK_KEY = "previous_week_key"
}
