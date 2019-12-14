package com.example.mylife.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.mylife.ui.schedule_friends_list.fragment_detail.InformationFriends1;
import com.example.mylife.ui.schedule_friends_list.fragment_detail.InformationFriends2;
import com.example.mylife.ui.schedule_friends_list.fragment_detail.InformationFriends3;
import com.example.mylife.ui.schedule_friends_list.fragment_detail.InformationFriends4;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends FragmentStatePagerAdapter {

    String FriendsID;
    private final List<Fragment> mFragmentList = new ArrayList<>();

    public Adapter(FragmentManager manager) {
        super(manager);
    }


    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        switch (position){
            case 0:

                InformationFriends1 information1 = new InformationFriends1();
                bundle.putString("keyFriends", FriendsID);
                information1.setArguments(bundle);
                return information1;

            case 1:

                InformationFriends2 information2 = new InformationFriends2();
                bundle.putString("keyFriends", FriendsID);
                information2.setArguments(bundle);
                return information2;

            case 2:

                InformationFriends3 information3 = new InformationFriends3();
                bundle.putString("keyFriends", FriendsID);
                information3.setArguments(bundle);
                return information3;

            case 3:

                InformationFriends4 information4 = new InformationFriends4();
                bundle.putString("keyFriends", FriendsID);
                information4.setArguments(bundle);
                return information4;

        }
        return null;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String FriendsID) {
        mFragmentList.add(fragment);
        this.FriendsID = FriendsID;
    }
}