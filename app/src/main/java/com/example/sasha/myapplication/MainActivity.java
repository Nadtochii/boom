package com.example.sasha.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sasha.myapplication.events.EventsManager;
import com.example.sasha.myapplication.fragments.FragmentMain;
import com.example.sasha.myapplication.game.PersonsDB;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PersonsDB.init(this);
        EventsManager.init(this);

        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentMain()).addToBackStack(null).commit();
    }

    @Override
    protected void onDestroy() {
        EventsManager.terminate();
        PersonsDB.terminate();

        super.onDestroy();
    }
}
