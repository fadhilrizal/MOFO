package com.example.padhil.mofo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText fullnameRegister;
    private EditText emailRegister;
    private EditText usernameRegister;
    private EditText passwordRegister;
    private EditText phoneRegister;
    private Button btnsubmit;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullnameRegister = (EditText) findViewById(R.id.fullnameRegister);
        emailRegister = (EditText) findViewById(R.id.emailRegister);
        usernameRegister = (EditText) findViewById(R.id.usernameRegister);
        passwordRegister = (EditText) findViewById(R.id.passwordRegister);
        phoneRegister = (EditText) findViewById(R.id.phoneRegister);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void submit(View view) {

        final ProgressDialog progressDialog = ProgressDialog.show(RegisterActivity.this, "Please wait...", "Processing...", true);
        (firebaseAuth.createUserWithEmailAndPassword(emailRegister.getText().toString(), passwordRegister.getText().toString())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();

                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(i);
                }
                else
                {
                    Log.e("ERROR", task.getException().toString());
                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }


            }

        });

    }
}
