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
    ImageButton backToLineup;
    RadioButton petrolButton, dieselButton, hybridButton;
    ImageView carImageView;
    TextView carSeating, carTransmission, carFuelType, carMaxSpeed, carEngine, carNameTextView, perDayPrice, perWeekPrice, perMonthPrice, customPrice;
    Button pickup;
    Double PetrolPrice, DieselPrice, selectedPrice = 0.0, days = 0.0;
    ImageView Clock1, Clock2, Clock3, Clock4;
    Boolean petrolSelect = Boolean.TRUE , dieselSelect = Boolean.FALSE;
    CardView plan1, plan2, plan3, plan4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_car_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // SHARED PREFERENCES INITIALIZATION
        SharedPreferences sharedPreferences = getSharedPreferences("OrderSummaryCD", MODE_PRIVATE);
        SharedPreferences.Editor editorCD = sharedPreferences.edit();

        // Initialize views AFTER setContentView()
        backToLineup = findViewById(R.id.backButton);
        petrolButton = findViewById(R.id.petrolButton);
        dieselButton = findViewById(R.id.dieselButton);
        hybridButton = findViewById(R.id.hybridButton);
        
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

        // Get data from intent
        String carName = getIntent().getStringExtra("carName");
        String carengine = getIntent().getStringExtra("Engine");
        String carImage = getIntent().getStringExtra("carImage");

        // Set data in views
        carNameTextView.setText(carName);
        carEngine.setText(carengine);
        Glide.with(this).load(carImage).into(carImageView);

        // NEW CODE
        String Transmission = getIntent().getStringExtra("Transmission");
        String FuelType = getIntent().getStringExtra("FuelType");
        String Seating = getIntent().getStringExtra("Seating");
        String Maxspeed = getIntent().getStringExtra("Max Speed");

        carTransmission = findViewById(R.id.car_detail_transmission);
        carSeating = findViewById(R.id.car_detail_seating);
        carFuelType = findViewById(R.id.car_detail_fuelType);

        carTransmission.setText(Transmission);
        carFuelType.setText(FuelType);
        carSeating.setText(String.valueOf(Seating));
        carMaxSpeed.setText(Maxspeed);
        //

        // SHARED PREFERENCES DATA STORAGE
        editorCD.putString("CarName", carName);
        editorCD.apply();

        Double petrolPricePerDay = getIntent().getDoubleExtra("Petrol Price", 0);
        Double dieselPricePerDay = getIntent().getDoubleExtra("Diesel Price", 0);

        double PetrolPrices[] = calculateRentalPricing(petrolPricePerDay);
        double DieselPrices[] = calculateRentalPricing(dieselPricePerDay);

// For Petrol Prices
        petrolButton.setOnClickListener(view -> {
            petrolSelect = true;
            dieselSelect = false;
            petrolButton.setBackgroundResource(R.drawable.car_detail_spec_bg_selector);
            dieselButton.setBackgroundResource(R.drawable.car_detailspec_bg);

            perDayPrice.setText(" ₹" + String.format("%.0f", PetrolPrices[0]) + "\n Day");
            perWeekPrice.setText(" ₹" + String.format("%.0f", PetrolPrices[1]) + "\n Week");
            perMonthPrice.setText(" ₹" + String.format("%.0f", PetrolPrices[2]) + "\n Month");

            // Plan 1 - Day
            plan1.setOnClickListener(view1 -> {
                selectedPrice = PetrolPrices[0];
                days = 1.0;
                highlightPlan(plan1, Clock1);;
            });

            // Plan 2 - Week
            plan2.setOnClickListener(view1 -> {
                selectedPrice = PetrolPrices[1];
                days = 7.0;
                highlightPlan(plan2, Clock2);
            });

            // Plan 3 - Month
            plan3.setOnClickListener(view1 -> {
                selectedPrice = PetrolPrices[2];
                days = 30.0;
                highlightPlan(plan3, Clock3);
            });

            plan4.setOnClickListener(view1 -> {
                selectedPrice = PetrolPrices[0];
                days = -1.0;
                highlightPlan(plan4, Clock4);
            });
        });

