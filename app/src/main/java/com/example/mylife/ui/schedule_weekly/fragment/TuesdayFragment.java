package com.example.mylife.ui.schedule_weekly.fragment;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mylife.R;
import com.example.mylife.adapter.RecyclerWeeklyAdapter;
import com.example.mylife.data.DataWeekly;
import com.example.mylife.dbhelper.DatabaseHelper;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TuesdayFragment extends Fragment {


    public TuesdayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tuesday, container, false);

        final ArrayList<DataWeekly> WeeklyItems = new ArrayList<>();
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        SQLiteDatabase ReadData = dbHelper.getReadableDatabase();

        RecyclerView recyclerView = v.findViewById(R.id.rvWeekly);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final Cursor cursor = ReadData.rawQuery("select * from schedule_weekly WHERE day = '" +
                "Tuesday" + "'", null);

        if (cursor.moveToFirst()){
            do {
                DataWeekly dataItems = new DataWeekly();
                dataItems.setActivity(cursor.getString(3));
                dataItems.setTime(cursor.getString(2));
                dataItems.setDay(cursor.getString(1));
                dataItems.setPlace(cursor.getString(4));
                WeeklyItems.add(dataItems);
            } while (cursor.moveToNext());
        }

        final RecyclerWeeklyAdapter adapter = new RecyclerWeeklyAdapter();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        adapter.setData(WeeklyItems);

        return v;
    }

}
