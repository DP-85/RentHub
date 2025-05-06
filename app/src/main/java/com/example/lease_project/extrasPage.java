package com.example.lease_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.*;

public class extrasPage extends AppCompatActivity {

    Button clearAll, confirmationPage;
    ConstraintLayout extra1, extra2, extra3, extra4, extra5;
    boolean isSelected1 = false, isSelected2 = false, isSelected3 = false, isSelected4 = false, isSelected5 = false;
    ImageButton homeButton, backToDetails;
    String pickupdate, returndate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_extras_page);

        // Get pickup and return date from previous activity
        pickupdate = getIntent().getStringExtra("pickupDate");
        returndate = getIntent().getStringExtra("returnDate");
        String BrandName = getIntent().getStringExtra("brandName");

        extra1 = findViewById(R.id.layout1);
        extra2 = findViewById(R.id.layout2);
        extra3 = findViewById(R.id.layout3);
        extra4 = findViewById(R.id.layout4);
        extra5 = findViewById(R.id.layout5);

        clearAll = findViewById(R.id.clearButton);
        confirmationPage = findViewById(R.id.proceedBtn);
        homeButton = findViewById(R.id.homeButton);
        backToDetails = findViewById(R.id.backToDetails);

        setLayoutClickListener(extra1, 1);
        setLayoutClickListener(extra2, 2);
        setLayoutClickListener(extra3, 3);
        setLayoutClickListener(extra4, 4);
        setLayoutClickListener(extra5, 5);

        clearAll.setOnClickListener(v -> clearAllSelections());

        confirmationPage.setOnClickListener(view -> {
            Intent i = new Intent(extrasPage.this, Confirmation_page.class);

            // Send pickup and return dates
            i.putExtra("pickupDate", pickupdate);
            i.putExtra("returnDate", returndate);
            i.putExtra("brandName", BrandName);

            // Send selected extras and their prices
            if (isSelected1) i.putExtra("Extra1", 200);
            if (isSelected2) i.putExtra("Extra2", 700);
            if (isSelected3) i.putExtra("Extra3", 400);
            if (isSelected4) i.putExtra("Extra4", 1400);
            if (isSelected5) i.putExtra("Extra5", 1400);

            startActivity(i);
        });

        backToDetails.setOnClickListener(view -> {
            Intent intent = new Intent(extrasPage.this, Booking_page.class);
            intent.putExtra("pickupDate", getIntent().getStringExtra("pickupDate"));
            intent.putExtra("returnDate", getIntent().getStringExtra("returnDate"));
            intent.putExtra("selectedPrice", getIntent().getDoubleExtra("selectedPrice", 0.0));
            intent.putExtra("days", getIntent().getDoubleExtra("days", 0.0));
            intent.putExtra("carName", getIntent().getStringExtra("carName"));
            intent.putExtra("brandName", getIntent().getStringExtra("brandName"));
            startActivity(intent);
            finish();
        });

        homeButton.setOnClickListener(view -> {
            Intent i = new Intent(extrasPage.this, home_page.class);
            startActivity(i);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setLayoutClickListener(ConstraintLayout layout, int index) {
        layout.setOnClickListener(v -> {
            switch (index) {
                case 1:
                    isSelected1 = !isSelected1;
                    layout.setBackgroundResource(isSelected1 ? R.drawable.extras_page_selected_background : R.drawable.extras_page_background);
                    break;
                case 2:
                    isSelected2 = !isSelected2;
                    layout.setBackgroundResource(isSelected2 ? R.drawable.extras_page_selected_background : R.drawable.extras_page_background);
                    break;
                case 3:
                    isSelected3 = !isSelected3;
                    layout.setBackgroundResource(isSelected3 ? R.drawable.extras_page_selected_background : R.drawable.extras_page_background);
                    break;
                case 4:
                    isSelected4 = !isSelected4;
                    layout.setBackgroundResource(isSelected4 ? R.drawable.extras_page_selected_background : R.drawable.extras_page_background);
                    break;
                case 5:
                    isSelected5 = !isSelected5;
                    layout.setBackgroundResource(isSelected5 ? R.drawable.extras_page_selected_background : R.drawable.extras_page_background);
                    break;
            }
        });
    }

    private void clearAllSelections() {
        isSelected1 = false;
        isSelected2 = false;
        isSelected3 = false;
        isSelected4 = false;
        isSelected5 = false;

        extra1.setBackgroundResource(R.drawable.extras_page_background);
        extra2.setBackgroundResource(R.drawable.extras_page_background);
        extra3.setBackgroundResource(R.drawable.extras_page_background);
        extra4.setBackgroundResource(R.drawable.extras_page_background);
        extra5.setBackgroundResource(R.drawable.extras_page_background);
    }
}
