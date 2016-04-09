package com.example.sasha.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sasha.myapplication.fragments.FragmentMain;

public class MainActivity extends AppCompatActivity {

//    private Button mRulesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentMain()).addToBackStack(null).commit();
//        mRulesButton = (Button)findViewById(R.id.button2);
//        mRulesButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mRulesButton.setText("This is rules!");
//            }
//        });
    }
}
