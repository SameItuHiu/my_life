package com.example.mylife.ui.wallet.fragment;


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
import com.example.mylife.adapter.RecyclerWalletAdapter;
import com.example.mylife.data.DataWallet;
import com.example.mylife.dbhelper.DatabaseHelper;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class InComingFragment extends Fragment {


    public InComingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_in_comming, container, false);

        final ArrayList<DataWallet> walletItems = new ArrayList<>();
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        SQLiteDatabase ReadData = dbHelper.getReadableDatabase();

        RecyclerView recyclerView = v.findViewById(R.id.rvInComing);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final Cursor cursor = ReadData.rawQuery("select * from transaksi WHERE type = '" +
                "incoming" + "'", null);

        if (cursor.moveToFirst()){
            do {
                DataWallet dataItems = new DataWallet();
                dataItems.setActivity(cursor.getString(1));
                dataItems.setMoney(cursor.getString(2));
                dataItems.setType(cursor.getString(3));
                dataItems.setId_wallet(cursor.getString(4));
                walletItems.add(dataItems);
            } while (cursor.moveToNext());
        }

        final RecyclerWalletAdapter adapter = new RecyclerWalletAdapter();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        adapter.setData(walletItems);

        return v;
    }

}
