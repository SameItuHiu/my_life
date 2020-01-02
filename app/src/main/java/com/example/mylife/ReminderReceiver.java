package com.example.mylife;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.mylife.data.DataWeekly;
import com.example.mylife.dbhelper.DatabaseHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class ReminderReceiver extends BroadcastReceiver {

    public static final String DAILY_REMINDER_RELEASE = "Daily Reminder Release";
    public static final String DAILY_REMINDER = "Daily Reminder";
    public static final String EXTRA_TYPE = "type";

    private final int ID_DAILY = 100;
    private final int ID_RELEASE = 101;

    String DATE_FORMAT = "yyyy-MM-dd";

    @Override
    public void onReceive(final Context context, Intent intent) {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String currentDate = dateFormat.format(date);
        Log.d("Today : ", currentDate);

        final Calendar c = Calendar.getInstance();
        int DAY_OF_WEEK = c.get(Calendar.DAY_OF_WEEK);

        String nameOfDay = null;

        if (DAY_OF_WEEK == 1){
            nameOfDay = "Sunday";
        }else if(DAY_OF_WEEK == 2){
            nameOfDay = "Monday";
        }else if(DAY_OF_WEEK == 3){
            nameOfDay = "Tuesday";
        }else if(DAY_OF_WEEK == 4){
            nameOfDay = "Wednesday";
        }else if(DAY_OF_WEEK == 5){
            nameOfDay = "Thusday";
        }else if(DAY_OF_WEEK == 6){
            nameOfDay = "Friday";
        }else if(DAY_OF_WEEK == 7){
            nameOfDay = "Saturday";
        }

        DatabaseHelper dbHelper = new DatabaseHelper(context);
        final SQLiteDatabase dbRead = dbHelper.getReadableDatabase();

        final Cursor cursor1 = dbRead.rawQuery("select * from schedule_weekly WHERE day = '" +
                nameOfDay + "'", null);
        int id = 2;
        if (cursor1.moveToFirst()){
            do {
                DataWeekly dataItems = new DataWeekly();
                dataItems.setActivity(cursor1.getString(3));
                dataItems.setTime(cursor1.getString(2));
                dataItems.setPlace(cursor1.getString(4));

                String activity = cursor1.getString(3);
                String place = cursor1.getString(4);
                String time = cursor1.getString(2);

                showAlarmNotification(context, activity, place,time, id);

                id++;

            } while (cursor1.moveToNext());
        }

    }

    public boolean isAlarmHasSet(Context context, String type){
        Intent intent = new Intent(context, ReminderReceiver.class);
        int requestCode = type.equalsIgnoreCase(DAILY_REMINDER) ? ID_DAILY : ID_RELEASE;

        return PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_NO_CREATE) != null;
    }

    private void showAlarmNotification(Context context, String activity, String place ,String time, int id) {
        String ID = "channel2";
        String NAME = "New Release Today";

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, ID)
                .setSmallIcon(R.drawable.ic_access_time_black_24dp)
                .setContentTitle(activity)
                .setContentText(time + " - " + place)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setContentIntent(pendingIntent)
                .setSound(alarmSound)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Notification notification = builder.build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(ID, NAME, NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(ID);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        if (notificationManager != null) {
            notificationManager.notify(id, notification);
        }
    }

    public void setDailyAlarm(Context context, String type) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReceiver.class);
        intent.putExtra(EXTRA_TYPE, type);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 6);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,ID_DAILY, intent,0);
        if(alarmManager != null){
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        }
    }

    public void cancelAlarm(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReceiver.class);
        int requestCode = type.equalsIgnoreCase(DAILY_REMINDER) ? ID_DAILY : ID_RELEASE;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
        Toast.makeText(context, "Alarm Canceled", Toast.LENGTH_SHORT).show();
    }
}
