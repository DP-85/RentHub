package com.example.lease_project;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

public class UpiBottomSheet extends BottomSheetDialogFragment {

    private String finalAmount = "0";  // Default value if not passed

    public UpiBottomSheet() {
        // Required empty constructor
    }

    // Constructor to receive amount
    public UpiBottomSheet(String amount) {
        this.finalAmount = amount;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.upi_bottom_sheet, container, false);

        TextView amountTextView = view.findViewById(R.id.amount); // Your TextView where amount should be shown
        Button payNow = view.findViewById(R.id.payNowUpi);

        // Set the final amount dynamically
        amountTextView.setText(finalAmount + " ₹");

        payNow.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), Final_page.class);
            startActivity(i);
            Toast.makeText(getActivity(), "Processing Payment...", Toast.LENGTH_SHORT).show();
            dismiss(); // Close the bottom sheet
        });

        return view;
    }
}
