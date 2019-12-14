package com.example.mylife.dbhelper;

import android.provider.BaseColumns;

public class DBContractTransactionWallet {

    public static String TABLE_NAME = "transaksi";
    public static final class NoteColumns implements BaseColumns {
        public static String id_wallet = "id_wallet";
        public static String date = "date";
        public static String activity = "activity";
        public static String money = "saldo";
        public static String type = "type";
        public static String id_transaksi = "id_transaksi";
    }
}
