package com.example.mylife.ui.schedule_friends_list;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mylife.MainActivity;
import com.example.mylife.R;
import com.example.mylife.adapter.Adapter;
import com.example.mylife.data.DataFriends;
import com.example.mylife.dbhelper.DBContractContact;
import com.example.mylife.dbhelper.DBContractFriends;
import com.example.mylife.dbhelper.DBContractTransactionWallet;
import com.example.mylife.dbhelper.DBContractWallet;
import com.example.mylife.dbhelper.DatabaseHelper;
import com.example.mylife.map;
import com.example.mylife.ui.schedule_friends_list.fragment_detail.InformationFriends1;
import com.example.mylife.ui.schedule_friends_list.fragment_detail.InformationFriends2;
import com.example.mylife.ui.schedule_friends_list.fragment_detail.InformationFriends3;
import com.example.mylife.ui.schedule_friends_list.fragment_detail.InformationFriends4;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Random;

public class FriendlistDetail extends AppCompatActivity {

    public static final String EXTRA_DATA = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendlist_detail);

        final DataFriends friends = getIntent().getParcelableExtra(EXTRA_DATA);

        // find views by id
        final ViewPager vPager = findViewById(R.id.pager);

        final Adapter adapter = new Adapter(getSupportFragmentManager());

        adapter.addFrag(new InformationFriends1(),friends.getId_friends());
        adapter.addFrag(new InformationFriends2(),friends.getId_friends());
        adapter.addFrag(new InformationFriends3(),friends.getId_friends());
        //adapter.addFrag(new InformationFriends4(),friends.getId_friends());

        // set adapter on viewpager
        vPager.setAdapter(adapter);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        final SQLiteDatabase dbWrite = dbHelper.getWritableDatabase();
        final SQLiteDatabase dbRead = dbHelper.getReadableDatabase();

        //to get ID wallet
        final Cursor cursor = dbRead.rawQuery("select * from friends WHERE id_friends = '" +
                friends.getId_friends() + "'", null);
        cursor.moveToPosition(0);
        final String nama = cursor.getString(1);
        final String status = cursor.getString(2);
        final String birthday = cursor.getString(3);
        final String description = cursor.getString(4);
        if (cursor.getString(5) != null){
            String path = cursor.getString(5);
            ImageView imagee = findViewById(R.id.imgPoster);
            Glide.with(this).load(String.valueOf(path)).into(imagee);
        }

        final BottomSheetDialog dialog = new BottomSheetDialog(FriendlistDetail.this);

        ImageView editFriends = findViewById(R.id.editFriends);
        editFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = vPager.getCurrentItem();
                if (position == 0){
                    final View view1 = getLayoutInflater().inflate(R.layout.popup_edit_friends_personal, null);
                    final EditText Edtname = view1.findViewById(R.id.name);
                    final EditText Edtstatus = view1.findViewById(R.id.status);
                    TextView tvsetDate = view1.findViewById(R.id.setDate);
                    final EditText Edtdesc = view1.findViewById(R.id.desc);
                    final TextView setDate = view1.findViewById(R.id.setDate);

                    Edtname.setText(nama);
                    Edtstatus.setText(status);
                    if (birthday != null){
                        tvsetDate.setText(birthday);
                    }

                    Edtdesc.setText(description);

                    setDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final Calendar c = Calendar.getInstance();
                            int day = c.get(Calendar.DAY_OF_MONTH);
                            int month = c.get(Calendar.MONTH);
                            int year = c.get(Calendar.YEAR);

                            DatePickerDialog datePickerDialog = new DatePickerDialog(FriendlistDetail.this,
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

                    dialog.setContentView(view1);
                    dialog.show();

                    Button submittt = view1.findViewById(R.id.submittt);
                    submittt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ContentValues values = new ContentValues();
                            values.put(DBContractFriends.NoteColumns.name, Edtname.getText().toString());
                            values.put(DBContractFriends.NoteColumns.status, Edtstatus.getText().toString());
                            values.put(DBContractFriends.NoteColumns.birthday, setDate.getText().toString());
                            values.put(DBContractFriends.NoteColumns.description, Edtdesc.getText().toString());
                            dbWrite.update(DBContractFriends.TABLE_NAME, values,  " id_friends = ?", new String[] {friends.getId_friends()});

                            Toast.makeText(FriendlistDetail.this, "Updated", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });

                }else if (position == 1){
                    final View view1 = getLayoutInflater().inflate(R.layout.popup_edit_friends_contact, null);

                    Random r = new Random();
                    int numberID = r.nextInt(999);
                    String ID1 = "TFW"+numberID;

                    Cursor cursor = dbRead.rawQuery("SELECT id_contact FROM contact WHERE id_contact = '" +
                            ID1 + "'",null);

                    cursor.moveToFirst();

                    while (cursor.getCount()>0)
                    {
                        numberID = r.nextInt(999);
                        ID1 = "TFW"+numberID;
                    }

                    final EditText edtPhone = view1.findViewById(R.id.no_handphone);
                    final EditText edtMail = view1.findViewById(R.id.email);
                    final EditText edtLine = view1.findViewById(R.id.lineID);
                    final EditText edtWA = view1.findViewById(R.id.whatsapp);
                    Button submit = view1.findViewById(R.id.submit);
                    final String finalID = ID1;

                    final Cursor cursor1 = dbRead.rawQuery("select * from contact WHERE id_friends = '" +
                            friends.getId_friends() + "'", null);
                    cursor1.moveToPosition(0);
                    final String phone = cursor1.getString(2);
                    final String email = cursor1.getString(3);
                    final String line = cursor1.getString(4);
                    final String whatsapp = cursor1.getString(5);

                    edtPhone.setText(phone);
                    edtMail.setText(email);
                    edtLine.setText(line);
                    edtWA.setText(whatsapp);

                    dialog.setContentView(view1);
                    dialog.show();

                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ContentValues values = new ContentValues();
                            values.put(DBContractContact.NoteColumns.id_contact, finalID);
                            values.put(DBContractContact.NoteColumns.phone, edtPhone.getText().toString());
                            values.put(DBContractContact.NoteColumns.email, edtMail.getText().toString());
                            values.put(DBContractContact.NoteColumns.line, edtLine.getText().toString());
                            values.put(DBContractContact.NoteColumns.whatsapp, edtWA.getText().toString());
                            dbWrite.update(DBContractContact.TABLE_NAME, values,  " id_friends = ?", new String[] {friends.getId_friends()});
                            Toast.makeText(FriendlistDetail.this, "Update", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });


                }else if (position == 2){
                    final View view1 = getLayoutInflater().inflate(R.layout.popup_edit_friends_address, null);

                    final EditText edtAddress = view1.findViewById(R.id.address);
                    TextView setAddress = view1.findViewById(R.id.setAddress);
                    Button submit = view1.findViewById(R.id.submit);

                    final Cursor cursor1 = dbRead.rawQuery("select * from friends WHERE id_friends = '" +
                            friends.getId_friends() + "'", null);
                    cursor1.moveToPosition(0);
                    final String address = cursor1.getString(6);
                    edtAddress.setText(address);
                    setAddress.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(FriendlistDetail.this, map.class);
                            i.putExtra("EXTRA_SESSION_ID", friends.getId_friends());
                            startActivity(i);
                        }
                    });

                    dialog.setContentView(view1);
                    dialog.show();

                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ContentValues values = new ContentValues();

                            values.put(DBContractFriends.NoteColumns.alamat, edtAddress.getText().toString());

                            dbWrite.update(DBContractFriends.TABLE_NAME, values,  " id_friends = ?", new String[] {friends.getId_friends()});
                            Toast.makeText(FriendlistDetail.this, "Update", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });

                }
