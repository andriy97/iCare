package com.example.icare2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    public static FragmentManager fragmentManager;
    public static MyDatabase MyDatabase; //creo l'istanza del database

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.priority:
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new PriorityFragment()).
                        addToBackStack(null).commit(); //aggiungo al backstack
                break;
            case R.id.graphs:
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new GraphsFragment()).
                        addToBackStack(null).commit(); //aggiungo al backstack
                break;

            case R.id.notifications:
                Calendar c =Calendar.getInstance();
                int ora=c.get(Calendar.HOUR_OF_DAY);
                int minuti=c.get(Calendar.MINUTE);

                TimePickerDialog timedialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //metto l'ora selezionata
                        Toast.makeText(getApplicationContext(), "Orario notifiche selezionato", Toast.LENGTH_SHORT).show();
                        Calendar ca =Calendar.getInstance();
                        ca.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        ca.set(Calendar.MINUTE, minute);
                        ca.set(Calendar.SECOND,0);

                        Intent intent= new Intent(MainActivity.this, Notification_reciever.class);
                        PendingIntent pendingIntent =PendingIntent.getBroadcast(MainActivity.this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        AlarmManager alarmManager= (AlarmManager)getSystemService(ALARM_SERVICE);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, ca.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                    }
                }, ora, minuti, true);
                timedialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //menu toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        MyDatabase = Room.databaseBuilder(getApplicationContext(), MyDatabase.class, "reportdb").allowMainThreadQueries().build(); //creo database (faccio allow main tread anche se non è da fare sul main thread)

        if (findViewById(R.id.fragment_container)!=null){ //se il container esiste, esiste perché l'ho creato in activity_main.xml

            if(savedInstanceState!=null)
            {
                return;
            }
            fragmentManager.beginTransaction().add(R.id.fragment_container, new HomeFragment()).commit(); //aggiungo il fragment HomeFragment al container
        }

    }

}
