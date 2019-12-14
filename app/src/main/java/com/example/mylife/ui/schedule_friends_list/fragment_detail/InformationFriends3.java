package com.example.mylife.ui.schedule_friends_list.fragment_detail;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mylife.R;

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

        return v;
    }

}