// For Diesel Prices
        dieselButton.setOnClickListener(view -> {
            petrolSelect = false;
            dieselSelect = true;
            dieselButton.setBackgroundResource(R.drawable.car_detail_spec_bg_selector);
            petrolButton.setBackgroundResource(R.drawable.car_detailspec_bg);

            perDayPrice.setText(" ₹" + String.format("%.0f", DieselPrices[0]) + "\n Day");
            perWeekPrice.setText(" ₹" + String.format("%.0f", DieselPrices[1]) + "\n Week");
            perMonthPrice.setText(" ₹" + String.format("%.0f", DieselPrices[2]) + "\n Month");

            // Plan 1 - Day
            plan1.setOnClickListener(view1 -> {
                selectedPrice = DieselPrices[0];
                days = 1.0;
                highlightPlan(plan1, Clock1);
            });

            // Plan 2 - Week
            plan2.setOnClickListener(view1 -> {
                selectedPrice = DieselPrices[1];
                days = 7.0;
                highlightPlan(plan2, Clock2);
            });

            // Plan 3 - Month
            plan3.setOnClickListener(view1 -> {
                selectedPrice = DieselPrices[2];
                days = 30.0;
                highlightPlan(plan3, Clock3);
            });

            plan4.setOnClickListener(view1 -> {
                selectedPrice = DieselPrices[0];
                days = -1.0;
                highlightPlan(plan4, Clock4);
            });
        });

