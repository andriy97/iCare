package com.example.icare2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    public static FragmentManager fragmentManager;
    public static MyDatabase MyDatabase; //creo l'istanza del database

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
