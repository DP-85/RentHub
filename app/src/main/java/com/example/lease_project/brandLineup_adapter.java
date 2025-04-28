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
import com.bumptech.glide.Glide;
import android.content.Intent;

// Adapter class for populating the RecyclerView with car lineup data
public class brandLineup_adapter extends RecyclerView.Adapter<brandLineup_adapter.ViewHolder> {

    // Basic data: Car name, details and image URL
    List<String> carNames, carDetails, carImages;
    Context context;
    LayoutInflater inflater;

    // Extra car info (to be sent to detail screen)
    List<String> Transmission, FuelType;
    List<String> Seating, MaxSpeed, Engine;
    List<Double> PetrolPrice, DieselPrice;

    // Constructor to initialize all lists and context
    public brandLineup_adapter(Context context, List<String> carNames, List<String> carDetails, List<String> carImages,
                               List<String> Transmission, List<String> FuelType, List<String> Seating, List<String> MaxSpeed, List<String> Engine,
                               List<Double> PetrolPrice, List<Double> DieselPrice) {

        this.carNames = carNames;
        this.carDetails = carDetails;
        this.carImages = carImages;
        this.context = context;
        this.inflater = LayoutInflater.from(context);

        this.Transmission = Transmission;
        this.FuelType = FuelType;
        this.Seating = Seating;
        this.MaxSpeed = MaxSpeed;
        this.Engine = Engine;
        this.PetrolPrice = PetrolPrice;
        this.DieselPrice = DieselPrice;
    }

    // This method inflates the XML layout for each item of the RecyclerView
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.brand_lineup, parent, false);
        return new brandLineup_adapter.ViewHolder(view);
    }

    // Binds data from lists to views in each RecyclerView item
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.carnames.setText(carNames.get(position));
        holder.cardetails.setText(carDetails.get(position));

        // Load image from URL using Glide
        Glide.with(context)
                .load(carImages.get(position)) // URL of image
                .placeholder(R.drawable.bmw_logo) // While loading
                //.error(R.drawable.error_image)  // On error (optional)
                .into(holder.carImage);

        // When "Details" button is clicked, open Car_Details activity
        holder.detailButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), Car_Details.class);

            // Send all required car data to the next activity via Intent extras
            intent.putExtra("carName", carNames.get(position));
            intent.putExtra("carImage", carImages.get(position));
            intent.putExtra("Transmission", Transmission.get(position));
            intent.putExtra("FuelType", FuelType.get(position));
            intent.putExtra("Seating", Seating.get(position));
            intent.putExtra("Max Speed", MaxSpeed.get(position));
            intent.putExtra("Engine", Engine.get(position));
            intent.putExtra("Petrol Price", PetrolPrice.get(position));
            intent.putExtra("Diesel Price", DieselPrice.get(position));

            // Start Car_Details activity
            view.getContext().startActivity(intent);
        });
    }

    // Tells the RecyclerView how many items are in the list
    @Override
    public int getItemCount() {
        return carNames.size();
    }

    // ViewHolder class holds references to the views in each item layout
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView carnames, cardetails, detailButton;
        ImageView carImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Link each view with its ID from brand_lineup.xml
            carnames = itemView.findViewById(R.id.carName);
            cardetails = itemView.findViewById(R.id.carDetails);
            carImage = itemView.findViewById(R.id.carImage);
            detailButton = itemView.findViewById(R.id.btnDetails); // It's a TextView styled like a button
        }
    }
}
