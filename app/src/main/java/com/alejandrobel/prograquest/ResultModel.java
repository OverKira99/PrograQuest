package com.alejandrobel.prograquest;

public class ResultModel {

    private String email;
    private int score;

    public ResultModel() {
        // Constructor vacío necesario para Firestore
    }

    public ResultModel(String email, int score) {
        this.email = email;
        this.score = score;
    }

    public String getEmail() {
        return email;
    }

    public int getScore() {
        return score;
    }
}
