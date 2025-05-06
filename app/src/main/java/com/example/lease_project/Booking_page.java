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

    long bookingDate = -1, returningDate = -1;
    boolean isSelectingPickup = true;

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

        Calendar today = Calendar.getInstance();
        today.add(Calendar.DAY_OF_MONTH, 1);
        pickupCalendar.setMinDate(today.getTimeInMillis());

        String previousPickup = getIntent().getStringExtra("pickupDate");
        String previousReturn = getIntent().getStringExtra("returnDate");

        if (previousPickup != null && previousReturn != null) {
            pickupDateText.setText("Pickup: " + previousPickup);
            returnDateText.setText("Return: " + previousReturn);
        }

        pickupDate.setOnClickListener(v -> {
            pickupDate.setBackgroundResource(R.drawable.rentplans_selected_background);
            returnDate.setBackgroundResource(R.drawable.rentplans_default_background);
            isSelectingPickup = true;
            pickupCalendar.setVisibility(View.VISIBLE);
        });

        returnDate.setOnClickListener(v -> {
            returnDate.setBackgroundResource(R.drawable.rentplans_selected_background);
            pickupDate.setBackgroundResource(R.drawable.rentplans_default_background);

            if (bookingDate == -1) {
                Toast.makeText(this, "Select Pickup Date first!", Toast.LENGTH_SHORT).show();
                return;
            }

            isSelectingPickup = false;
            pickupCalendar.setMinDate(bookingDate + 86400000);
        });

        pickupCalendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            long selectedDate = getDateInMillis(year, month, dayOfMonth);

            if (isSelectingPickup) {
                bookingDate = selectedDate;
                returningDate = -1;
                pickupDateText.setText("Pickup: " + dayOfMonth + "/" + (month + 1) + "/" + year);
                dayscount.setText("");
                Toast.makeText(this, "Pickup Date Selected", Toast.LENGTH_SHORT).show();

                if (days == 1 || days == 7 || days == 30) {
                    Calendar returnCalendar = Calendar.getInstance();
                    returnCalendar.set(year, month, dayOfMonth);
                    returnCalendar.add(Calendar.DAY_OF_MONTH, (int) days);
                    returningDate = returnCalendar.getTimeInMillis();

                    int returnDay = returnCalendar.get(Calendar.DAY_OF_MONTH);
                    int returnMonth = returnCalendar.get(Calendar.MONTH) + 1;
                    int returnYear = returnCalendar.get(Calendar.YEAR);
                    returnDateText.setText("Return: " + returnDay + "/" + returnMonth + "/" + returnYear);

                    dayscount.setText("Days: " + (int) days);
                    editorBP.putString("Days", dayscount.getText().toString());
                    editorBP.apply();

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
                    Toast.makeText(this, "Select a return date to calculate cost", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (selectedDate <= bookingDate) {
                    Toast.makeText(this, "Return date must be after pickup!", Toast.LENGTH_SHORT).show();
                } else {
                    returningDate = selectedDate;
                    returnDateText.setText("Return: " + dayOfMonth + "/" + (month + 1) + "/" + year);

                    double diffInMillis = returningDate - bookingDate;
                    double daysBetween = (int) (diffInMillis / (1000.0 * 60 * 60 * 24));
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

                String pickupRaw = pickupDateText.getText().toString().replace("Pickup: ", "");
                String returnRaw = returnDateText.getText().toString().replace("Return: ", "");

                String[] pickupParts = pickupRaw.split("/");
                String[] returnParts = returnRaw.split("/");

                String[] monthNames = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
                        "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};

                String pickupFormatted = pickupParts[0] + " " + monthNames[Integer.parseInt(pickupParts[1]) - 1] + " " + pickupParts[2];
                String returnFormatted = returnParts[0] + " " + monthNames[Integer.parseInt(returnParts[1]) - 1] + " " + returnParts[2];

                i.putExtra("pickupDate", pickupFormatted);
                i.putExtra("returnDate", returnFormatted);

                i.putExtra("selectedPrice", selectedPrice);
                i.putExtra("days", days);
                i.putExtra("carName", getIntent().getStringExtra("carName"));
                i.putExtra("brandName", getIntent().getStringExtra("brandName"));

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

    // UI elements (buttons, textviews, etc.)
    Button pickupDate, returnDate, pickupButton;
    CalendarView pickupCalendar;
    TextView calendarText, cost, pickupDateText, returnDateText, dayscount;

    // Variables to store the selected pickup and return dates (in milliseconds)
    long bookingDate = -1, returningDate = -1;

    // Boolean to track whether the user is picking pickup or return date
    boolean isSelectingPickup = true;

    // Variables for rental cost calculation
    double totalCost, petrolPricePerDay, dieselPricePerDay, selectedPrice, days = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Makes layout draw edge-to-edge
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booking_page);

        // Prepare SharedPreferences for storing order summary
        SharedPreferences sharedPreferences = getSharedPreferences("OrderSummaryBP", MODE_PRIVATE);
        SharedPreferences.Editor editorBP = sharedPreferences.edit();

        // Link UI elements
        pickupDate = findViewById(R.id.pickupDate);
        returnDate = findViewById(R.id.returnDate);
        pickupCalendar = findViewById(R.id.pickupCalendar);
        calendarText = findViewById(R.id.calendarText);
        pickupButton = findViewById(R.id.pickupButton);
        pickupDateText = findViewById(R.id.pickupDateText);
        returnDateText = findViewById(R.id.returnDateText);
        dayscount = findViewById(R.id.daysCount);
        cost = findViewById(R.id.cost);

        // Get the pricing and days info passed from the previous screen
        petrolPricePerDay = getIntent().getDoubleExtra("petrolPricePerDay", -1);
        dieselPricePerDay = getIntent().getDoubleExtra("dieselPricePerDay", -1);
        days = getIntent().getDoubleExtra("days", -1);
        selectedPrice = getIntent().getDoubleExtra("selectedPrice", -1);

        // Allow only tomorrow or later to be picked on calendar
        Calendar today = Calendar.getInstance();
        today.add(Calendar.DAY_OF_MONTH, 1); // Add 1 day
        pickupCalendar.setMinDate(today.getTimeInMillis());

        String previousPickup = getIntent().getStringExtra("pickupDate");
        String previousReturn = getIntent().getStringExtra("returnDate");

        if (previousPickup != null && previousReturn != null) {
            pickupDateText.setText("Pickup: " + previousPickup);
            returnDateText.setText("Return: " + previousReturn);
            // Optionally parse and set the calendar state too
        }


        // When user taps on "Pickup Date" button
        pickupDate.setOnClickListener(v -> {
            pickupDate.setBackgroundResource(R.drawable.rentplans_selected_background); // Highlight pickup
            returnDate.setBackgroundResource(R.drawable.rentplans_default_background); // Reset return
            isSelectingPickup = true; // Now we are picking pickup date
            pickupCalendar.setVisibility(View.VISIBLE); // Show calendar
        });

        // When user taps on "Return Date" button
        returnDate.setOnClickListener(v -> {
            returnDate.setBackgroundResource(R.drawable.rentplans_selected_background); // Highlight return
            pickupDate.setBackgroundResource(R.drawable.rentplans_default_background); // Reset pickup

            // If user hasn't selected pickup yet, show a message
            if (bookingDate == -1) {
                Toast.makeText(this, "Select Pickup Date first!", Toast.LENGTH_SHORT).show();
                return;
            }

            isSelectingPickup = false; // Now selecting return date
            pickupCalendar.setMinDate(bookingDate + 86400000); // Return must be at least +1 day
        });

        // When user taps a date on the calendar
        pickupCalendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            long selectedDate = getDateInMillis(year, month, dayOfMonth); // Get selected date in milliseconds

            if (isSelectingPickup) {
                // Save selected pickup date
                bookingDate = selectedDate;
                returningDate = -1; // Clear any previous return
                pickupDateText.setText("Pickup: " + dayOfMonth + "/" + (month + 1) + "/" + year);
                dayscount.setText(""); // Clear old days
                Toast.makeText(this, "Pickup Date Selected", Toast.LENGTH_SHORT).show();

                // If user selected 1-day, 7-days, or 30-days rental plan
                if (days == 1 || days == 7 || days == 30) {
                    Calendar returnCalendar = Calendar.getInstance();
                    returnCalendar.set(year, month, dayOfMonth);
                    returnCalendar.add(Calendar.DAY_OF_MONTH, (int) days); // Add rental days
                    returningDate = returnCalendar.getTimeInMillis();

                    int returnDay = returnCalendar.get(Calendar.DAY_OF_MONTH);
                    int returnMonth = returnCalendar.get(Calendar.MONTH) + 1;
                    int returnYear = returnCalendar.get(Calendar.YEAR);
                    returnDateText.setText("Return: " + returnDay + "/" + returnMonth + "/" + returnYear);

                    dayscount.setText("Days: " + (int) days);

                    // Save number of days
                    editorBP.putString("Days", dayscount.getText().toString());
                    editorBP.apply();

                    // Calculate total cost
                    if (selectedPrice != -1) {
                        totalCost = selectedPrice * days;
                    } else if (petrolPricePerDay != -1) {
                        totalCost = petrolPricePerDay * days;
                    } else {
                        totalCost = dieselPricePerDay * days;
                    }

                    // Show total price
                    cost.setText("Total Price: " + totalCost);
                    editorBP.putString("Price", cost.getText().toString());
                    editorBP.apply();

                } else {
                    // If not a predefined plan
                    Toast.makeText(this, "Select a return date to calculate cost", Toast.LENGTH_SHORT).show();
                }

            } else {
                // Now user selecting return date
                if (selectedDate <= bookingDate) {
                    Toast.makeText(this, "Return date must be after pickup!", Toast.LENGTH_SHORT).show();
                } else {
                    returningDate = selectedDate;
                    returnDateText.setText("Return: " + dayOfMonth + "/" + (month + 1) + "/" + year);

                    // Calculate days between pickup and return
                    double diffInMillis = returningDate - bookingDate;
                    double daysBetween = (int) (diffInMillis / (1000.0 * 60 * 60 * 24));
                    dayscount.setText("Days: " + daysBetween);

                    editorBP.putString("Days", dayscount.getText().toString());
                    editorBP.apply();

                    // Calculate total cost
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

        // When user taps "Continue" button
        pickupButton.setOnClickListener(view -> {
            if (bookingDate == -1 || returningDate == -1) {
                Toast.makeText(this, "Please select both pickup and return dates", Toast.LENGTH_SHORT).show();
            } else {
                Intent i = new Intent(Booking_page.this, extrasPage.class);

                // Pass selected pickup and return dates
                String pickupRaw = pickupDateText.getText().toString().replace("Pickup: ", "");
                String returnRaw = returnDateText.getText().toString().replace("Return: ", "");

                String[] pickupParts = pickupRaw.split("/");
                String[] returnParts = returnRaw.split("/");

                String[] monthNames = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
                        "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};

                String pickupFormatted = pickupParts[0] + " " + monthNames[Integer.parseInt(pickupParts[1]) - 1] + " " + pickupParts[2];
                String returnFormatted = returnParts[0] + " " + monthNames[Integer.parseInt(returnParts[1]) - 1] + " " + returnParts[2];

                i.putExtra("pickupDate", pickupFormatted);
                i.putExtra("returnDate", returnFormatted);

                i.putExtra("carName", getIntent().getStringExtra("carName"));
                i.putExtra("brandName", getIntent().getStringExtra("brandName"));
                startActivity(i);
            }
        });

        // Make sure layout adjusts properly with system UI (status bar, etc.)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Helper function: Converts year, month, day into milliseconds
    private long getDateInMillis(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        return calendar.getTimeInMillis();
    }
} */
