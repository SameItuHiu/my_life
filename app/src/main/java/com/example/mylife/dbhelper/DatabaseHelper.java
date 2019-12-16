package com.example.mylife.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "dbmylife";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_WALLET = String.format("CREATE TABLE %s"
                    + " (%s TEXT PRIMARY KEY,"
                    + " %s INTEGER NOT NULL)",
            DBContractWallet.TABLE_NAME,
            DBContractWallet.NoteColumns.id_wallet,
            DBContractWallet.NoteColumns.saldo
    );

    private static final String SQL_CREATE_TABLE_INOUT = String.format("CREATE TABLE %s"
                    + " (%s TEXT PRIMARY KEY,"
                    + " %s TEXT NOT NULL,"
                    + " %s INTEGER NOT NULL,"
                    + " %s TEXT NOT NULL,"
                    + " %s TEXT NOT NULL,"
                    + " %s DATETIME NOT NULL)",
            DBContractTransactionWallet.TABLE_NAME,
            DBContractTransactionWallet.NoteColumns.id_transaksi,
            DBContractTransactionWallet.NoteColumns.activity,
            DBContractTransactionWallet.NoteColumns.money,
            DBContractTransactionWallet.NoteColumns.type,
            DBContractTransactionWallet.NoteColumns.id_wallet,
            DBContractTransactionWallet.NoteColumns.date
    );

    private static final String SQL_CREATE_TABLE_SCHEDULE_WEEKLY = String.format("CREATE TABLE %s"
                    + " (%s TEXT PRIMARY KEY,"
                    + " %s TEXT NOT NULL,"
                    + " %s DATETIME NOT NULL,"
                    + " %s TEXT NOT NULL,"
                    + " %s TEXT NOT NULL)",
            DBContractScheduleWeekly.TABLE_NAME,
            DBContractScheduleWeekly.NoteColumns.id_weekly,
            DBContractScheduleWeekly.NoteColumns.day,
            DBContractScheduleWeekly.NoteColumns.time,
            DBContractScheduleWeekly.NoteColumns.activity,
            DBContractScheduleWeekly.NoteColumns.place
    );

    private static final String SQL_CREATE_TABLE_FRIENDS = String.format("CREATE TABLE %s"
                    + " (%s TEXT PRIMARY KEY,"
                    + " %s TEXT NOT NULL,"
                    + " %s TEXT NOT NULL,"
                    + " %s TEXT,"
                    + " %s TEXT,"
                    + " %s TEXT,"
                    + " %s TEXT,"
                    + " %s TEXT,"
                    + " %s TEXT)",
            DBContractFriends.TABLE_NAME,
            DBContractFriends.NoteColumns.id_friends,
            DBContractFriends.NoteColumns.name,
            DBContractFriends.NoteColumns.status,
            DBContractFriends.NoteColumns.birthday,
            DBContractFriends.NoteColumns.description,
            DBContractFriends.NoteColumns.path_photo,
            DBContractFriends.NoteColumns.alamat,
            DBContractFriends.NoteColumns.Latitude,
            DBContractFriends.NoteColumns.Longitude
    );

    private static final String SQL_CREATE_TABLE_CONTACT = String.format("CREATE TABLE %s"
                    + " (%s TEXT PRIMARY KEY,"
                    + " %s TEXT,"
                    + " %s TEXT,"
                    + " %s TEXT,"
                    + " %s TEXT,"
                    + " %s TEXT)",
            DBContractContact.TABLE_NAME,
            DBContractContact.NoteColumns.id_contact,
            DBContractContact.NoteColumns.id_friends,
            DBContractContact.NoteColumns.phone,
            DBContractContact.NoteColumns.email,
            DBContractContact.NoteColumns.line,
            DBContractContact.NoteColumns.whatsapp
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_WALLET);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_INOUT);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_SCHEDULE_WEEKLY);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_FRIENDS);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_CONTACT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBContractWallet.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBContractTransactionWallet.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBContractScheduleWeekly.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBContractFriends.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBContractContact.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
