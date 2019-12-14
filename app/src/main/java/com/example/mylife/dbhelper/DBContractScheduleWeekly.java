package com.example.mylife.dbhelper;

import android.provider.BaseColumns;

public class DBContractScheduleWeekly {

    public static String TABLE_NAME = "schedule_weekly";
    public static final class NoteColumns implements BaseColumns {
        public static String id_weekly = "id_weekly";
        public static String day = "day";
        public static String activity = "activity";
        public static String place = "place";
        public static String time = "time";

    }
}