//                else if (position == 3){
//                    final View view1 = getLayoutInflater().inflate(R.layout.popup_edit_friends_hutang, null);
//                    dialog.setContentView(view1);
//                    dialog.show();
//                }
            }
        });

        ImageView deleteFrinds = findViewById(R.id.deleteFriends);
        deleteFrinds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deletedRows = dbWrite.delete(DBContractFriends.TABLE_NAME, "id_friends = ?",
                        new String[] {friends.getId_friends()});
                dbWrite.delete(DBContractContact.TABLE_NAME, "id_friends = ?",
                        new String[] {friends.getId_friends()});

                adapter.notifyDataSetChanged();

                if (deletedRows > 0){
                    Toast.makeText(FriendlistDetail.this, "Dia sudah bukan teman kamu lagi", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });

        ImageView imgPoster = findViewById(R.id.imgPoster);
        imgPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final DataFriends friends = getIntent().getParcelableExtra(EXTRA_DATA);
        ImageView imgPoster = findViewById(R.id.imgPoster);

        if (resultCode == RESULT_OK) {
            final Uri imageUri = data.getData();

            Glide.with(this).load(imageUri).into(imgPoster);

            DatabaseHelper dbHelper = new DatabaseHelper(this);
            final SQLiteDatabase dbWrite = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(DBContractFriends.NoteColumns.path_photo, String.valueOf(imageUri));
            dbWrite.update(DBContractFriends.TABLE_NAME, values,  " id_friends = ?", new String[] {friends.getId_friends()});

        }

    }

    public void back(View view) {
        finish();
    }
}
