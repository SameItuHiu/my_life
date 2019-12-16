package com.example.mylife.ui.schedule_friends_list.fragment_detail;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mylife.R;
import com.example.mylife.dbhelper.DatabaseHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class InformationFriends3 extends Fragment {

    String FriendsID;

    public InformationFriends3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_information_friends3, container, false);

        if (getArguments() != null) {
            FriendsID = getArguments().getString("keyFriends");
        }

        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        final SQLiteDatabase dbRead = dbHelper.getReadableDatabase();

        //to get ID wallet
        final Cursor cursor = dbRead.rawQuery("select * from friends WHERE id_friends = '" +
                FriendsID + "'", null);
        cursor.moveToPosition(0);
        String address = cursor.getString(6);
        final String lat = cursor.getString(7);
        final String longit = cursor.getString(8);

        TextView tvAddress = v.findViewById(R.id.address);
        tvAddress.setText(address);

        Button direct = v.findViewById(R.id.direct);
        if (cursor.getString(7) == null){
            direct.setVisibility(View.GONE);
        }

        direct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Double mLat = Double.valueOf(lat);
                Double mLong = Double.valueOf(longit);
                Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?daddr="+mLat+","+mLong);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
        });

        return v;
    }

}
