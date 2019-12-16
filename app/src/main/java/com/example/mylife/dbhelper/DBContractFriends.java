package com.example.mylife.dbhelper;

import android.provider.BaseColumns;

public class DBContractFriends {

    public static String TABLE_NAME = "friends";
    public static final class NoteColumns implements BaseColumns {
        public static String id_friends = "id_friends";
        public static String name = "name";
        public static String status = "status";
        public static String birthday = "birthday";
        public static String description = "description";
        public static String path_photo = "path_photo";
        public static String alamat = "alamat";
        public static String Latitude = "Latitude";
        public static String Longitude = "Longitude";
    }
}
