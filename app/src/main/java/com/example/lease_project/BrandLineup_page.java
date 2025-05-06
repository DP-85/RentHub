package com.example.lease_project;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.LayoutInflater;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import android.widget.ImageButton;
import android.widget.TextView;
import android.util.Log;
import android.widget.Button;
import android.content.SharedPreferences;

public class BrandLineup_page extends AppCompatActivity {

    // UI Elements
    Button detailButton;
    TextView brand_name, rentNowButton;
    ImageButton brandLogosRedirect, homeimgbtn;
    RecyclerView brandLineup_recyclerView;
    LayoutInflater inflater;

    // Lists to hold car info
    List<String> carNames, carDetails, carImages;

    // Adapter to bind data to RecyclerView
    brandLineup_adapter adapter;

    // Firestore database instance
    FirebaseFirestore db;
    String brandName;
    // Additional specs
    List<String> Transmission, FuelType;
    List<String> Seating, MaxSpeed, Engine;
    List<Double> PetrolPrice, DieselPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Make app full screen with edge-to-edge content
        setContentView(R.layout.activity_brand_lineup_page);

        // SharedPreferences for storing brand temporarily (optional feature)
        SharedPreferences sharedPreferences = getSharedPreferences("OrderSummaryBL", MODE_PRIVATE);
        SharedPreferences.Editor editorBL = sharedPreferences.edit();

        // Link UI elements to Java code
        brandLineup_recyclerView = findViewById(R.id.brand_lineup_recyclerview);
        brand_name = findViewById(R.id.brand_name);
        brandLogosRedirect = findViewById(R.id.brandlogosredirect);
        homeimgbtn = findViewById(R.id.homeimgButton);

        // Initialize all lists
        carNames = new ArrayList<>();
        carDetails = new ArrayList<>();
        carImages = new ArrayList<>();
        Transmission = new ArrayList<>();
        FuelType = new ArrayList<>();
        Seating = new ArrayList<>();
        MaxSpeed = new ArrayList<>();
        Engine = new ArrayList<>();
        PetrolPrice = new ArrayList<>();
        DieselPrice = new ArrayList<>();

        // Get Firestore instance
        db = FirebaseFirestore.getInstance();

        // Get brand name passed from previous activity
        brandName = getIntent().getStringExtra("brandName");
        brand_name.setText(brandName);

        // Fetch car data for that brand from Firestore
        if (brandName != null) {
            fetchCarFromFirestore(brandName);
        } else {
            Log.e("BrandLineup_page", "Brand name is null!");
        }

        // Initialize adapter with empty lists initially
        adapter = new brandLineup_adapter(this, brandName, carNames, carDetails, carImages,
                Transmission, FuelType, Seating, MaxSpeed, Engine, PetrolPrice, DieselPrice);

        // Setup RecyclerView with vertical layout and adapter
        brandLineup_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        brandLineup_recyclerView.setAdapter(adapter);

        // Set padding for notch, status bar, etc.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Go to brand logos page
        brandLogosRedirect.setOnClickListener(view -> {
            Intent i = new Intent(BrandLineup_page.this, brands_logos_page.class);
            startActivity(i);
            finish();
        });

        // Go to home page
        homeimgbtn.setOnClickListener(view -> {
            Intent h = new Intent(BrandLineup_page.this, home_page.class);
            startActivity(h);
            finish();
        });
    }

    // This function fetches all cars under the brand from Firestore
    private void fetchCarFromFirestore(String brandName) {
        db.collection("Cars").document(brandName).collection("cars")
                .get()
                .addOnCompleteListener(task -> {
                    // 📦 `task` = result of the Firestore query (all documents under that brand)
                    if (task.isSuccessful()) {
                        // Clear lists first if you expect refresh

                        for (DocumentSnapshot document : task.getResult()) {
                            try {
                                // Extract data from Firestore document
                                String name = document.getString("Name");
                                String details = document.getString("Fuel Type") + " | " +
                                        document.getString("Transmission") + " | " +
                                        document.getString("Seating") + "-Seater";
                                String imageUrl = document.getString("Image");

                                // Individual specs
                                String transmission = "Transmission\n" + document.getString("Transmission");
                                String fueltype = "Fuel Type\n" + document.getString("Fuel Type");
                                String seating = document.getString("Seating") + " Seater";
                                String maxspeed = document.getString("Max Speed");
                                String engine = "Engine\n" + document.getString("Engine");

                                // Convert petrol price safely
                                double petrolPrice = 0.0;
                                if (document.contains("Petrol Price")) {
                                    Object petrolValue = document.get("Petrol Price");
                                    if (petrolValue instanceof Number) {
                                        petrolPrice = ((Number) petrolValue).doubleValue();
                                    }
                                }

                                // Convert diesel price safely
                                double dieselPrice = 0.0;
                                if (document.contains("Diesel Price")) {
                                    Object dieselValue = document.get("Diesel Price");
                                    if (dieselValue instanceof Number) {
                                        dieselPrice = ((Number) dieselValue).doubleValue();
                                    }
                                }

                                // Add all values to lists
                                carNames.add(name);
                                carDetails.add(details);
                                carImages.add(imageUrl);
                                Transmission.add(transmission);
                                FuelType.add(fueltype);
                                Seating.add(seating);
                                MaxSpeed.add(maxspeed);
                                Engine.add(engine);
                                PetrolPrice.add(petrolPrice);
                                DieselPrice.add(dieselPrice);
                            } catch (Exception e) {
                                Log.e("Firestore", "Error processing car: " + document.getId(), e);
                            }
                        }

                        // 🔁 Notify adapter that data has changed, update RecyclerView
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.e("Firestore", "Error fetching data", task.getException());
                    }
                }); // this whole block is an OnCompleteListener (runs once the query is done)
    }
}


