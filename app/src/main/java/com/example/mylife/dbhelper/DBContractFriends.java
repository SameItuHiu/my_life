package com.example.mylife.dbhelper;

import android.provider.BaseColumns;

public class DBContractFriends {

    public static String TABLE_NAME = "friends";
    public static final class NoteColumns implements BaseColumns {
        public static String id_friends = "id_friends";
        public static String id_hutang = "id_hutang";
        public static String id_location = "id_location";
        public static String name = "name";
        public static String status = "status";
        public static String birthday = "birthday";
        public static String description = "description";
        public static String path_photo = "path_photo";
        public static String no_handphone = "no_handphone";
        public static String email = "email";
    }
}
