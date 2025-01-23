package com.example.loansapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLoginActivity extends AppCompatActivity {

    private EditText etAdminCode;
    private Button btnAdminLogin;

    // Hårdkodet admin-kode (du kan gemme dette sikkert i en produktionsapp!)
    private static final String ADMIN_CODE = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        etAdminCode = findViewById(R.id.etAdminCode);
        btnAdminLogin = findViewById(R.id.btnAdminLogin);

        btnAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = etAdminCode.getText().toString().trim();
                if (code.equals(ADMIN_CODE)) {
                    // Åbn admin-oversigt
                    Intent intent = new Intent(AdminLoginActivity.this, AdminActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AdminLoginActivity.this, "Forkert kode!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
