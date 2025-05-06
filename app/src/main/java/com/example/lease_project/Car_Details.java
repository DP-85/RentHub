package com.example.lease_project;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.*;
import com.bumptech.glide.Glide;
import android.content.SharedPreferences;

public class Car_Details extends AppCompatActivity {

    // UI Components
    ImageButton backToLineup, backToHome;
    RadioButton petrolButton, dieselButton, electricButton;
    ImageView carImageView;
    TextView carSeating, carTransmission, carFuelType, carMaxSpeed, carEngine, carNameTextView, perDayPrice, perWeekPrice, perMonthPrice, customPrice;
    Button pickup;
    ImageView Clock1, Clock2, Clock3, Clock4;
    CardView plan1, plan2, plan3, plan4;
    String BrandName;
    // Variables to track prices and selection
    Double PetrolPrice, DieselPrice, ElectricPrice, selectedPrice = 0.0, days = 0.0;
    Boolean petrolSelect = Boolean.TRUE , dieselSelect = Boolean.FALSE, electricSelect = Boolean.FALSE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Enables edge-to-edge layout
        setContentView(R.layout.activity_car_details);

        // Adjust padding to avoid overlapping with system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Navigation Buttons
        backToHome = findViewById(R.id.detailsHomeRedirect);
        backToLineup = findViewById(R.id.backToLineupButton);

        // Back to home page
        backToHome.setOnClickListener(view -> {
            Intent home = new Intent(Car_Details.this, home_page.class);
            startActivity(home);
            finish();
        });

        // SharedPreferences to save car name for later use (like Order Summary)
        SharedPreferences sharedPreferences = getSharedPreferences("OrderSummaryCD", MODE_PRIVATE);
        SharedPreferences.Editor editorCD = sharedPreferences.edit();

        // Initialize UI components
        petrolButton = findViewById(R.id.petrolButton);
        dieselButton = findViewById(R.id.dieselButton);
        electricButton = findViewById(R.id.electricButton);

        carImageView = findViewById(R.id.carImage);
        carNameTextView = findViewById(R.id.carName);
        carEngine = findViewById(R.id.car_detail_engine);
        carMaxSpeed = findViewById(R.id.car_detail_speed);
        pickup = findViewById(R.id.pickupButton);

        Clock1 = findViewById(R.id.clock1);
        Clock2 = findViewById(R.id.clock2);
        Clock3 = findViewById(R.id.clock3);
        Clock4 = findViewById(R.id.clock4);

        perDayPrice = findViewById(R.id.perDayPlan);
        perWeekPrice = findViewById(R.id.perWeekPlan);
        perMonthPrice = findViewById(R.id.perMonthPlan);
        customPrice = findViewById(R.id.customPlan);

        plan1 = findViewById(R.id.plan1);
        plan2 = findViewById(R.id.plan2);
        plan3 = findViewById(R.id.plan3);
        plan4 = findViewById(R.id.plan4);

        // Get data from the previous page using Intent extras
        String carName = getIntent().getStringExtra("carName");
        String carengine = getIntent().getStringExtra("Engine");
        String carImage = getIntent().getStringExtra("carImage");
        BrandName = getIntent().getStringExtra("brandName");

        // Back to car lineup page
        backToLineup.setOnClickListener(view -> {
            openBrandLineup(BrandName);
        });

        // Set values to the UI
        carNameTextView.setText(carName);
        carEngine.setText(carengine);
        Glide.with(this).load(carImage).into(carImageView); // Load car image using Glide

        // Get more car info from Intent
        String Transmission = getIntent().getStringExtra("Transmission");
        String FuelType = getIntent().getStringExtra("FuelType");
        String Seating = getIntent().getStringExtra("Seating");
        String Maxspeed = getIntent().getStringExtra("Max Speed");

        // Set more car details to the UI
        carTransmission = findViewById(R.id.car_detail_transmission);
        carSeating = findViewById(R.id.car_detail_seating);
        carFuelType = findViewById(R.id.car_detail_fuelType);

        carTransmission.setText(Transmission);
        carFuelType.setText(FuelType);
        carSeating.setText(Seating);
        carMaxSpeed.setText(Maxspeed);

