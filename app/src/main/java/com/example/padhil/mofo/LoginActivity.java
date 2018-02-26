package com.example.padhil.mofo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      
    }

    public void loginbtn(View view) {
        Intent l1 = new Intent(LoginActivity.this, MainMenu.class);
        startActivity(l1);
        }

    public void registerbtn(View view) {
        Intent r1 = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(r1);
    }
}

