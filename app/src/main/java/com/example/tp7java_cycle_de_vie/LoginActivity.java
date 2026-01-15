package com.example.tp7java_cycle_de_vie;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            if (TextUtils.isEmpty(username)) {
                Toast.makeText(LoginActivity.this, "Veuillez entrer un nom d'utilisateur", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("USERNAME_EXTRA", username);
                startActivity(intent);
                finish(); // Termine LoginActivity pour ne pas pouvoir y revenir avec le bouton retour
            }
        });
    }
}
