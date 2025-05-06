package com.example.lease_project;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.*;
import android.content.Intent;

public class AboutUs_Page extends AppCompatActivity {

    ImageButton optionsredirect, homeredirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about_us_page);

        optionsredirect = findViewById(R.id.abtUsOptionsRedirect);
        homeredirect = findViewById(R.id.abtUsHomeRedirect);

        optionsredirect.setOnClickListener(view -> {
            Intent i = new Intent(AboutUs_Page.this, Options_page.class);
            startActivity(i);
        });

        homeredirect.setOnClickListener(view -> {
            Intent i = new Intent(AboutUs_Page.this, home_page.class);
            startActivity(i);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}