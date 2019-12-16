package com.example.mylife.ui.schedule_weekly.fragment;


import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mylife.R;
import com.example.mylife.adapter.RecyclerWeeklyAdapter;
import com.example.mylife.data.DataWeekly;
import com.example.mylife.dbhelper.DBContractScheduleWeekly;
import com.example.mylife.dbhelper.DatabaseHelper;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SaturdayFragment extends Fragment {


    public SaturdayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_saturday, container, false);

        final ArrayList<DataWeekly> WeeklyItems = new ArrayList<>();
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        SQLiteDatabase ReadData = dbHelper.getReadableDatabase();
        final SQLiteDatabase dbWrite = dbHelper.getWritableDatabase();

        RecyclerView recyclerView = v.findViewById(R.id.rvWeekly);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final Cursor cursor = ReadData.rawQuery("select * from schedule_weekly WHERE day = '" +
                "Saturday" + "'", null);

        if (cursor.moveToFirst()){
            do {
                DataWeekly dataItems = new DataWeekly();
                dataItems.setActivity(cursor.getString(3));
                dataItems.setTime(cursor.getString(2));
                dataItems.setDay(cursor.getString(1));
                dataItems.setPlace(cursor.getString(4));
                dataItems.setId(cursor.getString(0));
                WeeklyItems.add(dataItems);
            } while (cursor.moveToNext());
        }

        final RecyclerWeeklyAdapter adapter = new RecyclerWeeklyAdapter();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        adapter.setData(WeeklyItems);

        adapter.setOnItemClickCallback(new RecyclerWeeklyAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(final DataWeekly data) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int choice) {
                        switch (choice) {
                            case DialogInterface.BUTTON_POSITIVE:
                                dbWrite.delete(DBContractScheduleWeekly.TABLE_NAME, "id_weekly = ?",
                                        new String[] {String.valueOf(data.getId())});
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Delete this schedule?")
                        .setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

            }
        });

        return v;
    }

}
