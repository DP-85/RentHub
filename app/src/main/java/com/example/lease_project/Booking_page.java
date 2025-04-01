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

import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import android.content.SharedPreferences;

public class Booking_page extends AppCompatActivity {
    Button pickupDate, returnDate, pickupButton;
    CalendarView pickupCalendar;
    TextView calendarText, cost, pickupDateText, returnDateText, dayscount;
    long bookingDate = -1, returningDate = -1; // Store selected dates
    boolean isSelectingPickup = true; // Toggle between pickup & return selection
    double totalCost, petrolPricePerDay, dieselPricePerDay, selectedPrice, days = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booking_page);

        SharedPreferences sharedPreferences = getSharedPreferences("OrderSummaryBP", MODE_PRIVATE);
        SharedPreferences.Editor editorBP = sharedPreferences.edit();

        pickupDate = findViewById(R.id.pickupDate);
        returnDate = findViewById(R.id.returnDate);
        pickupCalendar = findViewById(R.id.pickupCalendar);
        calendarText = findViewById(R.id.calendarText);
        pickupButton = findViewById(R.id.pickupButton);
        pickupDateText = findViewById(R.id.pickupDateText);
        returnDateText = findViewById(R.id.returnDateText);
        dayscount = findViewById(R.id.daysCount);
        cost = findViewById(R.id.cost);

        petrolPricePerDay = getIntent().getDoubleExtra("petrolPricePerDay", -1);
        dieselPricePerDay = getIntent().getDoubleExtra("dieselPricePerDay", -1);

        days = getIntent().getDoubleExtra("days", -1);
        selectedPrice = getIntent().getDoubleExtra("selectedPrice", -1);

        // Get today's date and set minDate for pickup
        Calendar today = Calendar.getInstance();
        today.add(Calendar.DAY_OF_MONTH, 1); // Pickup can't be today or before
        pickupCalendar.setMinDate(today.getTimeInMillis());

        // Pickup Date Selection
        pickupDate.setOnClickListener(v -> {
            pickupDate.setBackgroundResource(R.drawable.rentplans_selected_background);
            returnDate.setBackgroundResource(R.drawable.rentplans_default_background);
            isSelectingPickup = true;
            pickupCalendar.setVisibility(View.VISIBLE);
        });

        // Return Date Selection
        returnDate.setOnClickListener(v -> {
            returnDate.setBackgroundResource(R.drawable.rentplans_selected_background);
            pickupDate.setBackgroundResource(R.drawable.rentplans_default_background);
            if (bookingDate == -1) {
                Toast.makeText(this, "Select Pickup Date first!", Toast.LENGTH_SHORT).show();
                return;
            }
            isSelectingPickup = false;
            pickupCalendar.setMinDate(bookingDate + 86400000); // Return must be after pickup
            //pickupCalendar.setVisibility(View.VISIBLE);
        });

        // Handle Date Selection

        pickupCalendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            long selectedDate = getDateInMillis(year, month, dayOfMonth);

            if (isSelectingPickup) {
                bookingDate = selectedDate;
                returningDate = -1; // Reset return date
                pickupDateText.setText("Pickup: " + dayOfMonth + "/" + (month + 1) + "/" + year);
                dayscount.setText(""); // Clear previous count
                Toast.makeText(this, "Pickup Date Selected", Toast.LENGTH_SHORT).show();

                if (days == 1 || days == 7 || days == 30) {
                    // Fixed days scenario
                    Calendar returnCalendar = Calendar.getInstance();
                    returnCalendar.set(year, month, dayOfMonth);
                    returnCalendar.add(Calendar.DAY_OF_MONTH, (int) days); // Add the specified days
                    returningDate = returnCalendar.getTimeInMillis();
                    int returnDay = returnCalendar.get(Calendar.DAY_OF_MONTH);
                    int returnMonth = returnCalendar.get(Calendar.MONTH) + 1; // Month is zero-based
                    int returnYear = returnCalendar.get(Calendar.YEAR);
                    returnDateText.setText("Return: " + returnDay + "/" + returnMonth + "/" + returnYear);

                    dayscount.setText("Days: " + (int) days);

                    editorBP.putString("Days", dayscount.getText().toString());
                    editorBP.apply();

                    // Calculate the total cost based on selectedPrice if available
                    if (selectedPrice != -1) {
                        totalCost = selectedPrice * days;
                    } else if (petrolPricePerDay != -1) {
                        totalCost = petrolPricePerDay * days;
                    } else {
                        totalCost = dieselPricePerDay * days;
                    }

                    cost.setText("Total Price: " + totalCost);

                    editorBP.putString("Price", cost.getText().toString());
                    editorBP.apply();

                } else {
                    // Custom days scenario (days == -1)
                    Toast.makeText(this, "Select a return date to calculate cost", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Handling return date selection for custom days
                if (selectedDate <= bookingDate) {
                    Toast.makeText(this, "Return date must be after pickup!", Toast.LENGTH_SHORT).show();
                } else {
                    returningDate = selectedDate;
                    returnDateText.setText("Return: " + dayOfMonth + "/" + (month + 1) + "/" + year);

                    double diffInMillis = returningDate - bookingDate;
                    double daysBetween = (int) (diffInMillis / (1000.0 * 60 * 60 * 24)); // Convert milliseconds to days
                    dayscount.setText("Days: " + daysBetween);

                    editorBP.putString("Days", dayscount.getText().toString());
                    editorBP.apply();

                    if (selectedPrice != -1) {
                        totalCost = selectedPrice * daysBetween;
                    } else if (petrolPricePerDay != -1) {
                        totalCost = petrolPricePerDay * daysBetween;
                    } else {
                        totalCost = dieselPricePerDay * daysBetween;
                    }

                    cost.setText("Total Price: " + totalCost);

                    editorBP.putString("Price", cost.getText().toString());
                    editorBP.apply();
                }
            }
        });

        pickupButton.setOnClickListener(view -> {
            if (bookingDate == -1 || returningDate == -1) {
                Toast.makeText(this, "Please select both pickup and return dates", Toast.LENGTH_SHORT).show();
            } else {
                Intent i = new Intent(Booking_page.this, extrasPage.class);
                startActivity(i);
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
    private long getDateInMillis(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        return calendar.getTimeInMillis();
    }
}

/*
        pickupCalendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            long selectedDate = getDateInMillis(year, month, dayOfMonth);

            if (isSelectingPickup) {
                bookingDate = selectedDate;
                returningDate = -1; // Reset return date
                pickupDateText.setText("Pickup: " + dayOfMonth + "/" + (month + 1) + "/" + year);
                dayscount.setText(""); // Clear previous count
                Toast.makeText(this, "Pickup Date Selected", Toast.LENGTH_SHORT).show();
            } else {
                if (selectedDate <= bookingDate) {
                    Toast.makeText(this, "Return date must be after pickup!", Toast.LENGTH_SHORT).show();
                } else {
                    returningDate = selectedDate;
                    returnDateText.setText("Return: " + dayOfMonth + "/" + (month + 1) + "/" + year);
                    double diffInMillis = returningDate - bookingDate;
                    double daysBetween = (int) diffInMillis / (1000.0 * 60 * 60 * 24); // Convert milliseconds to days
                    dayscount.setText("Days: " + daysBetween);
                    Toast.makeText(this, "Return Date Selected", Toast.LENGTH_SHORT).show();

                    if(petrolPricePerDay != -1) {
                        totalCost = petrolPricePerDay * daysBetween;
                        cost.setText("Total Price: " + totalCost);
                    }
                    else {
                        totalCost = dieselPricePerDay * daysBetween;
                        cost.setText("Total Price: " + totalCost);
                    }
                }
            }
            //pickupCalendar.setVisibility(View.GONE);
        });
*/