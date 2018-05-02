package com.example.padhil.mofo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

  private EditText email;
  private EditText password;
  private Button login;
  private FirebaseAuth firebaseAuth;
  
    private CallbackManager callbackManager;
    private LoginButton loginButton;

    private static final String TAG = "MAIN_ACTIVITY";
    private SignInButton mGooglebtn;
    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleApiClient;


    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        firebaseAuth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.loginButton);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                goMainMenu();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Cancel Login", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "Login Error", Toast.LENGTH_SHORT).show();
            }
        });

        mGooglebtn = findViewById(R.id.sign_in_button);

        firebaseAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){

                    startActivity(new Intent(LoginActivity.this, MainMenu.class));
                }
            }
        };
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(LoginActivity.this, "Ada error", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGooglebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    public void loginbtn(View view) {

        if (TextUtils.isEmpty(email.getText().toString())){
            Toast.makeText(LoginActivity.this, "Please fill in", Toast.LENGTH_LONG).show();
            return;
        }else if(TextUtils.isEmpty(password.getText().toString())) {
            Toast.makeText(LoginActivity.this, "Please fill in", Toast.LENGTH_LONG).show();
            return;
        }

            final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this, "Please wait...", "Processing...", true);
        (firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                       if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(LoginActivity.this, MainMenu.class);
                            i.putExtra("Email", firebaseAuth.getCurrentUser().getEmail());
                            startActivity(i);
                        } else {
                            Log.e("ERROR", task.getException().toString());
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }

    public void registerbtn(View view) {
        Intent r1 = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(r1);
    }

    private void goMainMenu() {
        Intent intent = new Intent(this, MainMenu.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

                Intent intent = new Intent(LoginActivity.this,MainMenu.class);
                startActivity(intent);
              
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivity(signInIntent);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Intent intent = new Intent(LoginActivity.this,MainMenu.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Autentikasi gagal", Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
}

