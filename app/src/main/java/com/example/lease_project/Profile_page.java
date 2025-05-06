package com.example.lease_project;

import android.app.DatePickerDialog;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Profile_page extends AppCompatActivity {

    ImageButton homeRedirect, profileOptionRedirect;
    TextView name, phone, email, address;
    EditText dlNumber, dlExpiry, resetPassword;
    Button saveChanges;
    FirebaseFirestore db;
    FirebaseAuth fauth;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_page);

        homeRedirect = findViewById(R.id.profileHomeRedirect);
        profileOptionRedirect = findViewById(R.id.profileOptionsRedirect);
        name = findViewById(R.id.profile_name);
        phone = findViewById(R.id.profile_phone);
        email = findViewById(R.id.profile_email);
        address = findViewById(R.id.profile_address);
        dlNumber = findViewById(R.id.profile_Dl);
        dlExpiry = findViewById(R.id.profile_Dl_expiry);
        resetPassword = findViewById(R.id.reset_pwd);
        saveChanges = findViewById(R.id.save_changes);

        db = FirebaseFirestore.getInstance();
        fauth = FirebaseAuth.getInstance();

        userID = Objects.requireNonNull(fauth.getCurrentUser()).getUid();

        DocumentReference documentReference = db.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, (documentSnapshot, error) -> {
            if (documentSnapshot != null && documentSnapshot.exists()) {
                name.setText(documentSnapshot.getString("Name"));
                phone.setText(documentSnapshot.getString("PhoneNo"));
                email.setText(documentSnapshot.getString("email"));
                address.setText(documentSnapshot.getString("Address"));

                // Initialize DL Number if missing
                if (documentSnapshot.contains("DLNumber")) {
                    dlNumber.setText(documentSnapshot.getString("DLNumber"));
                } else {
                    dlNumber.setText("");
                    documentSnapshot.getReference().update("DLNumber", "");
                }

                // Initialize DL Expiry if missing
                if (documentSnapshot.contains("DLExpiry")) {
                    dlExpiry.setText(documentSnapshot.getString("DLExpiry"));
                } else {
                    dlExpiry.setText("");
                    documentSnapshot.getReference().update("DLExpiry", "");
                }
            }
        });


        dlExpiry.setFocusable(false);
        dlExpiry.setOnClickListener(v -> showDatePickerDialog());

        saveChanges.setOnClickListener(view -> {
            String dlNum = dlNumber.getText().toString().trim();
            String expiryDate = dlExpiry.getText().toString().trim();
            String newPassword = resetPassword.getText().toString().trim();

            DocumentReference docRef = db.collection("Users").document(userID);
            docRef.update("DLNumber", dlNum, "DLExpiry", expiryDate)
                    .addOnSuccessListener(unused -> Toast.makeText(this, "Changes saved", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(this, "Error saving changes", Toast.LENGTH_SHORT).show());

            if (!newPassword.isEmpty()) {
                fauth.getCurrentUser().updatePassword(newPassword)
                        .addOnSuccessListener(unused -> Toast.makeText(this, "Password updated", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Toast.makeText(this, "Password update failed", Toast.LENGTH_SHORT).show());
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        homeRedirect.setOnClickListener(view -> {
            Intent i = new Intent(Profile_page.this, home_page.class);
            startActivity(i);
            finish();
        });

        profileOptionRedirect.setOnClickListener(view -> {
            Intent i = new Intent(Profile_page.this, Options_page.class);
            startActivity(i);
            finish();
        });
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    String formattedDate = String.format(Locale.getDefault(), "%02d-%02d-%d", dayOfMonth, month + 1, year);
                    dlExpiry.setText(formattedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }
}

/*
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

    ImageButton homeRedirect, profileOptionRedirect;
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
        profileOptionRedirect = findViewById(R.id.profileOptionsRedirect);
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

        profileOptionRedirect.setOnClickListener(view -> {
            Intent i = new Intent(Profile_page.this, Options_page.class);
            startActivity(i);
            finish();
        });
    }

}*/

