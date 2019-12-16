package com.example.mylife.ui.schedule_weekly;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransitionImpl;
import androidx.viewpager.widget.ViewPager;

import com.example.mylife.R;
import com.example.mylife.adapter.tabAdapter;
import com.example.mylife.dbhelper.DBContractScheduleWeekly;
import com.example.mylife.dbhelper.DBContractTransactionWallet;
import com.example.mylife.dbhelper.DatabaseHelper;
import com.example.mylife.ui.schedule_weekly.fragment.FridayFragment;
import com.example.mylife.ui.schedule_weekly.fragment.MondayFragment;
import com.example.mylife.ui.schedule_weekly.fragment.SaturdayFragment;
import com.example.mylife.ui.schedule_weekly.fragment.SundayFragment;
import com.example.mylife.ui.schedule_weekly.fragment.ThusdayFragment;
import com.example.mylife.ui.schedule_weekly.fragment.TuesdayFragment;
import com.example.mylife.ui.schedule_weekly.fragment.WednesdayFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;
import java.util.Random;

public class ScheduleWeeklyFragment extends Fragment {

    private int mHour, mMinute;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_schedule_weekly, container, false);
        // find views by id
        ViewPager vPager = v.findViewById(R.id.weeklyPager);
        TabLayout tLayout = v.findViewById(R.id.tabsWeekly);

        // attach tablayout with viewpager
        tLayout.setupWithViewPager(vPager);

        final tabAdapter adapter = new tabAdapter(getFragmentManager());

        // add your fragments
        adapter.addFrag(new SundayFragment(), "Sun");
        adapter.addFrag(new MondayFragment(), "Mon");
        adapter.addFrag(new TuesdayFragment(), "Tues");
        adapter.addFrag(new WednesdayFragment(), "Wed");
        adapter.addFrag(new ThusdayFragment(), "Thus");
        adapter.addFrag(new FridayFragment(), "Fri");
        adapter.addFrag(new SaturdayFragment(), "Sat");

        // set adapter on viewpager
        vPager.setAdapter(adapter);

        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        final SQLiteDatabase dbWrite = dbHelper.getWritableDatabase();
        final SQLiteDatabase dbRead = dbHelper.getReadableDatabase();

        FloatingActionButton fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View view1 = getLayoutInflater().inflate(R.layout.popup_add_weekly, null);
                final EditText Activity = view1.findViewById(R.id.weeklyActivity);
                final EditText Place = view1.findViewById(R.id.placeActivity);
                Button submit = view1.findViewById(R.id.submit);
                final TextView setTime = view1.findViewById(R.id.setTime);

                final Spinner Weekly = view1.findViewById(R.id.dateWeekly);
                final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                        R.array.Weekly,
                        android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Weekly.setAdapter(adapter);

                final BottomSheetDialog dialog = new BottomSheetDialog(getContext());
                dialog.setContentView(view1);
                dialog.show();

                setTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final Calendar c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR_OF_DAY);
                        mMinute = c.get(Calendar.MINUTE);
                        // Launch Time Picker Dialog
                        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                                new TimePickerDialog.OnTimeSetListener() {

                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute) {

                                        setTime.setText(hourOfDay + " : " + minute);

                                    }
                                }, mHour, mMinute, true);
                        timePickerDialog.show();
                    }
                });

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String setWeekly = String.valueOf(Weekly.getSelectedItem());

                        if (Activity.getText().toString().isEmpty()){
                            Activity.setError("");
                        }else if (Place.getText().toString().isEmpty()){
                            Place.setError("");
                        }else if (setTime.getText().toString().equals("00:00")){
                            setTime.setError("please set a time");
                        }else{
                            Random r = new Random();
                            int numberID = r.nextInt(1000);
                            String ID = "WKLY"+numberID;

                            Cursor cursor = dbRead.rawQuery("SELECT id_weekly FROM schedule_weekly WHERE id_weekly = '" +
                                    ID + "'",null);

                            cursor.moveToFirst();
                            while (cursor.getCount()>0)
                            {
                                numberID = r.nextInt(1000);
                                ID = "WKLY"+numberID;
                            }

                            ContentValues values = new ContentValues();
                            values.put(DBContractScheduleWeekly.NoteColumns.id_weekly, ID);
                            values.put(DBContractScheduleWeekly.NoteColumns.day, setWeekly);
                            values.put(DBContractScheduleWeekly.NoteColumns.time, setTime.getText().toString());
                            values.put(DBContractScheduleWeekly.NoteColumns.activity, Activity.getText().toString());
                            values.put(DBContractScheduleWeekly.NoteColumns.place, Place.getText().toString());
                            dbWrite.insert(DBContractScheduleWeekly.TABLE_NAME,null,values);

                            Toast.makeText(getContext(), setWeekly, Toast.LENGTH_SHORT).show();
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    }

                });
            }
        });



        return v;
    }
}