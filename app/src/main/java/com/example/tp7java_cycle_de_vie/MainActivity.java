package com.example.tp7java_cycle_de_vie;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvWelcomeDashboard;
    private String currentUsername;

    private ActivityResultLauncher<Intent> profileLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvWelcomeDashboard = findViewById(R.id.tvWelcomeDashboard);
        Button btnGoToProfile = findViewById(R.id.btnGoToProfile);
        Button btnGoToTasks = findViewById(R.id.btnGoToTasks);

        currentUsername = getIntent().getStringExtra("USERNAME_EXTRA");
        tvWelcomeDashboard.setText("Bienvenue, " + currentUsername + " !");

        profileLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        currentUsername = result.getData().getStringExtra("NEW_USERNAME_EXTRA");
                        tvWelcomeDashboard.setText("Bienvenue, " + currentUsername + " !");
                    }
                }
        );

        btnGoToProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            intent.putExtra("USERNAME_EXTRA", currentUsername);
            profileLauncher.launch(intent);
        });

        btnGoToTasks.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TasksActivity.class);
            intent.putExtra("USERNAME_EXTRA", currentUsername);
            startActivity(intent);
        });
    }
}
