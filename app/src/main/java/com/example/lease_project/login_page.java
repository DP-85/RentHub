package com.example.lease_project;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import android.content.Intent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login_page extends AppCompatActivity {

    EditText login_email, login_password;
    Button signup_redirect, login_button, forgot_password;
    FirebaseAuth fauth;
    ImageButton btn1, btn2, btn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_page);

        login_email = findViewById(R.id.login_username);
        login_password = findViewById(R.id.login_password);
        signup_redirect = findViewById(R.id.signup_redirect);
        login_button = findViewById(R.id.login_button);
        forgot_password = findViewById(R.id.forgot_password);

        btn1 = findViewById(R.id.imageButton);
        btn2 = findViewById(R.id.imageButton2);
        btn3 = findViewById(R.id.imageButton3);

        fauth = FirebaseAuth.getInstance();

        //Ignore this bit
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn1.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.facebook.com/share/1KTvZjTm8b/?mibextid=wwXIfr"));
            startActivity(intent);
        });

        btn2.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.instagram.com/carwaleindia?igsh=bHdwOGp5anl5aTV1"));
            startActivity(intent);
        });

        btn3.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.carwale.com/"));
            startActivity(intent);
        });


        signup_redirect.setOnClickListener(view -> {
            Intent sp = new Intent(login_page.this, signup_page.class);
            startActivity(sp);
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = login_email.getText().toString().trim();
                String password = login_password.getText().toString().trim();

                if(TextUtils.isEmpty(email)) {
                    login_email.setError("Email is required");
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    login_password.setError("Password is required");
                    return;
                }

                if(password.length() < 7) {
                    login_password.setError("Password must be at least 7 character");
                    return;
                }

                fauth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(login_page.this, "WELCOME BACK", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), home_page.class));
                        }
                        else{
                            Toast.makeText(login_page.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resetMail = new EditText(view.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Reset your Password");
                passwordResetDialog.setMessage("Enter your email to receive password reset link");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String mail = resetMail.getText().toString();
                        fauth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(login_page.this, "Reset link sent to your mail", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(login_page.this, "Error! Reset link is not sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                passwordResetDialog.create().show();
            }
        });


    }
}