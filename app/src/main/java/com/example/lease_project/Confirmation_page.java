package com.example.lease_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.SharedPreferences;
import android.widget.*;
import android.content.Intent;

public class Confirmation_page extends AppCompatActivity {

    TextView pickupDate, returnDate, brand_name, car_name, total_days, base_price, chauffeur, boosterSeat, organizers, tyreInflator, topBox, totalPrice;
    TextView card, upi, installments, cod;
    ImageButton homeButton;
    Button payButton;
    String selectedPaymentMethod = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_confirmation_page);

        brand_name = findViewById(R.id.brand_Name);
        car_name = findViewById(R.id.car_Name);
        total_days = findViewById(R.id.total_Days);
        base_price = findViewById(R.id.base_Price);
        chauffeur = findViewById(R.id.chauffeur);
        boosterSeat = findViewById(R.id.boosterSeat);
        organizers = findViewById(R.id.luggageRack);
        tyreInflator = findViewById(R.id.tyreInflator);
        topBox = findViewById(R.id.topBox);
        totalPrice = findViewById(R.id.total_Price);
        pickupDate = findViewById(R.id.pickingdate);
        returnDate = findViewById(R.id.returningdate);

        card = findViewById(R.id.Card);
        upi = findViewById(R.id.upi);
        installments = findViewById(R.id.installments);
        cod = findViewById(R.id.cod);

        homeButton = findViewById(R.id.homeButton);
        payButton = findViewById(R.id.paymentButton);

        // Payment button click listeners
        card.setOnClickListener(view -> {
            highlightpayment(card, "Card");
            payButton.setText("Pay with Card");
            payButton.setOnClickListener(v -> {
                CardBottomSheet bottomSheet = new CardBottomSheet();
                bottomSheet.show(getSupportFragmentManager(), "CardBottomSheet");
            });
        });

        upi.setOnClickListener(view -> {
            highlightpayment(upi, "UPI");
            payButton.setText("Pay with UPI");
            // Inside your UPI button click:
            payButton.setOnClickListener(v -> {
                String amountText = totalPrice.getText().toString().replace("Total Amount : ", "").replace("₹", "").trim();
                UpiBottomSheet upiBottomSheet = new UpiBottomSheet(amountText);
                upiBottomSheet.show(getSupportFragmentManager(), "UpiBottomSheet");
            });

        });

        installments.setOnClickListener(view -> {
            highlightpayment(installments, "Installments");
            payButton.setText("Pay in Installments");
            payButton.setOnClickListener(view1 -> {
                InstallmentsBottomSheet installmentsBottomSheet = new InstallmentsBottomSheet();
                installmentsBottomSheet.show(getSupportFragmentManager(), "InstallmentsBottomSheet");
            });
        });

        cod.setOnClickListener(view -> {
            highlightpayment(cod, "COD");
            payButton.setText("Pay with COD");
            payButton.setOnClickListener(v -> {
                Intent i = new Intent(Confirmation_page.this, Final_page.class);
                startActivity(i);
            });
        });

        // Receiving SharedPreferences for brand, car name, etc.
        SharedPreferences sharedPreferencesBL = getSharedPreferences("OrderSummaryBL", MODE_PRIVATE);
        SharedPreferences sharedPreferencesBP = getSharedPreferences("OrderSummaryBP", MODE_PRIVATE);
        SharedPreferences sharedPreferencesCD = getSharedPreferences("OrderSummaryCD", MODE_PRIVATE);

        String BrandName = getIntent().getStringExtra("brandName");
        String Days = sharedPreferencesBP.getString("Days", "N/A");
        String Price = sharedPreferencesBP.getString("Price", "N/A");
        String carName = sharedPreferencesCD.getString("CarName", "N/A");

        // Receiving dates and extras from intent
        String pickupdate = getIntent().getStringExtra("pickupDate");
        String returndate = getIntent().getStringExtra("returnDate");

        int extra1 = getIntent().getIntExtra("Extra1", 0);
        int extra2 = getIntent().getIntExtra("Extra2", 0);
        int extra3 = getIntent().getIntExtra("Extra3", 0);
        int extra4 = getIntent().getIntExtra("Extra4", 0);
        int extra5 = getIntent().getIntExtra("Extra5", 0);

        // Convert base price from SharedPreferences safely
        double basePrice = 0;
        try {
            basePrice = Double.parseDouble(Price.replaceAll("[^0-9.]", ""));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Calculate total extra cost
        int extrasTotal = extra1 + extra2 + extra3 + extra4 + extra5;

        // Calculate final total
        double finalTotal = basePrice + extrasTotal + 5000;

        // Set pickup and return dates
        pickupDate.setText(pickupdate);
        returnDate.setText(returndate);

        // Set brand, car, days and base price
        brand_name.setText(" Brand                 : " + BrandName);
        car_name.setText(" Car                      : " + carName);
        total_days.setText(" Total Days          : " + Days);
        base_price.setText(" Base Price          : " + basePrice + " ₹");

        // Set extras
        chauffeur.setText(extra1 != 0 ? " Chauffeur          : 200 ₹" : " Chauffeur          : None");
        boosterSeat.setText(extra2 != 0 ? " Booster Seat    : 700 ₹" : " Booster Seat    : None");
        tyreInflator.setText(extra3 != 0 ? " Tyre Inflator  : 400 ₹" : " Tyre Inflator : None");
        topBox.setText(extra4 != 0 ? " Top Box             : 1400 ₹" : " Top Box             : None");
        organizers.setText(extra5 != 0 ? " Organizers         : 1400 ₹" : " Organizers         : None");

        // Set final total price
        totalPrice.setText("Total Amount : " + finalTotal + " ₹");

        // Home button redirect
        homeButton.setOnClickListener(view -> {
            Intent i = new Intent(Confirmation_page.this, home_page.class);
            startActivity(i);
        });

        // Handle window inset padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void highlightpayment(TextView textView, String paymentMethod) {
        card.setBackgroundResource(R.drawable.radio_button_default);
        upi.setBackgroundResource(R.drawable.radio_button_default);
        installments.setBackgroundResource(R.drawable.radio_button_default);
        cod.setBackgroundResource(R.drawable.radio_button_default);
        textView.setBackgroundResource(R.drawable.radio_button_selector);
        selectedPaymentMethod = paymentMethod;
    }
}
