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
    Button detailButton;
    TextView brand_name;
    ImageButton brandLogosRedirect;
    RecyclerView brandLineup_recyclerView;
    LayoutInflater inflater;
    List<String> carNames, carDetails, carImages;
    brandLineup_adapter adapter;
    FirebaseFirestore db;

    // NEW CODE
    List<String> Transmission, FuelType;
    List<String> Seating, MaxSpeed, Engine;
    List<Double> PetrolPrice, DieselPrice;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_brand_lineup_page);

        // SHARED PREFERENCES INITIALIZATION
        SharedPreferences sharedPreferences = getSharedPreferences("OrderSummaryBL", MODE_PRIVATE);
        SharedPreferences.Editor editorBL = sharedPreferences.edit();

        brandLineup_recyclerView = findViewById(R.id.brand_lineup_recyclerview);
        carNames = new ArrayList<>();
        carDetails = new ArrayList<>();
        carImages = new ArrayList<>();

        // NEW CODE
        Transmission = new ArrayList<>();
        FuelType = new ArrayList<>();
        Seating = new ArrayList<>();
        MaxSpeed = new ArrayList<>();
        Engine = new ArrayList<>();

        PetrolPrice = new ArrayList<>();
        DieselPrice = new ArrayList<>();
        //

        adapter = new brandLineup_adapter(this, carNames, carDetails, carImages, Transmission, FuelType,
                Seating, MaxSpeed, Engine, PetrolPrice, DieselPrice);

        brandLineup_recyclerView.setLayoutManager(new LinearLayoutManager(this));

        brandLineup_recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        brand_name = findViewById(R.id.brand_name);

        String brandName = getIntent().getStringExtra("brandName"); // Use "brandName" to match the key
        brand_name.setText(brandName);
        if (brandName != null) {
            fetchCarFromFirestore(brandName);
        } else {
            Log.e("BrandLineup_page", "Brand name is null!");
        }

        editorBL.putString("BrandName", brandName);
        editorBL.apply();
        Log.d("SharedPreferences", "Stored BrandName: " + brandName);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    private void fetchCarFromFirestore(String brandName) {
        db.collection("Cars").document(brandName).collection("cars") // Fetch cars from the brand's subcollection
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        carNames.clear();
                        carDetails.clear();
                        carImages.clear();

                        // NEW CODE
                        Transmission.clear();
                        FuelType.clear();
                        Seating.clear();
                        MaxSpeed.clear();
                        Engine.clear();
                        //

                        PetrolPrice.clear();
                        DieselPrice.clear();

                        for (DocumentSnapshot document : task.getResult()) {
                            String name = document.getString("Name");
                            String details = document.getString("Fuel Type") + " | " +
                                    document.getString("Transmission") + " | " +
                                    document.getString("Seating") + "-Seater";
                            String imageUrl = document.getString("Image");

                            // NEW CODE

                            String transmission = "Transmission\n" + document.getString("Transmission");
                            String fueltype = "Fuel Type\n" + document.getString("Fuel Type");
                            String seating = document.getString("Seating") + " Seater";
                            String maxspeed = document.getString("Max Speed");
                            String engine = "Engine\n" + document.getString("Engine");
                            //

                            double petrolPrice = document.getDouble("Petrol Price");
                            double dieselPrice = document.getDouble("Diesel Price");

                            carNames.add(name);
                            carDetails.add(details);
                            carImages.add(imageUrl);

                            // NEW CODE
                            Transmission.add(transmission);
                            FuelType.add(fueltype);
                            Seating.add(seating);
                            MaxSpeed.add(maxspeed);
                            Engine.add(engine);
                            //

                            PetrolPrice.add(petrolPrice);
                            DieselPrice.add(dieselPrice);
                        }

                        adapter.notifyDataSetChanged();
                    } else {
                        Log.e("Firestore", "Error fetching data", task.getException());
                    }
                });
    }

    private void setupRecyclerView(List<String> carNames, List<String> carDetails, List<String> carImages, List<String> Transmission,
                                   List<String> FuelType, List<String> Seating, List<String> MaxSpeed, List<String> Engine,
                                   List<Double> PetrolPrice, List<Double> DieselPrice) {
        adapter = new brandLineup_adapter(this, carNames, carDetails, carImages, Transmission, FuelType, Seating, MaxSpeed, Engine,
                PetrolPrice, DieselPrice);
        brandLineup_recyclerView.setAdapter(adapter);
    }


}