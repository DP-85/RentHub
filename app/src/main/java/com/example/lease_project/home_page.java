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

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;


public class home_page extends AppCompatActivity {
    Button brands_btn, allBrandsBtn;
    LinearLayout landRover, bmw, audi, volvo, mercedes;
    ImageButton profileButton, optionsButton;
    EditText searchBar;

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

        List<String> brands = new ArrayList<>();

        searchBar = findViewById(R.id.searchBar);

        brands.add("BMW");
        brands.add("Audi");
        brands.add("Mercedes");
        brands.add("Volvo");
        brands.add("Land Rover");
        brands.add("Jeep");
        brands.add("Toyota");
        brands.add("Volkswagen");
        brands.add("Skoda");
        brands.add("Hyundai");
        brands.add("Kia");
        brands.add("Honda");
        brands.add("Suzuki");
        brands.add("Mahindra");
        brands.add("Tata");
        brands.add("MG");

        EditText searchBar = findViewById(R.id.searchBar);

        searchBar.setOnEditorActionListener((v, actionId, event) -> {
            String input = v.getText().toString().trim().toLowerCase();
            for (String brand : brands) {
                if (brand.toLowerCase().equals(input)) {
                    openBrandLineup(brand);
                    return true;  // Prevent further action
                }
            }
            // If no match, show a message
            Toast.makeText(home_page.this, "Brand not found", Toast.LENGTH_SHORT).show();
            return true;
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

// SCROLLVIEW BRANDS BUTTON
        brands_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home_page.this, brands_logos_page.class);
                startActivity(i);
                finish();
            }
        });

// FRONT BRANDS BUTTON
        allBrandsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(home_page.this, brands_logos_page.class);
                startActivity(I);
                finish();
            }
        });

// FUNCTIONS ATTACHED TO EACH LAYOUT IN ORDER TO OPEN A SPECIFIC BRAND LINEUP PAGE
        landRover.setOnClickListener(view -> openBrandLineup("Land Rover"));
        bmw.setOnClickListener(view -> openBrandLineup("BMW"));
        audi.setOnClickListener(view -> openBrandLineup("Audi"));
        volvo.setOnClickListener(view -> openBrandLineup("Volvo"));
        mercedes.setOnClickListener(view -> openBrandLineup("Mercedes"));

// BUTTON IMPLEMENTATION TO OPEN OPTIONS PAGE
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home_page.this, Options_page.class);
                startActivity(i);
                finish();
            }
        });

    }

// FUNCTION DEFINITION TO OPEN A SPECIFIC BRAND LINEUP PAGE
    private void openBrandLineup(String brandName) {
        Intent intent = new Intent(home_page.this, BrandLineup_page.class);
        intent.putExtra("brandName", brandName);  // Send brand name to BrandLineup page
        startActivity(intent);
        finish();
    }
}

