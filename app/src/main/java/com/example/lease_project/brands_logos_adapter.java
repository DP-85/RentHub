package com.example.lease_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import android.content.Intent;

public class brands_logos_adapter extends RecyclerView.Adapter<brands_logos_adapter.ViewHolder> {

    List<String> brand_names; // List to hold brand names
    List<Integer> brand_logos; // List to hold brand logos (image resources)
    Context context; // Context to access resources and start activities
    LayoutInflater inflater; // To inflate the layout for each item in the RecyclerView

    // Constructor to initialize the data (brand names and logos) for the adapter
    public brands_logos_adapter(Context ctx, List<String> brand_names, List<Integer> brand_logos) {
        this.brand_names = brand_names;
        this.brand_logos = brand_logos;
        this.inflater = LayoutInflater.from(ctx); // Initializes the LayoutInflater from the context
    }

    // Called to create a new ViewHolder, which holds the views for each item in the list
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        View view = inflater.inflate(R.layout.brands_grid, parent, false);
        return new ViewHolder(view); // Return a new ViewHolder with the inflated view
    }

    // Called to bind the data (brand name and logo) to the views in the ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Set the brand name and logo for the current item at the given position
        holder.names.setText(brand_names.get(position)); // Set brand name
        holder.logos.setImageResource(brand_logos.get(position)); // Set brand logo (image)

        // Set up a click listener for each item in the RecyclerView
        holder.itemView.setOnClickListener(v -> {
            // When an item is clicked, start a new activity to show details of the brand
            Intent intent = new Intent(v.getContext(), BrandLineup_page.class);
            intent.putExtra("brandName", brand_names.get(position)); // Pass the brand name to the new activity
            v.getContext().startActivity(intent); // Start the new activity
        });
    }

    // Return the total number of items in the list
    @Override
    public int getItemCount() {
        return brand_names.size(); // Return the size of the list (number of brands)
    }

    // ViewHolder class to hold references to the views (TextView and ImageView) for each item
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView names; // Reference to the TextView that displays the brand name
        ImageView logos; // Reference to the ImageView that displays the brand logo

        // Constructor to initialize the views inside each item layout
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            names = itemView.findViewById(R.id.brand_text); // Find the TextView for brand name
            logos = itemView.findViewById(R.id.brand_logo); // Find the ImageView for brand logo
        }
    }
}
