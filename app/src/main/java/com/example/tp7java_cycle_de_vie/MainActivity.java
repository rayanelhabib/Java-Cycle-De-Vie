package com.example.tp7java_cycle_de_vie;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Lifecycle";
    private TextView tvWelcome;
    private ListView lvTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvWelcome = findViewById(R.id.tvWelcome);
        lvTasks = findViewById(R.id.lvTasks);

        // Récupérer le nom d'utilisateur
        String username = getIntent().getStringExtra("USERNAME_EXTRA");
        tvWelcome.setText("Bienvenue, " + username + " !");

        // Données pour la ListView
        ArrayList<String> tasks = new ArrayList<>();
        tasks.add("Apprendre le cycle de vie Android");
        tasks.add("Créer une ListView");
        tasks.add("Gérer les Intents");
        tasks.add("Faire le rapport du TP");

        // Adapter pour la ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);
        lvTasks.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "L'application devient visible à l'écran", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "L'application est prête à être utilisée", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "L'application perd le focus", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Application mise en pause.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        new AlertDialog.Builder(this)
                .setTitle("Application en arrière-plan")
                .setMessage("L'application est désormais en arrière-plan.")
                .setPositiveButton("OK", null)
                .show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        new AlertDialog.Builder(this)
                .setTitle("Retour à l'application")
                .setMessage("Voulez-vous continuer l'utilisation de l'application ?")
                .setPositiveButton("Continuer", (dialog, which) -> {
                    // L'utilisateur continue
                })
                .setNegativeButton("Annuler", (dialog, which) -> {
                    finish(); // Ferme l'activité si l'utilisateur annule
                })
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Fermeture définitive de l'application et libération des ressources.");
    }
}
