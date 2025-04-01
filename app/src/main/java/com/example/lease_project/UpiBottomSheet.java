package com.example.lease_project;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;

public class UpiBottomSheet extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.upi_bottom_sheet, container, false);

        Button payNow = view.findViewById(R.id.payNowUpi);
        payNow.setOnClickListener(v -> {
                Intent i = new Intent(getActivity(), Final_page.class);
                startActivity(i);
                Toast.makeText(getActivity(), "Processing Payment...", Toast.LENGTH_SHORT).show();
                dismiss(); // Close the bottom sheet
        });

        return view;
    }


}