        // Save selected car name to shared preferences
        editorCD.putString("CarName", carName);
        editorCD.apply();

        // Get petrol and diesel prices from intent
        Double petrolPricePerDay = getIntent().getDoubleExtra("Petrol Price", 0);
        Double dieselPricePerDay = getIntent().getDoubleExtra("Diesel Price", 0);
        Double electricPricePerDay = getIntent().getDoubleExtra("Electric Price", 0);

        // Calculate weekly and monthly prices using helper function
        double PetrolPrices[] = calculateRentalPricing(petrolPricePerDay);
        double DieselPrices[] = calculateRentalPricing(dieselPricePerDay);
        double ElectricPrices[] = calculateRentalPricing(electricPricePerDay);

        // ========== PETROL BUTTON ==========
        petrolButton.setOnClickListener(view -> {
            petrolSelect = true;
            dieselSelect = false;

            // Change button background colors to indicate selection
            petrolButton.setBackgroundResource(R.drawable.car_detail_spec_bg_selector);
            dieselButton.setBackgroundResource(R.drawable.car_detailspec_bg);
            electricButton.setBackgroundResource(R.drawable.car_detailspec_bg);

            // Set petrol prices on the UI
            perDayPrice.setText(" ₹" + String.format("%.0f", PetrolPrices[0]) + "\n Day");
            perWeekPrice.setText(" ₹" + String.format("%.0f", PetrolPrices[1]) + "\n Week");
            perMonthPrice.setText(" ₹" + String.format("%.0f", PetrolPrices[2]) + "\n Month");

            // Plan 1 - Daily
            plan1.setOnClickListener(view1 -> {
                selectedPrice = PetrolPrices[0];
                days = 1.0;
                highlightPlan(plan1, Clock1);
            });

            // Plan 2 - Weekly
            plan2.setOnClickListener(view1 -> {
                selectedPrice = PetrolPrices[1];
                days = 7.0;
                highlightPlan(plan2, Clock2);
            });

            // Plan 3 - Monthly
            plan3.setOnClickListener(view1 -> {
                selectedPrice = PetrolPrices[2];
                days = 30.0;
                highlightPlan(plan3, Clock3);
            });

            // Plan 4 - Custom (currently treated same as daily)
            plan4.setOnClickListener(view1 -> {
                selectedPrice = PetrolPrices[0];
                days = -1.0; // special value for custom
                highlightPlan(plan4, Clock4);
            });
        });

        // ========== DIESEL BUTTON ==========
        dieselButton.setOnClickListener(view -> {
            petrolSelect = false;
            dieselSelect = true;

            // Update selection UI
            dieselButton.setBackgroundResource(R.drawable.car_detail_spec_bg_selector);
            petrolButton.setBackgroundResource(R.drawable.car_detailspec_bg);
            electricButton.setBackgroundResource(R.drawable.car_detailspec_bg);

            // Set diesel prices
            perDayPrice.setText(" ₹" + String.format("%.0f", DieselPrices[0]) + "\n Day");
            perWeekPrice.setText(" ₹" + String.format("%.0f", DieselPrices[1]) + "\n Week");
            perMonthPrice.setText(" ₹" + String.format("%.0f", DieselPrices[2]) + "\n Month");

            // Plan 1 - Daily
            plan1.setOnClickListener(view1 -> {
                selectedPrice = DieselPrices[0];
                days = 1.0;
                highlightPlan(plan1, Clock1);
            });

            // Plan 2 - Weekly
            plan2.setOnClickListener(view1 -> {
                selectedPrice = DieselPrices[1];
                days = 7.0;
                highlightPlan(plan2, Clock2);
            });

            // Plan 3 - Monthly
            plan3.setOnClickListener(view1 -> {
                selectedPrice = DieselPrices[2];
                days = 30.0;
                highlightPlan(plan3, Clock3);
            });

            // Plan 4 - Custom
            plan4.setOnClickListener(view1 -> {
                selectedPrice = DieselPrices[0];
                days = -1.0;
                highlightPlan(plan4, Clock4);
            });
        });

