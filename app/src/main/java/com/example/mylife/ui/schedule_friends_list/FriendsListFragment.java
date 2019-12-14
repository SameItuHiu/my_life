package com.example.mylife.ui.schedule_friends_list;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylife.R;
import com.example.mylife.adapter.RecyclerFriendsAdapter;
import com.example.mylife.data.DataFriends;
import com.example.mylife.dbhelper.DBContractFriends;
import com.example.mylife.dbhelper.DatabaseHelper;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

public class FriendsListFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_schedule_friends, container, false);

        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        final SQLiteDatabase dbWrite = dbHelper.getWritableDatabase();
        final SQLiteDatabase dbRead = dbHelper.getReadableDatabase();

        FloatingActionButton fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View view1 = getLayoutInflater().inflate(R.layout.popup_add_friends, null);

                final TextView name = view1.findViewById(R.id.name);
                final TextView status = view1.findViewById(R.id.status);
                final TextView desc = view1.findViewById(R.id.desc);


                final BottomSheetDialog dialog = new BottomSheetDialog(getContext());
                dialog.setContentView(view1);
                dialog.show();

                Button submit = view1.findViewById(R.id.submit);
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (name.getText().toString().isEmpty()){
                            name.setError("Please write your friend name");
                        }else if(status.getText().toString().isEmpty()){
                            status.setError("Please write who is he/she?");
                        }else if(desc.getText().toString().isEmpty()){
                            desc.setError("Please write describ of your friends");
                        }else{
                            Random r = new Random();
                            int numberID = r.nextInt(1000);
                            String ID = "FRN"+numberID;

                            Cursor cursor = dbRead.rawQuery("SELECT id_friends FROM friends WHERE id_friends = '" +
                                    ID + "'",null);

                            cursor.moveToFirst();
                            while (cursor.getCount()>0)
                            {
                                numberID = r.nextInt(1000);
                                ID = "FRN"+numberID;
                            }

                            ContentValues values = new ContentValues();
                            values.put(DBContractFriends.NoteColumns.name, name.getText().toString());
                            values.put(DBContractFriends.NoteColumns.status, status.getText().toString());
                            values.put(DBContractFriends.NoteColumns.id_friends, ID);
                            dbWrite.insert(DBContractFriends.TABLE_NAME,null,values);

                            Toast.makeText(getContext(), "Congrats you already have a friend", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
        final ArrayList<DataFriends> FriendsItems = new ArrayList<>();

        RecyclerView recyclerView = v.findViewById(R.id.rvListFriends);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final Cursor cursor = dbRead.rawQuery("select * from friends where status != '' ", null);

        if (cursor.moveToFirst()){
            do {
                DataFriends dataItems = new DataFriends();
                dataItems.setId_friends(cursor.getString(0));
                dataItems.setName(cursor.getString(3));
                dataItems.setStatus(cursor.getString(4));
                FriendsItems.add(dataItems);
            } while (cursor.moveToNext());
        }

        final RecyclerFriendsAdapter adapter = new RecyclerFriendsAdapter();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        adapter.setData(FriendsItems);

        adapter.setOnItemClickCallback(new RecyclerFriendsAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(DataFriends data) {
                Intent intent = new Intent(getContext(), FriendlistDetail.class);
                intent.putExtra(FriendlistDetail.EXTRA_DATA, data);
                startActivity(intent);
            }
        });
        return v;
    }
}