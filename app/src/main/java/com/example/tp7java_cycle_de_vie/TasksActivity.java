package com.example.tp7java_cycle_de_vie;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class TasksActivity extends AppCompatActivity {

    private static final String TAG = "Lifecycle";
    private ListView lvTasks;
    private Spinner spinnerTaskFilter, spinnerNewTaskStatus;
    private EditText etNewTaskName;
    private TaskAdapter taskAdapter;
    private List<Task> allTasks;
    private TextView tvCurrentState;

    private ActivityResultLauncher<Intent> taskDetailLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        // Initialisation des vues
        tvCurrentState = findViewById(R.id.tvCurrentState);
        TextView tvWelcome = findViewById(R.id.tvWelcome);
        lvTasks = findViewById(R.id.lvTasks);
        spinnerTaskFilter = findViewById(R.id.spinnerTaskFilter);
        etNewTaskName = findViewById(R.id.etNewTaskName);
        spinnerNewTaskStatus = findViewById(R.id.spinnerNewTaskStatus);
        Button btnAddTask = findViewById(R.id.btnAddTask);

        updateLifecycleState("onCreate()");

        String username = getIntent().getStringExtra("USERNAME_EXTRA");
        tvWelcome.setText("Tâches de " + username);

        // Données initiales
        allTasks = new ArrayList<>();
        allTasks.add(new Task("Apprendre le cycle de vie", "Terminée"));
        allTasks.add(new Task("Intégrer un Spinner", "Terminée"));
        allTasks.add(new Task("Créer un Adapter personnalisé", "À faire"));

        // Adapter pour la ListView
        taskAdapter = new TaskAdapter(this, new ArrayList<>());
        lvTasks.setAdapter(taskAdapter);

        // Adapter pour le Spinner de filtre
        ArrayAdapter<CharSequence> filterAdapter = ArrayAdapter.createFromResource(this,
                R.array.task_filter_options, android.R.layout.simple_spinner_item);
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTaskFilter.setAdapter(filterAdapter);

        // Adapter pour le Spinner de statut (nouvelle tâche)
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this,
                R.array.new_task_status_options, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNewTaskStatus.setAdapter(statusAdapter);

        // Logique du bouton "Ajouter"
        btnAddTask.setOnClickListener(v -> {
            String taskName = etNewTaskName.getText().toString().trim();
            if (taskName.isEmpty()) {
                Toast.makeText(TasksActivity.this, "Veuillez entrer un nom pour la tâche", Toast.LENGTH_SHORT).show();
                return;
            }
            String taskStatus = spinnerNewTaskStatus.getSelectedItem().toString();
            allTasks.add(new Task(taskName, taskStatus));
            filterTasks(spinnerTaskFilter.getSelectedItem().toString()); // Rafraîchir la liste
            etNewTaskName.setText(""); // Vider le champ
            Toast.makeText(TasksActivity.this, "Tâche ajoutée !", Toast.LENGTH_SHORT).show();
        });


        // ... (le reste du code pour les listeners, le launcher et le cycle de vie reste le même) ...
        spinnerTaskFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterTasks(parent.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        lvTasks.setOnItemClickListener((parent, view, position, id) -> {
            Task selectedTask = taskAdapter.getItem(position);
            if (selectedTask != null) {
                Intent intent = new Intent(TasksActivity.this, TaskDetailActivity.class);
                intent.putExtra("TASK_EXTRA", selectedTask);
                int originalPosition = allTasks.indexOf(selectedTask);
                intent.putExtra("TASK_POSITION", originalPosition);
                taskDetailLauncher.launch(intent);
            }
        });

        taskDetailLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == AppCompatActivity.RESULT_OK && result.getData() != null) {
                    int completedTaskPosition = result.getData().getIntExtra("COMPLETED_TASK_POSITION", -1);
                    if (completedTaskPosition != -1) {
                        allTasks.get(completedTaskPosition).setStatus("Terminée");
                        filterTasks(spinnerTaskFilter.getSelectedItem().toString());
                        Toast.makeText(this, "Tâche mise à jour !", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        filterTasks("Toutes");
    }

    private void updateLifecycleState(String state) {
        tvCurrentState.setText("État actuel : " + state);
        Log.d(TAG, "État actuel : " + state);
    }

    private void filterTasks(String filter) {
        List<Task> filteredTasks = new ArrayList<>();
        if (filter.equals("Toutes")) {
            filteredTasks.addAll(allTasks);
        } else {
            for (Task task : allTasks) {
                if (task.getStatus().equals(filter)) {
                    filteredTasks.add(task);
                }
            }
        }
        taskAdapter.clear();
        taskAdapter.addAll(filteredTasks);
        taskAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateLifecycleState("onStart()");
        Toast.makeText(this, "L'application devient visible à l'écran", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateLifecycleState("onResume()");
        Toast.makeText(this, "L'application est prête à être utilisée", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        updateLifecycleState("onPause()");
        Toast.makeText(this, "L'application perd le focus", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        updateLifecycleState("onStop()");
        new AlertDialog.Builder(this)
                .setTitle("Application en arrière-plan")
                .setMessage("L'application est désormais en arrière-plan.")
                .setPositiveButton("OK", null)
                .show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateLifecycleState("onRestart()");
        new AlertDialog.Builder(this)
                .setTitle("Retour à l'application")
                .setMessage("Voulez-vous continuer l'utilisation de l'application ?")
                .setPositiveButton("Continuer", (dialog, which) -> {})
                .setNegativeButton("Annuler", (dialog, which) -> finish())
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "État actuel : onDestroy()");
    }
}
