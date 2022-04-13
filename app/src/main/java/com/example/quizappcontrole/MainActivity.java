package com.example.quizappcontrole;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

        TextView email= (TextView) findViewById(R.id.email) ;
        TextView password= (TextView) findViewById(R.id.password) ;
        Button loginbtn = findViewById(R.id.loginBtn) ;
        TextView sigup = findViewById(R.id.sigup) ;
        sigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this , SignUp.class) ;
                startActivity(i);

            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Login(email.getText().toString().trim(),password.getText().toString().trim());

            } });

    }
    public void Login(String email , String password)
    {
        mAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "login:success");

                    Intent i = new Intent(MainActivity.this , localisation.class) ;
                    startActivity(i);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "login:failure", task.getException());
                    Toast.makeText(MainActivity.this, "Authentication failed."+email + password,
                            Toast.LENGTH_SHORT).show();
                    //  updateUI(null);
                }
            }
        });
    }


}