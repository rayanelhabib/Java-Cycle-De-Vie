package com.example.tp7java_cycle_de_vie;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnCloseSimple = findViewById(R.id.btnCloseSimple);
        Button btnCloseWithDialog = findViewById(R.id.btnCloseWithDialog);

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            if (TextUtils.isEmpty(username)) {
                Toast.makeText(LoginActivity.this, "Veuillez entrer un nom d'utilisateur", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("USERNAME_EXTRA", username);
                startActivity(intent);
                finish();
            }
        });

        // Bouton pour fermer directement
        btnCloseSimple.setOnClickListener(v -> {
            finish();
        });

        // Bouton pour fermer avec confirmation
        btnCloseWithDialog.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Confirmation")
                    .setMessage("Voulez-vous vraiment fermer l'application ?")
                    .setPositiveButton("Oui", (dialog, which) -> {
                        finish(); // Ferme l'application si l'utilisateur clique sur "Oui"
                    })
                    .setNegativeButton("Non", null) // Ne fait rien si l'utilisateur clique sur "Non"
                    .show();
        });
    }
}
