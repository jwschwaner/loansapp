package com.example.loansapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.example.loansapp.controller.LoanController;
import com.example.loansapp.model.Loan;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity implements LoanListAdapter.OnLoanActionListener {

    private Spinner spinnerFilterBrand, spinnerFilterCable;
    private Button btnApplyFilter;
    private RecyclerView recyclerView;
    private LoanListAdapter adapter;
    private LoanController loanController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        spinnerFilterBrand = findViewById(R.id.spinnerFilterBrand);
        spinnerFilterCable = findViewById(R.id.spinnerFilterCable);
        btnApplyFilter = findViewById(R.id.btnApplyFilter);

        recyclerView = findViewById(R.id.recyclerViewLoans);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loanController = new LoanController(this);

        // Opsæt spinners (hardkodede valgmuligheder)
        // Brand
        String[] brandOptions = {"Alle", "Brand A", "Brand B"};
        android.widget.ArrayAdapter<String> brandAdapter = new android.widget.ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, brandOptions
        );
        brandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilterBrand.setAdapter(brandAdapter);

        // Cable
        String[] cableOptions = {"Alle", "USB-C", "Micro-USB"};
        android.widget.ArrayAdapter<String> cableAdapter = new android.widget.ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, cableOptions
        );
        cableAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilterCable.setAdapter(cableAdapter);

        // Vis alle aktive udlån ved opstart
        loadLoans(loanController.getAllActiveLoans());

        btnApplyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedBrand = spinnerFilterBrand.getSelectedItem().toString();
                String selectedCable = spinnerFilterCable.getSelectedItem().toString();
                List<Loan> filteredLoans = loanController.filterLoans(selectedBrand, selectedCable);
                loadLoans(filteredLoans);
            }
        });
    }

    private void loadLoans(List<Loan> loans) {
        if (adapter == null) {
            adapter = new LoanListAdapter(this, loans, this);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setData(loans);
        }
    }

    @Override
    public void onMarkReturned(int loanId) {
        // Markér som afleveret
        loanController.markLoanReturned(loanId);
        // Opdater listen
        List<Loan> updatedLoans = loanController.getAllActiveLoans();
        loadLoans(updatedLoans);
    }
}
