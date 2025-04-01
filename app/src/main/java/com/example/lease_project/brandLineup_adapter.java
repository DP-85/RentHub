package com.example.lease_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Executors;
import com.bumptech.glide.Glide;
import android.content.Intent;

public class brandLineup_adapter extends RecyclerView.Adapter<brandLineup_adapter.ViewHolder> {

    List<String> carNames, carDetails, carImages;
    Context context;
    LayoutInflater inflater;

    // NEW CODE
    List<String> Transmission, FuelType;
    List<String> Seating, MaxSpeed, Engine;
    List<Double> PetrolPrice, DieselPrice;
    //

    public brandLineup_adapter(Context context, List<String> carNames, List<String> carDetails, List<String> carImages,
                               List<String> Transmission, List<String> FuelType, List<String> Seating, List<String> MaxSpeed, List<String> Engine,
                               List<Double> PetrolPrice, List<Double> DieselPrice) {
        this.carNames = carNames;
        this.carDetails = carDetails;
        this.carImages = carImages;
        this.context = context;
        this.inflater = LayoutInflater.from(context);

        //  NEW CODE
        this.Transmission = Transmission;
        this.FuelType = FuelType;
        this.Seating = Seating;
        this.MaxSpeed = MaxSpeed;
        this.Engine = Engine;

        this.PetrolPrice = PetrolPrice;
        this.DieselPrice = DieselPrice;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.brand_lineup, parent, false);
        return new brandLineup_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.carnames.setText(carNames.get(position));
        holder.cardetails.setText(carDetails.get(position));

        // Use Glide to load image
        Glide.with(context)
                .load(carImages.get(position))
                .placeholder(R.drawable.bmw_logo) // Optional: Set a placeholder image
                //.error(R.drawable.error_image) // Optional: Set an error image
                .into(holder.carImage);

        holder.detailButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), Car_Details.class);
            intent.putExtra("carName", carNames.get(position));
            //intent.putExtra("carDetails", carDetails.get(position));
            intent.putExtra("carImage", carImages.get(position));

            //  NEW CODE
            intent.putExtra("Transmission", Transmission.get(position));
            intent.putExtra("FuelType", FuelType.get(position));
            intent.putExtra("Seating", Seating.get(position));
            intent.putExtra("Max Speed", MaxSpeed.get(position));
            intent.putExtra("Engine", Engine.get(position));
            //

            intent.putExtra("Petrol Price", PetrolPrice.get(position));
            intent.putExtra("Diesel Price", DieselPrice.get(position));

            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return carNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView carnames, cardetails;
        ImageView carImage;
        Button detailButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            carnames = itemView.findViewById(R.id.carName);
            cardetails = itemView.findViewById(R.id.carDetails);
            carImage = itemView.findViewById(R.id.carImage);
            detailButton = itemView.findViewById(R.id.btnDetails);
        }
    }
}