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

            payButton.setOnClickListener(v -> {
                UpiBottomSheet upibottomSheet = new UpiBottomSheet();
                upibottomSheet.show(getSupportFragmentManager(), "UpiBottomSheet");
            });
        });

        installments.setOnClickListener(view -> {
            highlightpayment(installments, "Installments");
            payButton.setText("Pay in installments");
        });

        cod.setOnClickListener(view -> {
            highlightpayment(cod, "COD");
            payButton.setText("Pay with COD");

            payButton.setOnClickListener(v -> {
                Intent i = new Intent(Confirmation_page.this, Final_page.class);
                startActivity(i);
            });
        });

        // Storing brand name (Make sure this runs before trying to retrieve)
        SharedPreferences sharedPreferences = getSharedPreferences("OrderSummaryBL", MODE_PRIVATE);
        SharedPreferences.Editor editorBL = sharedPreferences.edit();

        String BrandName = sharedPreferences.getString("BrandName", "N/A");

        SharedPreferences sharedPreferences2 = getSharedPreferences("OrderSummaryBP", MODE_PRIVATE);
        String Days = sharedPreferences2.getString("Days", "N/A");
        String Price = sharedPreferences2.getString("Price", "N/A");

        SharedPreferences sharedPreferences3 = getSharedPreferences("OrderSummaryCD", MODE_PRIVATE);
        String carName = sharedPreferences3.getString("CarName", "N/A");

        SharedPreferences sharedPreferences4 = getSharedPreferences("OrderSummaryEP", MODE_PRIVATE);
        String extra1 = sharedPreferences4.getString("Extra1", "None");
        String extra2 = sharedPreferences4.getString("Extra2", "None");
        String extra3 = sharedPreferences4.getString("Extra3", "None");
        String extra4 = sharedPreferences4.getString("Extra4", "None");
        String extra5 = sharedPreferences4.getString("Extra5", "None");

// Combine selected extras into one string
        String Chauffeur = "";
        String BoosterSeat = "";
        String TyreInflator = "";
        String TopBox = "";
        String Organizers = "";
        if (!extra1.isEmpty()) Chauffeur += extra1 + "\n";
        if (!extra2.isEmpty()) BoosterSeat += extra2 + "\n";
        if (!extra3.isEmpty()) TyreInflator += extra3 + "\n";
        if (!extra4.isEmpty()) TopBox += extra4 + "\n";
        if (!extra5.isEmpty()) Organizers += extra5 + "\n";

        String pickupdate = getIntent().getStringExtra("pickupDate");
        String returndate = getIntent().getStringExtra("returnDate");

        // Calculate total extras
        int extrasTotal = 0;
        try {
            if (extra1 != null && !extra1.equals("None")) extrasTotal += Integer.parseInt(extra1);
            if (extra2 != null && !extra2.equals("None")) extrasTotal += Integer.parseInt(extra2);
            if (extra3 != null && !extra3.equals("None")) extrasTotal += Integer.parseInt(extra3);
            if (extra4 != null && !extra4.equals("None")) extrasTotal += Integer.parseInt(extra4);
            if (extra5 != null && !extra5.equals("None")) extrasTotal += Integer.parseInt(extra5);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

// Convert base price to integer
        int basePrice = 0;
        try {
            basePrice = Integer.parseInt(Price);
        } catch (Exception e) {
            e.printStackTrace();
        }

// Total Price = Base + Extras
        int finalTotal = basePrice + extrasTotal;

// Show in TextView
        totalPrice.setText("Total Amount : " + finalTotal + " ₹");


// Show in TextView
        totalPrice.setText("Total Amount : " + finalTotal + " ₹");


// Set text in TextView

        pickupDate.setText(pickupdate);
        returnDate.setText(returndate);
        brand_name.setText(" Brand                 : " + BrandName);
        car_name.setText(" Car                      : " + carName);
        total_days.setText(" Total Days          : " + Days);
        base_price.setText(" Base Price          : " + Price);
        chauffeur.setText(" Cheuffeur          : " + Chauffeur);
        boosterSeat.setText(" Booster Seat    : " + BoosterSeat);
        tyreInflator.setText(" Tyre Inflator : " + TyreInflator);
        topBox.setText(" Top Box : " + TopBox);
        organizers.setText(" Organizers : " + Organizers);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        homeButton.setOnClickListener(view -> {
            Intent i = new Intent(Confirmation_page.this, home_page.class);
            startActivity(i);
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