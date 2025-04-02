package com.example.lease_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.*;

public class Options_page extends AppCompatActivity {

    ImageButton backToHome;
    Button profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_options_page);

        backToHome = findViewById(R.id.backToHome);
        profileButton = findViewById(R.id.profileButton);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        backToHome.setOnClickListener(view -> {
            Intent i = new Intent(Options_page.this, home_page.class);
            startActivity(i);
        });

        profileButton.setOnClickListener(view -> {
            Intent p = new Intent(Options_page.this, Profile_page.class);
            startActivity(p);
        });
    }
}