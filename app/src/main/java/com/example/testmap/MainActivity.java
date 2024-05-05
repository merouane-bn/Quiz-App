package com.example.testmap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText etLogin, etPassword;
    private Button bLogin;
    private TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        etLogin = findViewById(R.id.etMail);
        etPassword = findViewById(R.id.etPasswd);
        bLogin = findViewById(R.id.bLogin);
        tvRegister = findViewById(R.id.tvRegister);

        bLogin.setOnClickListener(v -> {
            String emailText = etLogin.getText().toString().trim();
            String passwordText = etPassword.getText().toString().trim();
            if (emailText.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
                etLogin.setError("Valid email is required");
            } else if (passwordText.isEmpty()) {
                etPassword.setError("Password is required");
            } else {
                mAuth.signInWithEmailAndPassword(emailText, passwordText)
                        .addOnSuccessListener(authResult -> {
                            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, MapActivity.class));
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(this, "Login Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });

        tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, Register.class));
        });
    }
}
