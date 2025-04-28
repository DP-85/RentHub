package com.example.lease_project;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.ImageButton;
import android.content.Intent;

public class Terms_Page extends AppCompatActivity {

    ImageButton Back, termspagehomebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_terms_page);

        Back = findViewById(R.id.Back);
        termspagehomebtn = findViewById(R.id.termsPageHomeButton);

        Back.setOnClickListener(view -> {
            Intent i = new Intent(Terms_Page.this, Options_page.class);
            startActivity(i);
        });

        termspagehomebtn.setOnClickListener(view -> {
            Intent home = new Intent(Terms_Page.this, home_page.class);
            startActivity(home);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}