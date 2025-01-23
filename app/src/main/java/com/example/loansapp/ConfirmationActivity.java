package com.example.loansapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmationActivity extends AppCompatActivity {

    private TextView tvSummary;
    private Button btnBackToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        tvSummary = findViewById(R.id.tvSummary);
        btnBackToMain = findViewById(R.id.btnBackToMain);

        // Modtag data fra UserActivity
        Intent intent = getIntent();
        String brand = intent.getStringExtra("brand");
        String cable = intent.getStringExtra("cable");
        String name = intent.getStringExtra("name");
        String contact = intent.getStringExtra("contact");
        String time = intent.getStringExtra("time");

        // Vis opsummering
        String summary = "Tablet-brand: " + brand + "\n"
                + "Kabeltype: " + cable + "\n"
                + "Låners navn: " + name + "\n"
                + "Kontaktinfo: " + (contact.isEmpty() ? "Ingen oplyst" : contact) + "\n"
                + "Udlånstidspunkt: " + time;

        tvSummary.setText(summary);

        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tilbage til startskærm
                Intent i = new Intent(ConfirmationActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });
    }
}
