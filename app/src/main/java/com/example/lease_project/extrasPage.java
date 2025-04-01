package com.example.lease_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.*;
import android.content.SharedPreferences;
import android.content.Intent;

public class extrasPage extends AppCompatActivity {
    Button clearAll, confirmationPage;
    ConstraintLayout extra1, extra2, extra3, extra4, extra5;
    boolean isSelected1 = false, isSelected2 = false, isSelected3 = false, isSelected4 = false, isSelected5 = false;
    ImageButton homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_extras_page);

        SharedPreferences sharedPreferences = getSharedPreferences("OrderSummaryEP", MODE_PRIVATE);
        SharedPreferences.Editor editorEP = sharedPreferences.edit();

        extra1 = findViewById(R.id.layout1);
        extra2 = findViewById(R.id.layout2);
        extra3 = findViewById(R.id.layout3);
        extra4 = findViewById(R.id.layout4);
        extra5 = findViewById(R.id.layout5);

        clearAll = findViewById(R.id.clearButton);
        confirmationPage = findViewById(R.id.proceedBtn);

        homeButton = findViewById(R.id.homeButton);

        setLayoutClickListener(extra1, 1);
        setLayoutClickListener(extra2, 2);
        setLayoutClickListener(extra3, 3);
        setLayoutClickListener(extra4, 4);
        setLayoutClickListener(extra5, 5);

        clearAll.setOnClickListener(v -> clearAllSelections());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        confirmationPage.setOnClickListener(view -> {
            Intent i = new Intent(extrasPage.this, Confirmation_page.class);
            startActivity(i);
        });

        homeButton.setOnClickListener(view -> {
            Intent i = new Intent(extrasPage.this, home_page.class);
            startActivity(i);
        });
    }

    // Function for Selecting Extras
    // Function for Selecting Extras
    private void setLayoutClickListener(ConstraintLayout layout, int index) {
        layout.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("OrderSummaryEP", MODE_PRIVATE);
            SharedPreferences.Editor editorEP = sharedPreferences.edit();

            switch (index) {
                case 1:
                    isSelected1 = !isSelected1;
                    layout.setBackgroundResource(isSelected1 ? R.drawable.extras_page_selected_background : R.drawable.extras_page_background);
                    editorEP.putString("Extra1", isSelected1 ? "200/Day" : ""); // Save or clear value
                    break;
                case 2:
                    isSelected2 = !isSelected2;
                    layout.setBackgroundResource(isSelected2 ? R.drawable.extras_page_selected_background : R.drawable.extras_page_background);
                    editorEP.putString("Extra2", isSelected2 ? "700" : "");
                    break;
                case 3:
                    isSelected3 = !isSelected3;
                    layout.setBackgroundResource(isSelected3 ? R.drawable.extras_page_selected_background : R.drawable.extras_page_background);
                    editorEP.putString("Extra3", isSelected3 ? "400" : "");
                    break;
                case 4:
                    isSelected4 = !isSelected4;
                    layout.setBackgroundResource(isSelected4 ? R.drawable.extras_page_selected_background : R.drawable.extras_page_background);
                    editorEP.putString("Extra4", isSelected4 ? "1400" : "");
                    break;
                case 5:
                    isSelected5 = !isSelected5;
                    layout.setBackgroundResource(isSelected5 ? R.drawable.extras_page_selected_background : R.drawable.extras_page_background);
                    editorEP.putString("Extra5", isSelected5 ? "1400" : "");
                    break;
            }
            editorEP.apply(); // Apply changes
        });
    }


    // Function to clear ALL extras
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