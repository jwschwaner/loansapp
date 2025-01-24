package com.example.loansapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.loansapp.controller.LoanController;
import com.example.loansapp.model.Loan;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserActivity extends AppCompatActivity {

    private Spinner spinnerBrand, spinnerCable;
    private EditText etName, etContact;
    private Button btnSubmit;
    private LoanController loanController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        initGui();
    }

    private void initGui() {
        fetchViews();

        loanController = new LoanController(this);

        setupSpinners();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String brand = spinnerBrand.getSelectedItem().toString();
                String cable = spinnerCable.getSelectedItem().toString();
                String name = etName.getText().toString().trim();
                String contact = etContact.getText().toString().trim();

                // Tjek validering
                if (brand.equals("Vælg brand")) {
                    Toast.makeText(UserActivity.this, "Vælg venligst et tablet-brand", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (name.isEmpty()) {
                    Toast.makeText(UserActivity.this, "Angiv venligst låners navn", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Indsæt nuværende dato/tid
                String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

                // Opret Loan-objekt
                Loan loan = createLoanObject(brand, cable, name, contact, currentTime);

                long insertedId = loanController.createLoan(loan);

                if (insertedId > 0) {
                    // Gå til bekræftelsesskærm
                    Intent intent = new Intent(UserActivity.this, ConfirmationActivity.class);
                    // Send data videre til kvittering
                    intent.putExtra("brand", brand);
                    intent.putExtra("cable", cable.equals("Ingen") ? "Ingen" : cable);
                    intent.putExtra("name", name);
                    intent.putExtra("contact", contact);
                    intent.putExtra("time", currentTime);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(UserActivity.this, "Fejl ved oprettelse af udlån", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @NonNull
    private static Loan createLoanObject(String brand, String cable, String name, String contact, String currentTime) {
        Loan loan = new Loan();
        loan.setTabletBrand(brand);
        loan.setCableType(cable.equals("Ingen") ? "" : cable);
        loan.setBorrowerName(name);
        loan.setBorrowerContact(contact);
        loan.setLoanTime(currentTime);
        loan.setReturned(false);
        return loan;
    }

    private void setupSpinners() {
        // Udfyld spinner for brand
        String[] brands = {"Vælg brand", "Brand A", "Brand B"};
        ArrayAdapter<String> brandAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, brands);
        brandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBrand.setAdapter(brandAdapter);

        // Udfyld spinner for kabeltype
        String[] cables = {"Ingen", "USB-C", "Micro-USB"};
        ArrayAdapter<String> cableAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, cables);
        cableAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCable.setAdapter(cableAdapter);
    }

    private void fetchViews() {
        spinnerBrand = findViewById(R.id.spinnerBrand);
        spinnerCable = findViewById(R.id.spinnerCable);
        etName = findViewById(R.id.etName);
        etContact = findViewById(R.id.etContact);
        btnSubmit = findViewById(R.id.btnSubmit);
    }
}
