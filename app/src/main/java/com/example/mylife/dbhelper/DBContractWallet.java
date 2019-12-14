package com.example.mylife.dbhelper;

import android.net.Uri;
import android.provider.BaseColumns;

public class DBContractWallet {

    public static String TABLE_NAME = "Wallet";
    public static final class NoteColumns implements BaseColumns {
        public static String id_wallet = "id_wallet";
        public static String saldo = "saldo";

    }
}
