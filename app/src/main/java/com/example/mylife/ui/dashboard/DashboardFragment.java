package com.example.mylife.ui.dashboard;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylife.R;
import com.example.mylife.adapter.RecyclerWeeklyAdapter;
import com.example.mylife.data.DataWallet;
import com.example.mylife.data.DataWeekly;
import com.example.mylife.dbhelper.DBContractWallet;
import com.example.mylife.dbhelper.DatabaseHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class DashboardFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        TextView currentDate = root.findViewById(R.id.currentDate);
        final Calendar c = Calendar.getInstance();
        int DAY_OF_WEEK = c.get(Calendar.DAY_OF_WEEK);
        int DAY_OF_MONTH = c.get(Calendar.DAY_OF_MONTH);
        int MONTH = c.get(Calendar.MONTH) + 1;
        int YEAR = c.get(Calendar.YEAR);

        String nameOfDay = null;

        if (DAY_OF_WEEK == 1){
            nameOfDay = "Sunday";
        }else if(DAY_OF_WEEK == 2){
            nameOfDay = "Monday";
        }else if(DAY_OF_WEEK == 3){
            nameOfDay = "Tuesday";
        }else if(DAY_OF_WEEK == 4){
            nameOfDay = "Wednesday";
        }else if(DAY_OF_WEEK == 5){
            nameOfDay = "Thusday";
        }else if(DAY_OF_WEEK == 6){
            nameOfDay = "Friday";
        }else if(DAY_OF_WEEK == 7){
            nameOfDay = "Saturday";
        }
        String Date = nameOfDay + ", " + DAY_OF_MONTH +" "+ MONTH +" "+ YEAR;
        currentDate.setText(Date);

        Random r = new Random();
        int numberID = r.nextInt(999);
        String ID = "MY"+numberID;

        final ArrayList<DataWeekly> WeeklyItems = new ArrayList<>();
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        final SQLiteDatabase dbWrite = dbHelper.getWritableDatabase();
        final SQLiteDatabase dbRead = dbHelper.getReadableDatabase();

        Cursor cursor = dbRead.rawQuery("SELECT * FROM wallet ",null);
        cursor.moveToFirst();
        if (cursor.getCount()==0)
        {
            ContentValues values = new ContentValues();
            values.put(DBContractWallet.NoteColumns.id_wallet, ID);
            values.put(DBContractWallet.NoteColumns.saldo, "0");
            dbWrite.insert(DBContractWallet.TABLE_NAME,null,values);
        }

        RecyclerView recyclerView = root.findViewById(R.id.rvScheduleToday);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final Cursor cursor1 = dbRead.rawQuery("select * from schedule_weekly WHERE day = '" +
                nameOfDay + "'", null);

        if (cursor1.moveToFirst()){
            do {
                DataWeekly dataItems = new DataWeekly();
                dataItems.setActivity(cursor1.getString(3));
                dataItems.setTime(cursor1.getString(2));
                dataItems.setDay(cursor1.getString(1));
                dataItems.setPlace(cursor1.getString(4));
                WeeklyItems.add(dataItems);
            } while (cursor1.moveToNext());
        }

        final RecyclerWeeklyAdapter adapter = new RecyclerWeeklyAdapter();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        adapter.setData(WeeklyItems);

        //to get ID wallet
        Cursor cursorID = dbRead.rawQuery("SELECT * FROM Wallet",null);
        cursorID.moveToPosition(0);
        int dataSaldo = cursorID.getInt(1);

        TextView saldoWallet = root.findViewById(R.id.saldoWallet);
        saldoWallet.setText(String.valueOf(dataSaldo));

        return root;
    }
}