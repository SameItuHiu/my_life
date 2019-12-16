package com.example.mylife.dbhelper;

import android.provider.BaseColumns;

public class DBContractContact {

    public static String TABLE_NAME = "contact";
    public static final class NoteColumns implements BaseColumns {
        public static String id_friends = "id_friends";
        public static String id_contact = "id_contact";
        public static String phone = "phone";
        public static String email = "email";
        public static String line = "line";
        public static String whatsapp = "whatsapp";
    }
}
