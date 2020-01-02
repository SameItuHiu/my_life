package com.example.mylife;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ActionBar action = getSupportActionBar();
        if (action != null) {
            action.setTitle("Settings");
            action.setDisplayHomeAsUpEnabled(true);
            action.setHomeButtonEnabled(true);
        }

        final Switch daily = findViewById(R.id.switchDaily);
        final ReminderReceiver alarmReminder = new ReminderReceiver();

        if(alarmReminder.isAlarmHasSet(this,ReminderReceiver.DAILY_REMINDER)){
            daily.setChecked(true);
        }else{
            daily.setChecked(false);
        }

        daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(daily.isChecked()){
                    alarmReminder.setDailyAlarm(Settings.this,ReminderReceiver.DAILY_REMINDER);
                    Toast toast = Toast.makeText(Settings.this, "Daily set on", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    alarmReminder.cancelAlarm(Settings.this, ReminderReceiver.DAILY_REMINDER);
                    Toast toast = Toast.makeText(Settings.this, "Daily set off", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
}
