package com.example.lease_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Rect;
import android.view.View;
import android.widget.ImageButton;

public class brands_logos_page extends AppCompatActivity {

    RecyclerView brandList;
    List<String> brand_names;
    List<Integer> brand_logos;
    brands_logos_adapter adapter;
    ImageButton homebutton;

// CUSTOM RECYCLERVIEW CLASS USED FOR DECORATION AND SPACING PURPOSE
    class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private final int spacing;

        public GridSpacingItemDecoration(int spacing) {
            this.spacing = spacing;
        }

        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = spacing;
            outRect.right = spacing;
            outRect.top = spacing;
            outRect.bottom = spacing;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_brands_logos_page);

        ImageButton homeBackBtn = findViewById(R.id.homeBackButton);
        homebutton = findViewById(R.id.homeimageButton);

        brandList = findViewById(R.id.brandList);

// ARRAYS CONTAINING ITEMS TO BE DISPLAYED IN THE RECYCLERVIEW
        brand_names = new ArrayList<>();
        brand_logos = new ArrayList<>();

// ADDITION OF BRAND NAME TEXT INTO ARRAYLIST
        brand_names.add("BMW");
        brand_names.add("Audi");
        brand_names.add("Mercedes");
        brand_names.add("Volvo");
        brand_names.add("Land Rover");
        brand_names.add("Jeep");
        brand_names.add("Toyota");
        brand_names.add("Volkswagen");
        brand_names.add("Skoda");
        brand_names.add("Hyundai");
        brand_names.add("Kia");
        brand_names.add("Honda");
        brand_names.add("Suzuki");
        brand_names.add("Mahindra");
        brand_names.add("Tata");
        brand_names.add("MG");

// ADDITION OF BRAND LOGOS IMAGES INTO ARRAYLIST
        brand_logos.add(R.drawable.bmw_logo);
        brand_logos.add(R.drawable.audi_logo);
        brand_logos.add(R.drawable.mercedes_logo);
        brand_logos.add(R.drawable.volvo_logo);
        brand_logos.add(R.drawable.landrover_logo);
        brand_logos.add(R.drawable.jeep_logo);
        brand_logos.add(R.drawable.toyota_logo);
        brand_logos.add(R.drawable.vw_logo);
        brand_logos.add(R.drawable.skoda_logo);
        brand_logos.add(R.drawable.hyundai_logo);
        brand_logos.add(R.drawable.kia_logo);
        brand_logos.add(R.drawable.honda_logo);
        brand_logos.add(R.drawable.suzuki_logo);
        brand_logos.add(R.drawable.mahindra_logo);
        brand_logos.add(R.drawable.tata_logo);
        brand_logos.add(R.drawable.mg_logo);

// INITIALIZING THE ADAPTER ACCESS
        adapter = new brands_logos_adapter(this, brand_names, brand_logos);

// USED FOR DISPLAYING ITEMS INTO A GRID STRUCTURE
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3); // DIVISION OF ITEMS INTO SPAN

// PREFETCHING OF DISPLAY ITEMS IN ORDER TO AVOID LAG
        gridLayoutManager.setItemPrefetchEnabled(true);
        gridLayoutManager.setInitialPrefetchItemCount(10); // NO. OF ITEMS TO PREFETCH ITEMS AT ONCE
        brandList.setLayoutManager(gridLayoutManager);
//
        brandList.setHasFixedSize(true); // INDICATION FOR RECYCLER VIEW TO AVOID UNNECESSARY ITEMS & LAYOUT SIZE CALCULATION

// CONNECTING THE MAIN JAVA PAGE AND ADAPTER PAGE
        brandList.setAdapter(adapter);

// GRID SPACING AND DECORATIONS
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.grid_spacing);
        brandList.addItemDecoration(new GridSpacingItemDecoration(spacingInPixels));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

// BUTTON TO REDIRECT TO HOME
        homeBackBtn.setOnClickListener(view -> {
            Intent i = new Intent(brands_logos_page.this, home_page.class);
            startActivity(i);
            finish();
        });

// IMAGE BUTTON TO REDIRECT TO HOME
        homebutton.setOnClickListener(view -> {
            Intent h = new Intent(brands_logos_page.this, home_page.class);
            startActivity(h);
            finish();
        });

    }

}