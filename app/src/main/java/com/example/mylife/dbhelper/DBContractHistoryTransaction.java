package com.example.mylife.dbhelper;

import android.provider.BaseColumns;

public class DBContractHistoryTransaction {

    public static String TABLE_NAME = "history_transaksi";
    public static final class NoteColumns implements BaseColumns {
        public static String id_transaksi = "id_transaksi";
        public static String id_wallet = "id_wallet";
    }
}
