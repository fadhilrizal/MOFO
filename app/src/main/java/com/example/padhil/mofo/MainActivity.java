package com.example.padhil.mofo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      
        //Hypo
        //haiyoooo deyyy
        //fadell
        //deliaaaaa
        //azkya nih


        //dnnn

        //test

      
    }

    public void Regis(View view) {
        Intent hey = new Intent(MainActivity.this, Second.class);
        startActivity(hey);
    }
}
