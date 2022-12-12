package com.example.pinkcal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Button;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dashboard);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent  intent=new Intent(MainActivity.this, EventosActivity.class);
                startActivity(intent);
                finish();
            }
        },100);



    }
    }