// Send selected price on pickup button click
        pickup.setOnClickListener(view -> {
            if (selectedPrice != 0.0 && days != 0.0) {
                Intent intent = new Intent(Car_Details.this, Booking_page.class);
                intent.putExtra("selectedPrice", selectedPrice);
                intent.putExtra("days", days);
                startActivity(intent);
            } else {
                Toast.makeText(Car_Details.this, "Please select a plan first!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Highlight the selected plan visually
    private void highlightPlan(CardView selectedPlan, ImageView selectedClock) {
        plan1.setBackgroundResource(R.drawable.rentplans_default_background);
        plan2.setBackgroundResource(R.drawable.rentplans_default_background);
        plan3.setBackgroundResource(R.drawable.rentplans_default_background);
        plan4.setBackgroundResource(R.drawable.rentplans_default_background);
        selectedPlan.setBackgroundResource(R.drawable.rentplans_selected_background);
        Clock1.setColorFilter(Color.parseColor("black"), PorterDuff.Mode.SRC_IN);
        Clock2.setColorFilter(Color.parseColor("black"), PorterDuff.Mode.SRC_IN);
        Clock3.setColorFilter(Color.parseColor("black"), PorterDuff.Mode.SRC_IN);
        Clock4.setColorFilter(Color.parseColor("black"), PorterDuff.Mode.SRC_IN);
        selectedClock.setColorFilter(Color.parseColor("blue"), PorterDuff.Mode.SRC_IN);
    }

    private double[] calculateRentalPricing(double dailyPrice) {
        double weeklyPrice = (dailyPrice * 7) * 0.85; // 15% discount
        double monthlyPrice = (dailyPrice * 30) * 0.75; // 25% discount

        return new double[]{dailyPrice, weeklyPrice, monthlyPrice}; // Returning all prices
    }
}

/*
        OLD CODE

        petrolPlans = findViewById(R.id.petrolPlans);
        dieselPlans = findViewById(R.id.dieselPlans);
        hybridPlans = findViewById(R.id.hybridPlans);

        PetrolPriceDay = findViewById(R.id.petrolPriceDay);
        PetrolPriceWeek = findViewById(R.id.petrolPriceWeek);
        PetrolPriceMonth = findViewById(R.id.petrolPriceMonth);
        DieselPriceDay = findViewById(R.id.dieselPriceDay);
        DieselPriceWeek = findViewById(R.id.dieselPriceWeek);
        DieselPriceMonth = findViewById(R.id.dieselPriceMonth);

        customPetrol = findViewById(R.id.customPetrol);
        customDiesel = findViewById(R.id.customDiesel);

        PetrolPriceDay.setText("Per Day: ₹" + String.format("%.0f", PetrolPrices[0]));
        PetrolPriceWeek.setText("One Week: ₹" + String.format("%.0f", PetrolPrices[1]));
        PetrolPriceMonth.setText("A Month: ₹" + String.format("%.0f", PetrolPrices[2]));

        DieselPriceDay.setText("Per Day: ₹" + String.format("%.0f", DieselPrices[0]));
        DieselPriceWeek.setText("One Week: ₹" + String.format("%.0f", DieselPrices[1]));
        DieselPriceMonth.setText("A Month: ₹" + String.format("%.0f", DieselPrices[2]));

        petrolButton.setOnClickListener(v -> {
            petrolPlans.setVisibility(View.VISIBLE);
            dieselPlans.setVisibility(View.GONE);
            hybridPlans.setVisibility(View.GONE);
            petrolButton.setBackgroundResource(R.drawable.car_detail_spec_bg_selector);
            dieselButton.setBackgroundResource(R.drawable.car_detailspec_bg);
            hybridButton.setBackgroundResource(R.drawable.car_detailspec_bg);

            customPetrol.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Car_Details.this, Booking_page.class);
                    i.putExtra("petrolPricePerDay", petrolPricePerDay);
                    startActivity(i);
                }
            });
        });

        dieselButton.setOnClickListener(v -> {
            petrolPlans.setVisibility(View.GONE);
            dieselPlans.setVisibility(View.VISIBLE);
            hybridPlans.setVisibility(View.GONE);
            dieselButton.setBackgroundResource(R.drawable.car_detail_spec_bg_selector);
            petrolButton.setBackgroundResource(R.drawable.car_detailspec_bg);
            hybridButton.setBackgroundResource(R.drawable.car_detailspec_bg);

            customDiesel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Car_Details.this, Booking_page.class);
                    i.putExtra("dieselPricePerDay", dieselPricePerDay);
                    startActivity(i);
                }
            });
        });

        hybridButton.setOnClickListener(v -> {
            petrolPlans.setVisibility(View.GONE);
            dieselPlans.setVisibility(View.GONE);
            hybridPlans.setVisibility(View.VISIBLE);
            dieselButton.setBackgroundResource(R.drawable.car_detailspec_bg);
            petrolButton.setBackgroundResource(R.drawable.car_detailspec_bg);
            hybridButton.setBackgroundResource(R.drawable.car_detail_spec_bg_selector);
        });

*/


/*
// PETROL PRICES
        petrolButton.setOnClickListener(view -> {
            petrolSelect = Boolean.TRUE;
            petrolButton.setBackgroundResource(R.drawable.car_detail_spec_bg_selector);
            dieselButton.setBackgroundResource(R.drawable.car_detailspec_bg);
            hybridButton.setBackgroundResource(R.drawable.car_detailspec_bg);

            if (petrolSelect == Boolean.TRUE) {
                perDayPrice.setText(" ₹" + String.format("%.0f", PetrolPrices[0]) + "\n Day");
                plan1.setOnClickListener(view1 -> {

                    plan1.setBackgroundResource(R.drawable.rentplans_selected_background);
                    perDayClock.setColorFilter(Color.parseColor("blue"), PorterDuff.Mode.SRC_IN);
                    perWeekClock.setColorFilter(Color.parseColor("black"), PorterDuff.Mode.SRC_IN);
                    perMonthClock.setColorFilter(Color.parseColor("black"), PorterDuff.Mode.SRC_IN);
                    customClock.setColorFilter(Color.parseColor("black"), PorterDuff.Mode.SRC_IN);

                    plan2.setBackgroundResource(R.drawable.rentplans_default_background);
                    plan3.setBackgroundResource(R.drawable.rentplans_default_background);
                    plan4.setBackgroundResource(R.drawable.rentplans_default_background);
                });

                perWeekPrice.setText(" ₹" + String.format("%.0f", PetrolPrices[1]) + "\n Week");
                plan2.setOnClickListener(view1 -> {
                    plan2.setBackgroundResource(R.drawable.rentplans_selected_background);

                    perWeekClock.setColorFilter(Color.parseColor("blue"), PorterDuff.Mode.SRC_IN);
                    perDayClock.setColorFilter(Color.parseColor("black"), PorterDuff.Mode.SRC_IN);
                    perMonthClock.setColorFilter(Color.parseColor("black"), PorterDuff.Mode.SRC_IN);
                    customClock.setColorFilter(Color.parseColor("black"), PorterDuff.Mode.SRC_IN);

                    plan1.setBackgroundResource(R.drawable.rentplans_default_background);
                    plan3.setBackgroundResource(R.drawable.rentplans_default_background);
                    plan4.setBackgroundResource(R.drawable.rentplans_default_background);
                });

                perMonthPrice.setText(" ₹" + String.format("%.0f", PetrolPrices[2]) + "\n Month");
                plan3.setOnClickListener(view1 -> {
                    plan3.setBackgroundResource(R.drawable.rentplans_selected_background);

                    perMonthClock.setColorFilter(Color.parseColor("blue"), PorterDuff.Mode.SRC_IN);
                    perWeekClock.setColorFilter(Color.parseColor("black"), PorterDuff.Mode.SRC_IN);
                    perDayClock.setColorFilter(Color.parseColor("black"), PorterDuff.Mode.SRC_IN);
                    customClock.setColorFilter(Color.parseColor("black"), PorterDuff.Mode.SRC_IN);

                    plan2.setBackgroundResource(R.drawable.rentplans_default_background);
                    plan1.setBackgroundResource(R.drawable.rentplans_default_background);
                    plan4.setBackgroundResource(R.drawable.rentplans_default_background);
                });

                customPrice.setOnClickListener(v -> {
                    Intent i = new Intent(Car_Details.this, Booking_page.class);
                    i.putExtra("petrolPricePerDay", petrolPricePerDay);
                    startActivity(i);
                });
            }
        });

// PETROL PRICES

// DIESEL PRICES
        dieselButton.setOnClickListener(view -> {
            petrolSelect = Boolean.FALSE;
            dieselSelect = Boolean.TRUE;

            dieselButton.setBackgroundResource(R.drawable.car_detail_spec_bg_selector);
            petrolButton.setBackgroundResource(R.drawable.car_detailspec_bg);
            hybridButton.setBackgroundResource(R.drawable.car_detailspec_bg);

            if (dieselSelect == Boolean.TRUE) {
                perDayPrice.setText(" ₹" + String.format("%.0f", DieselPrices[0]) + "\n Day");
                plan1.setOnClickListener(view1 -> {

                    plan1.setBackgroundResource(R.drawable.rentplans_selected_background);
                    perDayClock.setColorFilter(Color.parseColor("blue"), PorterDuff.Mode.SRC_IN);
                    perWeekClock.setColorFilter(Color.parseColor("black"), PorterDuff.Mode.SRC_IN);
                    perMonthClock.setColorFilter(Color.parseColor("black"), PorterDuff.Mode.SRC_IN);
                    customClock.setColorFilter(Color.parseColor("black"), PorterDuff.Mode.SRC_IN);

                    plan2.setBackgroundResource(R.drawable.rentplans_default_background);
                    plan3.setBackgroundResource(R.drawable.rentplans_default_background);
                    plan4.setBackgroundResource(R.drawable.rentplans_default_background);
                });

                perWeekPrice.setText(" ₹" + String.format("%.0f", DieselPrices[1]) + "\n Week");
                plan2.setOnClickListener(view1 -> {
                    plan2.setBackgroundResource(R.drawable.rentplans_selected_background);

                    perWeekClock.setColorFilter(Color.parseColor("blue"), PorterDuff.Mode.SRC_IN);
                    perDayClock.setColorFilter(Color.parseColor("black"), PorterDuff.Mode.SRC_IN);
                    perMonthClock.setColorFilter(Color.parseColor("black"), PorterDuff.Mode.SRC_IN);
                    customClock.setColorFilter(Color.parseColor("black"), PorterDuff.Mode.SRC_IN);

                    plan1.setBackgroundResource(R.drawable.rentplans_default_background);
                    plan3.setBackgroundResource(R.drawable.rentplans_default_background);
                    plan4.setBackgroundResource(R.drawable.rentplans_default_background);
                });

                perMonthPrice.setText(" ₹" + String.format("%.0f", DieselPrices[2]) + "\n Month");
                plan3.setOnClickListener(view1 -> {
                    plan3.setBackgroundResource(R.drawable.rentplans_selected_background);

                    perMonthClock.setColorFilter(Color.parseColor("blue"), PorterDuff.Mode.SRC_IN);
                    perWeekClock.setColorFilter(Color.parseColor("black"), PorterDuff.Mode.SRC_IN);
                    perDayClock.setColorFilter(Color.parseColor("black"), PorterDuff.Mode.SRC_IN);
                    customClock.setColorFilter(Color.parseColor("black"), PorterDuff.Mode.SRC_IN);

                    plan2.setBackgroundResource(R.drawable.rentplans_default_background);
                    plan1.setBackgroundResource(R.drawable.rentplans_default_background);
                    plan4.setBackgroundResource(R.drawable.rentplans_default_background);
                });

                customPrice.setOnClickListener(v -> {
                    Intent i = new Intent(Car_Details.this, Booking_page.class);
                    i.putExtra("dieselPricePerDay", dieselPricePerDay);
                    startActivity(i);
                });
            }
        });

// DIESEL PRICES

        // Back button listener
        backToLineup.setOnClickListener(view -> {
            Intent intent = new Intent(Car_Details.this, BrandLineup_page.class);
            startActivity(intent);
            finish();
        });


        pickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Car_Details.this, Booking_page.class);
                startActivity(i);
            }
        });
*/