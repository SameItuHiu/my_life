package com.example.mylife.ui.wallet;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.mylife.MainActivity;
import com.example.mylife.dbhelper.DBContractHistoryTransaction;
import com.example.mylife.dbhelper.DBContractWallet;
import com.example.mylife.ui.wallet.fragment.InComingFragment;
import com.example.mylife.ui.wallet.fragment.OutGoingFragment;
import com.example.mylife.R;
import com.example.mylife.adapter.tabAdapter;
import com.example.mylife.dbhelper.DBContractTransactionWallet;
import com.example.mylife.dbhelper.DatabaseHelper;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;
import java.util.Random;


public class WalletFragment extends Fragment{


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_wallet, container, false);

        ImageView addWallet = v.findViewById(R.id.addwallet);
        final ImageView minusWallet = v.findViewById(R.id.minuswallet);

        TextView moneyWallet = v.findViewById(R.id.moneyWallet);

        // find views by id
        ViewPager vPager = v.findViewById(R.id.pagerWallet);
        TabLayout tLayout = v.findViewById(R.id.tabWallet);

        // attach tablayout with viewpager
        tLayout.setupWithViewPager(vPager);

        final tabAdapter adapter = new tabAdapter(getFragmentManager());

        // add your fragments
        adapter.addFrag(new InComingFragment(), "Incoming");
        adapter.addFrag(new OutGoingFragment(), "Outgoing");

        // set adapter on viewpager
        vPager.setAdapter(adapter);

        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        final SQLiteDatabase dbWrite = dbHelper.getWritableDatabase();
        final SQLiteDatabase dbRead = dbHelper.getReadableDatabase();

        final View view1 = getLayoutInflater().inflate(R.layout.popup_inout_wallet, null);

        final EditText inCom = view1.findViewById(R.id.editTextWallet);
        final EditText inComActv = view1.findViewById(R.id.editTextActivity);

        final TextView tvInOut = view1.findViewById(R.id.tvInOut);
        final TextView tvInOutMoney = view1.findViewById(R.id.tvInOutMoney);
        final TextView setDate = view1.findViewById(R.id.setDate);

        final Button submit = view1.findViewById(R.id.submit);
        final BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        dialog.setContentView(view1);

        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                month = month+1;
                                String Date = day +" / "+ month +" / "+ year;
                                setDate.setText(Date);
                            }
                        },year,month,day);
                datePickerDialog.show();


            }
        });

        //to get ID wallet
        Cursor cursorID = dbRead.rawQuery("SELECT * FROM Wallet",null);
        cursorID.moveToPosition(0);
        final String dataID = cursorID.getString(0);
        final int dataSaldo = cursorID.getInt(1);

        moneyWallet.setText(String.valueOf(dataSaldo));

        addWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tvInOut.setText("Incoming Wallet");
                tvInOutMoney.setText("Incoming Money");
                dialog.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (inComActv.getText().toString().isEmpty()){
                            inComActv.setError("this field can't be blank");
                        }else if (inCom.getText().toString().isEmpty()){
                            inCom.setError("this field can't be blank");
                        }else if (setDate.getText().toString().equals("Tap to set date")){
                            setDate.setError("please set date");
                        }else{
                            Random r = new Random();
                            int numberID = r.nextInt(999);
                            String ID = "TFW"+numberID;

                            final int saldo = Integer.parseInt(inCom.getText().toString());

                            final int pluSaldo = dataSaldo + saldo;

                            Cursor cursor = dbRead.rawQuery("SELECT id_transaksi FROM transaksi WHERE id_transaksi = '" +
                                    ID + "'",null);

                            cursor.moveToFirst();

                            while (cursor.getCount()>0)
                            {
                                numberID = r.nextInt(999);
                                ID = "TFW"+numberID;
                            }
                            ContentValues values = new ContentValues();
                            values.put(DBContractTransactionWallet.NoteColumns.id_transaksi, ID);
                            values.put(DBContractTransactionWallet.NoteColumns.activity, inComActv.getText().toString());
                            values.put(DBContractTransactionWallet.NoteColumns.money, saldo);
                            values.put(DBContractTransactionWallet.NoteColumns.type, "incoming");
                            values.put(DBContractTransactionWallet.NoteColumns.id_wallet, dataID);
                            values.put(DBContractTransactionWallet.NoteColumns.date, setDate.getText().toString());
                            dbWrite.insert(DBContractTransactionWallet.TABLE_NAME,null,values);

                            //update saldo form table wallet
                            ContentValues valuess = new ContentValues();
                            valuess.put(DBContractWallet.NoteColumns.saldo, pluSaldo);
                            // updating row
                            dbWrite.update(DBContractWallet.TABLE_NAME, valuess,  " id_wallet = ?", new String[] {dataID});

                            ContentValues valuesss = new ContentValues();
                            valuesss.put(DBContractHistoryTransaction.NoteColumns.id_transaksi, ID);
                            valuesss.put(DBContractHistoryTransaction.NoteColumns.id_wallet, dataID);
                            dbWrite.insert(DBContractHistoryTransaction.TABLE_NAME,null,valuesss);

                            Toast.makeText(getContext(), "sukses", Toast.LENGTH_SHORT).show();
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    }
                });


            }
        });

        minusWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tvInOut.setText("Outgoing Wallet");
                tvInOutMoney.setText("Outgoing Money");
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (inComActv.getText().toString().isEmpty()){
                            inComActv.setError("this field can't be blank");
                        }else if (inCom.getText().toString().isEmpty()){
                            inCom.setError("this field can't be blank");
                        }else if (setDate.getText().toString().equals("Tap to set date")){
                            setDate.setError("please set date");
                        }else {
                            Random r = new Random();
                            int numberID = r.nextInt(999);
                            String ID = "TFW"+numberID;

                            int saldo = Integer.parseInt(inCom.getText().toString());
                            final int MinSaldo = dataSaldo - saldo;

                            Cursor cursor = dbRead.rawQuery("SELECT id_transaksi FROM transaksi WHERE id_transaksi = '" +
                                    ID + "'",null);

                            cursor.moveToFirst();

                            while (cursor.getCount()>0)
                            {
                                numberID = r.nextInt(999);
                                ID = "TFW"+numberID;
                            }

                            ContentValues values = new ContentValues();
                            values.put(DBContractTransactionWallet.NoteColumns.id_transaksi, ID);
                            values.put(DBContractTransactionWallet.NoteColumns.activity, inComActv.getText().toString());
                            values.put(DBContractTransactionWallet.NoteColumns.money, saldo);
                            values.put(DBContractTransactionWallet.NoteColumns.type, "outgoing");
                            values.put(DBContractTransactionWallet.NoteColumns.id_wallet, dataID);
                            values.put(DBContractTransactionWallet.NoteColumns.date, setDate.getText().toString());
                            dbWrite.insert(DBContractTransactionWallet.TABLE_NAME,null,values);

                            //update saldo form table wallet
                            ContentValues valuess = new ContentValues();
                            valuess.put(DBContractWallet.NoteColumns.saldo, MinSaldo);
                            // updating row
                            dbWrite.update(DBContractWallet.TABLE_NAME, valuess,  " id_wallet = ?", new String[] {dataID});

                            ContentValues valuesss = new ContentValues();
                            valuesss.put(DBContractHistoryTransaction.NoteColumns.id_transaksi, ID);
                            valuesss.put(DBContractHistoryTransaction.NoteColumns.id_wallet, dataID);
                            dbWrite.insert(DBContractHistoryTransaction.TABLE_NAME,null,valuesss);

                            Toast.makeText(getContext(), "Sukses", Toast.LENGTH_SHORT).show();
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