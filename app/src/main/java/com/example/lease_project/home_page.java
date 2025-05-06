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

import com.google.firebase.firestore.FirebaseFirestore;

import android.widget.EditText;
import java.util.ArrayList;
import java.util.List;

public class home_page extends AppCompatActivity {
    Button brands_btn, allBrandsBtn;
    LinearLayout landRover, bmw, audi, volvo, mercedes;
    ImageButton profileButton, optionsButton;
    EditText searchBar;
    TextView X1Details, XUVDetails, CherokeeDetails, cClassDetails;
    TextView rentNow1, rentNow2, rentNow3, rentNow4;

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

        X1Details = findViewById(R.id.detailsBtnX1);
        XUVDetails = findViewById(R.id.detailsBtnXUV700);
        CherokeeDetails = findViewById(R.id.detailsBtnCherokee);
        cClassDetails = findViewById(R.id.detailsBtnCClass);

        rentNow1 = findViewById(R.id.rentNowBtn1);
        rentNow2 = findViewById(R.id.rentNowBtn2);
        rentNow3 = findViewById(R.id.rentNowBtn3);
        rentNow4 = findViewById(R.id.rentNowBtn4);

        brands.add("BMW");
        brands.add("Audi");
        brands.add("Mercedes");
        brands.add("Volvo");
        brands.add("Land Rover");
        brands.add("Jeep");
        brands.add("Toyota");
        brands.add("Volkswagen");
        //brands.add("Skoda");
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


        X1Details.setOnClickListener(view -> {
            openCarDetails("BMW", "BMW X1".trim());
        });

        XUVDetails.setOnClickListener(view -> {
            openCarDetails("Mahindra", "Mahindra XUV700".trim());
        });

        CherokeeDetails.setOnClickListener(view -> {
            openCarDetails("Jeep", "Jeep Grand Cherokee".trim());
        });

        cClassDetails.setOnClickListener(view -> {
            openCarDetails("Mercedes", "Mercedes-Benz C-Class".trim());
        });


        rentNow1.setOnClickListener(view -> {
            Intent i = new Intent(home_page.this, RentNowPage.class);
            startActivity(i);
        });

        rentNow2.setOnClickListener(view -> {
            Intent i = new Intent(home_page.this, RentNowPage.class);
            startActivity(i);
        });

        rentNow3.setOnClickListener(view -> {
            Intent i = new Intent(home_page.this, RentNowPage.class);
            startActivity(i);
        });

        rentNow4.setOnClickListener(view -> {
            Intent i = new Intent(home_page.this, RentNowPage.class);
            startActivity(i);
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

    private void openCarDetails(String brandName, String carName) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Cars")
                .document(brandName)
                .collection("cars")
                .document(carName)
                .get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        Intent intent = new Intent(home_page.this, Car_Details.class);
                        intent.putExtra("carName", carName);
                        intent.putExtra("carImage", doc.getString("Image"));
                        intent.putExtra("Transmission", doc.getString("Transmission"));
                        intent.putExtra("FuelType", doc.getString("Fuel Type"));
                        intent.putExtra("Seating", doc.getString("Seating") + " Seater");
                        intent.putExtra("Max Speed", doc.getString("Max Speed"));
                        intent.putExtra("Engine", doc.getString("Engine"));

                        // Send prices safely
                        Double petrolPrice = doc.getDouble("Petrol Price");
                        Double dieselPrice = doc.getDouble("Diesel Price");
                        intent.putExtra("Petrol Price", petrolPrice != null ? petrolPrice : 0.0);
                        intent.putExtra("Diesel Price", dieselPrice != null ? dieselPrice : 0.0);

                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Car not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error loading car details", Toast.LENGTH_SHORT).show();
                });
    }
}