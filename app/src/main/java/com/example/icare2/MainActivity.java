package com.example.icare2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static FragmentManager fragmentManager;
    public static MyDatabase MyDatabase; //creo l'istanza del database

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.priority:
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new PriorityFragment()).
                        addToBackStack(null).commit(); //aggiungo al backstack
        }
        switch (item.getItemId()){
            case R.id.notifications:
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new NotificationsFragment()).
                        addToBackStack(null).commit(); //aggiungo al backstack
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
