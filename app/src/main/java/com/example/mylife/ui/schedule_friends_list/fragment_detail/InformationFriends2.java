package com.example.mylife.ui.schedule_friends_list.fragment_detail;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mylife.R;
import com.example.mylife.dbhelper.DatabaseHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class InformationFriends2 extends Fragment {

    String FriendsID;

    public InformationFriends2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_information_friends2, container, false);

        if (getArguments() != null) {
            FriendsID = getArguments().getString("keyFriends");
        }

        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        final SQLiteDatabase dbRead = dbHelper.getReadableDatabase();

        //to get ID wallet
        final Cursor cursor = dbRead.rawQuery("select * from contact WHERE id_friends = '" +
                FriendsID + "'", null);
        cursor.moveToPosition(0);
        final String phone = cursor.getString(2);
        final String email = cursor.getString(3);
        final String line = cursor.getString(4);
        final String whatsapp = cursor.getString(5);

        TextView tvPhone = v.findViewById(R.id.phone);
        TextView tvEmail = v.findViewById(R.id.email);
        TextView tvLine = v.findViewById(R.id.line);
        TextView tvWhatsapp = v.findViewById(R.id.whatsapp);

        tvPhone.setText(phone);
        tvEmail.setText(email);
        tvLine.setText(line);
        tvWhatsapp.setText(whatsapp);

        return v;
    }

}
