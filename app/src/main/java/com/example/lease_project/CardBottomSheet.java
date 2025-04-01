package com.example.lease_project;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import java.util.regex.Pattern;

public class CardBottomSheet extends BottomSheetDialogFragment {

    EditText cardNumber, cardExpiry, cardCVV, cardName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_bottom_sheet, container, false);

        cardNumber = view.findViewById(R.id.cardNumber);
        cardExpiry = view.findViewById(R.id.cardExpriry);
        cardCVV = view.findViewById(R.id.cardCVV);
        cardName = view.findViewById(R.id.cardHolderName);

        Button payNow = view.findViewById(R.id.payNowButton);
        payNow.setOnClickListener(v -> {
            if (validateCardDetails()) {
                Intent i = new Intent(getActivity(), Final_page.class);
                startActivity(i);
                Toast.makeText(getActivity(), "Processing Payment...", Toast.LENGTH_SHORT).show();
                dismiss(); // Close the bottom sheet
            }
        });

        return view;
    }

    private boolean validateCardDetails() {
        String cardNum = cardNumber.getText().toString().trim();
        String expiry = cardExpiry.getText().toString().trim();
        String cvv = cardCVV.getText().toString().trim();
        String name = cardName.getText().toString().trim();

        // Check if any field is empty
        if (cardNum.isEmpty()) {
            cardNumber.setError("Card number is required");
            return false;
        }
        if (expiry.isEmpty()) {
            cardExpiry.setError("Expiry date is required");
            return false;
        }
        if (cvv.isEmpty()) {
            cardCVV.setError("CVV is required");
            return false;
        }
        if (name.isEmpty()) {
            cardName.setError("Cardholder name is required");
            return false;
        }

        // Validate Card Number (16 digits)
        if (!cardNum.matches("\\d{16}")) {
            cardNumber.setError("Enter a valid 16-digit card number");
            return false;
        }

        // Validate Expiry Date (MM/YY format)
        if (!Pattern.matches("^(0[1-9]|1[0-2])/[0-9]{2}$", expiry)) {
            cardExpiry.setError("Enter valid MM/YY format");
            return false;
        }

        // Validate CVV (3 digits)
        if (!cvv.matches("\\d{3}")) {
            cardCVV.setError("Enter a valid 3-digit CVV");
            return false;
        }

        return true; // All inputs are valid
    }
}
