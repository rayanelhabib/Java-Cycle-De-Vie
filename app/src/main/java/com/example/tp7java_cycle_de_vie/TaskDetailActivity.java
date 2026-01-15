package com.example.tp7java_cycle_de_vie;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TaskDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        TextView tvDetailTaskName = findViewById(R.id.tvDetailTaskName);
        Button btnMarkAsDone = findViewById(R.id.btnMarkAsDone);

        // Récupérer la tâche et sa position
        Intent intent = getIntent();
        Task task = (Task) intent.getSerializableExtra("TASK_EXTRA");
        int taskPosition = intent.getIntExtra("TASK_POSITION", -1);

        if (task != null) {
            tvDetailTaskName.setText(task.getName());
        }

        btnMarkAsDone.setOnClickListener(v -> {
            if (taskPosition != -1) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("COMPLETED_TASK_POSITION", taskPosition);
                setResult(RESULT_OK, resultIntent);
                finish(); // Retourne à MainActivity
            }
        });
    }
}
