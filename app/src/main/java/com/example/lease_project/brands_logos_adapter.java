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

    List<String> brand_names;
    List<Integer> brand_logos;
    Context context;
    LayoutInflater inflater;

    public brands_logos_adapter(Context ctx, List<String> brand_names, List<Integer> brand_logos) {
        this.brand_names = brand_names;
        this.brand_logos = brand_logos;
        this.inflater = LayoutInflater.from(ctx);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.brands_grid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.names.setText(brand_names.get(position));
            holder.logos.setImageResource(brand_logos.get(position));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), BrandLineup_page.class);
            intent.putExtra("brandName", brand_names.get(position)); // Make sure this key is "brandName"
            v.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return brand_names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView names;
        ImageView logos;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            names = itemView.findViewById(R.id.brand_text);
            logos = itemView.findViewById(R.id.brand_logo);
        }
    }
}
