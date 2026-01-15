package com.example.tp7java_cycle_de_vie;

import java.io.Serializable;

public class Task implements Serializable {
    private String name;
    private String status; // "À faire" ou "Terminée"

    public Task(String name, String status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
