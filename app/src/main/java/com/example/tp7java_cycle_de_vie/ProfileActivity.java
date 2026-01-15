package com.example.tp7java_cycle_de_vie;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private EditText etEditUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etEditUsername = findViewById(R.id.etEditUsername);
        Button btnValidateUsername = findViewById(R.id.btnValidateUsername);

        String currentUsername = getIntent().getStringExtra("USERNAME_EXTRA");
        etEditUsername.setText(currentUsername);

        btnValidateUsername.setOnClickListener(v -> {
            String newUsername = etEditUsername.getText().toString().trim();
            if (!newUsername.isEmpty()) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("NEW_USERNAME_EXTRA", newUsername);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
