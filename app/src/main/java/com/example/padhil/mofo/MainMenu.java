package com.example.padhil.mofo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;


public class MainMenu extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        mAuth = FirebaseAuth.getInstance();
    }

    public void goWeather(View view) {
        Intent weather = new Intent(MainMenu.this, MenuForcastActivity.class);
        startActivity(weather);
    }

    public void logout(View view){
            mAuth.signOut();
            Intent intent = new Intent(MainMenu.this, LoginActivity.class);
            startActivity(intent);
            finish();
    }
}
