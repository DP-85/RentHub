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

public class InstallmentsBottomSheet extends BottomSheetDialogFragment {

    private boolean isOptionSelected = false; // Track if any option is selected

    public InstallmentsBottomSheet() {
        // Required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.installments_bottom_sheet, container, false);

        Button day15 = view.findViewById(R.id.days15);
        Button month1 = view.findViewById(R.id.month1);
        Button month3 = view.findViewById(R.id.month3);
        Button confirm = view.findViewById(R.id.payNowinst);

        day15.setOnClickListener(view1 -> {
            highlightSelection(day15, month1, month3);
            isOptionSelected = true;
        });

        month1.setOnClickListener(view1 -> {
            highlightSelection(month1, day15, month3);
            isOptionSelected = true;
        });

        month3.setOnClickListener(view1 -> {
            highlightSelection(month3, day15, month1);
            isOptionSelected = true;
        });

        confirm.setOnClickListener(view1 -> {
            if (isOptionSelected) {
                Intent i = new Intent(getActivity(), Final_page.class);
                startActivity(i);
                dismiss(); // optional: close bottom sheet
            } else {
                Toast.makeText(getActivity(), "Please select an installment plan.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void highlightSelection(Button selected, Button... others) {
        selected.setBackgroundResource(R.drawable.extras_page_selected_background);
        for (Button btn : others) {
            btn.setBackgroundResource(R.drawable.input_background);
        }
    }
}