        electricButton.setOnClickListener(view -> {
            petrolSelect = true;
            dieselSelect = false;

            // Change button background colors to indicate selection
            electricButton.setBackgroundResource(R.drawable.car_detail_spec_bg_selector);
            dieselButton.setBackgroundResource(R.drawable.car_detailspec_bg);
            petrolButton.setBackgroundResource(R.drawable.car_detailspec_bg);


            // Set petrol prices on the UI
            perDayPrice.setText(" ₹" + String.format("%.0f", ElectricPrices[0]) + "\n Day");
            perWeekPrice.setText(" ₹" + String.format("%.0f", ElectricPrices[1]) + "\n Week");
            perMonthPrice.setText(" ₹" + String.format("%.0f", ElectricPrices[2]) + "\n Month");

            // Plan 1 - Daily
            plan1.setOnClickListener(view1 -> {
                selectedPrice = ElectricPrices[0];
                days = 1.0;
                highlightPlan(plan1, Clock1);
            });

            // Plan 2 - Weekly
            plan2.setOnClickListener(view1 -> {
                selectedPrice = ElectricPrices[1];
                days = 7.0;
                highlightPlan(plan2, Clock2);
            });

            // Plan 3 - Monthly
            plan3.setOnClickListener(view1 -> {
                selectedPrice = ElectricPrices[2];
                days = 30.0;
                highlightPlan(plan3, Clock3);
            });

            // Plan 4 - Custom (currently treated same as daily)
            plan4.setOnClickListener(view1 -> {
                selectedPrice = ElectricPrices[0];
                days = -1.0; // special value for custom
                highlightPlan(plan4, Clock4);
            });
        });


        // ========== PICKUP BUTTON ==========
        // Sends selected price and plan duration to next page
        pickup.setOnClickListener(view -> {
            if (selectedPrice != 0.0 && days != 0.0) {
                Intent intent = new Intent(Car_Details.this, Booking_page.class);
                intent.putExtra("selectedPrice", selectedPrice);
                intent.putExtra("days", days);
                intent.putExtra("carName", carName);
                intent.putExtra("brandName", BrandName);
                startActivity(intent);
            } else {
                Toast.makeText(Car_Details.this, "Please select a plan first!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ========== HELPER FUNCTION ==========
    // Highlights the selected plan visually and resets others
    private void highlightPlan(CardView selectedPlan, ImageView selectedClock) {
        // Reset backgrounds
        plan1.setBackgroundResource(R.drawable.rentplans_default_background);
        plan2.setBackgroundResource(R.drawable.rentplans_default_background);
        plan3.setBackgroundResource(R.drawable.rentplans_default_background);
        plan4.setBackgroundResource(R.drawable.rentplans_default_background);

        // Highlight selected plan
        selectedPlan.setBackgroundResource(R.drawable.rentplans_selected_background);

        // Reset clock colors
        Clock1.setColorFilter(Color.parseColor("black"), PorterDuff.Mode.SRC_IN);
        Clock2.setColorFilter(Color.parseColor("black"), PorterDuff.Mode.SRC_IN);
        Clock3.setColorFilter(Color.parseColor("black"), PorterDuff.Mode.SRC_IN);
        Clock4.setColorFilter(Color.parseColor("black"), PorterDuff.Mode.SRC_IN);

        // Highlight selected clock
        selectedClock.setColorFilter(Color.parseColor("blue"), PorterDuff.Mode.SRC_IN);
    }

    // ========== HELPER FUNCTION ==========
    // Calculates weekly (15% off) and monthly (25% off) prices
    private double[] calculateRentalPricing(double dailyPrice) {
        double weeklyPrice = (dailyPrice * 7) * 0.85;
        double monthlyPrice = (dailyPrice * 30) * 0.75;
        return new double[]{dailyPrice, weeklyPrice, monthlyPrice};
    }

    private void openBrandLineup(String brandName) {
        Intent intent = new Intent(Car_Details.this, BrandLineup_page.class);
        intent.putExtra("brandName", brandName);
        startActivity(intent);
        finish(); // optional, if you don't want users to return to Car_Details on back press
    }

}
