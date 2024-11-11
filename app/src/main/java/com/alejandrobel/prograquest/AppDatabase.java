package com.alejandrobel.prograquest;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Question.class, Score.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract QuestionDao questionDao();
    public abstract ScoreDao scoreDao();
}
