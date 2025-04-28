package com.example.lease_project;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.*;
import android.content.Intent;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

public class Profile_page extends AppCompatActivity {

    ImageButton homeRedirect;
    TextView name, phone, email, address;
    EditText dlNumber, dlExpiry, resetPassword;
    FirebaseFirestore db;
    FirebaseAuth fauth;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_page);

        homeRedirect = findViewById(R.id.profileHomeRedirect);
        name = findViewById(R.id.profile_name);
        phone = findViewById(R.id.profile_phone);
        email = findViewById(R.id.profile_email);
        address = findViewById(R.id.profile_address);
        dlNumber = findViewById(R.id.profile_Dl);

        db = FirebaseFirestore.getInstance();
        fauth = FirebaseAuth.getInstance();

        userID = Objects.requireNonNull(fauth.getCurrentUser()).getUid();

        DocumentReference documentReference = db.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                name.setText(documentSnapshot.getString("Name"));
                phone.setText(documentSnapshot.getString("PhoneNo"));
                email.setText(documentSnapshot.getString("email"));
                address.setText(documentSnapshot.getString("Address"));
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        homeRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Profile_page.this, home_page.class);
                startActivity(i);
                finish();
            }
        });
    }


}

/*

    Variables:
    name, phone, email, address, dlNumber, dlExpiry, forgotPassword

    Functions:
    onEvent(documentSnapshot, FirebaseFirestoreException)

*/