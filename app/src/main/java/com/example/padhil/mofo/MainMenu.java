package com.example.padhil.mofo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void forcast(View view) {
        Intent l1 = new Intent(MainMenu.this, MenuForcastActivity.class);
        startActivity(l1);
    }
}
