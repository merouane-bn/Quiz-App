package com.example.testmap;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    EditText etMail, etPassword, etPassword1;
    Button bRegister;
    private FirebaseAuth mAuth;
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser=mAuth.getCurrentUser();
        if(currentUser!=null){
            startActivity(new Intent(Register.this, MapActivity.class));
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        etMail=(EditText) findViewById(R.id.etMail);
        etPassword=(EditText) findViewById(R.id.etPasswd);
        etPassword1=(EditText)findViewById(R.id.etConfirmPasswd);
        bRegister=(Button)findViewById(R.id.bRegister);
        mAuth = FirebaseAuth.getInstance();

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=etMail.getText().toString();
                String password=etPassword.getText().toString();
                String password1=etPassword1.getText().toString();
                if(TextUtils.isEmpty(mail)){
                    Toast.makeText(getApplicationContext(),"Veuillez inserer votre adresse",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Veuillez inserer votre mdp",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password1)){
                    Toast.makeText(getApplicationContext(),"Veuillez confirmer votre mdp",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.length()<4){
                    Toast.makeText(getApplicationContext(),"Votre mdp doit contenir au moins 4 caracteres",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password.equals(password1)){
                    Toast.makeText(getApplicationContext(),"Mdp invalid, reessayer",Toast.LENGTH_SHORT).show();
                    return;
                }

                signUp(etMail.getText().toString(),etPassword.getText().toString());

            }
        });
    }

    public void signUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign up success
                            Toast.makeText(getApplicationContext(),"Inscription réussie!",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Register.this, MapActivity.class));
                            finish();
                        } else {
                            // Sign up fails
                            Toast.makeText(getApplicationContext(), "Sign up failed. " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}