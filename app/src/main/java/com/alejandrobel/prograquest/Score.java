package com.alejandrobel.prograquest;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "scores")
public class Score {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String userName;
    private int score;
    private String topic; // Nuevo campo para filtrar por tema

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }
}
