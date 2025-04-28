package com.example.lease_project;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;
import android.content.Intent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signup_page extends AppCompatActivity {

    EditText signup_name, signup_phone, signup_email, signup_password, signup_confirm_password, signup_address;
    Button signup_button, login_redirect;
    FirebaseAuth fauth;
    FirebaseFirestore db;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup_page);

        signup_name = findViewById(R.id.signup_name);
        signup_phone = findViewById(R.id.signup_phone);
        signup_email = findViewById(R.id.signup_email);
        signup_password = findViewById(R.id.signup_password);
        signup_confirm_password = findViewById(R.id.signup_confirm_password);
        signup_button = findViewById(R.id.signup_button);
        signup_address = findViewById(R.id.signup_address);
        login_redirect = findViewById(R.id.login_redirect);

        fauth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = signup_name.getText().toString().trim();
                String phone = signup_phone.getText().toString().trim();
                String email = signup_email.getText().toString().trim();
                String password = signup_password.getText().toString().trim();
                String address = signup_address.getText().toString().trim();

                String fullname = signup_name.getText().toString();
                String phoneno = signup_phone.getText().toString();
                String Email = signup_email.getText().toString();
                String Address = signup_address.getText().toString();

                if(TextUtils.isEmpty(name)) {
                    signup_name.setError("Name is required");
                    return;
                }

                if (!phone.matches("\\d{10}")) { // Ensuring 10-digit phone number
                    signup_phone.setError("Enter a valid 10-digit phone number");
                    return;
                }

                if(TextUtils.isEmpty(email)) {
                    signup_email.setError("Email is required");
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    signup_password.setError("Password is required");
                    return;
                }

                if(password.length() < 7) {
                    signup_password.setError("Password must be at least 7 character");
                    return;
                }

                if(TextUtils.isEmpty(address)) {
                    signup_address.setError("Address is mandatory");
                }

                if(address.length() < 13) {
                    signup_address.setError("Address is too short");
                    return;
                }

                fauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(signup_page.this, "User Registered", Toast.LENGTH_SHORT).show();
                            userID = fauth.getCurrentUser().getUid(); // Initialize userID first
                            DocumentReference documentReference = db.collection("Users").document(userID);
                            Map<String, Object> user = new HashMap<>();

                            user.put("Name", fullname);
                            user.put("email", Email);
                            user.put("PhoneNo", phoneno);
                            user.put("Address", Address);

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d("Tag", "onSuccess: User profile is created for " + userID);
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), login_page.class));
                        }
                        else {
                            Toast.makeText(signup_page.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        login_redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login_page = new Intent(signup_page.this, com.example.lease_project.login_page.class);
                startActivity(login_page);
            }
        });
    }
}