package com.example.mylife.ui.schedule_friends_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mylife.MainActivity;
import com.example.mylife.R;
import com.example.mylife.adapter.Adapter;
import com.example.mylife.adapter.tabAdapter;
import com.example.mylife.data.DataFriends;
import com.example.mylife.ui.schedule_friends_list.fragment_detail.InformationFriends1;
import com.example.mylife.ui.schedule_friends_list.fragment_detail.InformationFriends2;
import com.example.mylife.ui.schedule_friends_list.fragment_detail.InformationFriends3;
import com.example.mylife.ui.schedule_friends_list.fragment_detail.InformationFriends4;
import com.example.mylife.ui.wallet.fragment.InComingFragment;
import com.example.mylife.ui.wallet.fragment.OutGoingFragment;

public class FriendlistDetail extends AppCompatActivity {

    public static final String EXTRA_DATA = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendlist_detail);

        DataFriends friends = getIntent().getParcelableExtra(EXTRA_DATA);

        // find views by id
        ViewPager vPager = findViewById(R.id.pager);

        final Adapter adapter = new Adapter(getSupportFragmentManager());

        adapter.addFrag(new InformationFriends1(),friends.getId_friends());
        adapter.addFrag(new InformationFriends2(),friends.getId_friends());
        adapter.addFrag(new InformationFriends3(),friends.getId_friends());
        adapter.addFrag(new InformationFriends4(),friends.getId_friends());

        // set adapter on viewpager
        vPager.setAdapter(adapter);

    }

    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
