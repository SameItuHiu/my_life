package com.example.mylife.ui.schedule_friends_list.fragment_detail;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mylife.R;
import com.example.mylife.dbhelper.DatabaseHelper;
import com.example.mylife.ui.schedule_friends_list.FriendlistDetail;

/**
 * A simple {@link Fragment} subclass.
 */
public class InformationFriends1 extends Fragment {

    String FriendsID;

    public InformationFriends1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_information_friends1, container, false);

        if (getArguments() != null) {
            FriendsID = getArguments().getString("keyFriends");
        }

        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        final SQLiteDatabase dbRead = dbHelper.getReadableDatabase();

        //to get ID wallet
        final Cursor cursor = dbRead.rawQuery("select * from friends WHERE id_friends = '" +
                FriendsID + "'", null);
        cursor.moveToPosition(0);
        String nama = cursor.getString(1);
        String status = cursor.getString(2);
        String birthday = cursor.getString(3);
        String description = cursor.getString(4);

        TextView tvname = v.findViewById(R.id.name);
        TextView tvstatus = v.findViewById(R.id.status);
        TextView tvbirthday = v.findViewById(R.id.birthday);
        TextView tvdescription = v.findViewById(R.id.description);

        tvname.setText(nama);
        tvstatus.setText(status);
        if (birthday == null){
            tvbirthday.setText("-");
        }else{
            tvbirthday.setText(birthday);
        }
        if (description == null){
            tvdescription.setText("No Description");
        }else{
            tvdescription.setText(description);
        }


        return v;
    }
}
