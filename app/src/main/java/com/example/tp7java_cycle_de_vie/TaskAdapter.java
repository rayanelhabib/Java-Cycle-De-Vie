package com.example.tp7java_cycle_de_vie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {

    public TaskAdapter(Context context, List<Task> tasks) {
        super(context, 0, tasks);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Récupérer la tâche pour cette position
        Task task = getItem(position);

        // Vérifier si une vue existante est réutilisée, sinon la créer
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_task, parent, false);
        }

        // Récupérer les vues du layout
        TextView tvTaskName = convertView.findViewById(R.id.tvTaskName);
        TextView tvTaskStatus = convertView.findViewById(R.id.tvTaskStatus);

        // Remplir les vues avec les données de la tâche
        if (task != null) {
            tvTaskName.setText(task.getName());
            tvTaskStatus.setText(task.getStatus());
        }

        return convertView;
    }
}
