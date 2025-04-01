package com.example.lease_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.*;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class home_page extends AppCompatActivity {

    Button brands_btn, allBrandsBtn;

    LinearLayout landRover, bmw, audi, volvo, mercedes;

    ImageButton profileButton, optionsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);

        brands_btn = findViewById(R.id.brands_btn);
        allBrandsBtn = findViewById(R.id.allBrandsButton);

        landRover = findViewById(R.id.landRoverLayout);
        bmw = findViewById(R.id.BmwLayout);
        audi = findViewById(R.id.audiLayout);
        volvo = findViewById(R.id.volvoLayout);
        mercedes = findViewById(R.id.mercedesLayout);

        optionsButton = findViewById(R.id.optionsButton);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        brands_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home_page.this, brands_logos_page.class);
                startActivity(i);
            }
        });

        allBrandsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(home_page.this, brands_logos_page.class);
                startActivity(I);
            }
        });

        landRover.setOnClickListener(view -> openBrandLineup("Land Rover"));
        bmw.setOnClickListener(view -> openBrandLineup("BMW"));
        audi.setOnClickListener(view -> openBrandLineup("Audi"));
        volvo.setOnClickListener(view -> openBrandLineup("Volvo"));
        mercedes.setOnClickListener(view -> openBrandLineup("Mercedes"));

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home_page.this, Options_page.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void openBrandLineup(String brandName) {
        Intent intent = new Intent(home_page.this, BrandLineup_page.class);
        intent.putExtra("brandName", brandName);  // Send brand name to BrandLineup page
        startActivity(intent);
        finish();
    }


}

