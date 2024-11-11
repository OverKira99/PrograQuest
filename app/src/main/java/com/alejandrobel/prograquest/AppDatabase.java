package com.alejandrobel.prograquest;

import android.content.Context;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.Room;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.annotation.NonNull;

@Database(entities = {Question.class, Score.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract QuestionDao questionDao();
    public abstract ScoreDao scoreDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "app_database")
                            .addCallback(roomCallback) // Llama a la función de carga inicial
                            .allowMainThreadQueries() // Evitar esto en producción
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new Thread(() -> {
                // Insertar preguntas iniciales
                populateDatabase(INSTANCE.questionDao());
            }).start();
        }
    };

    private static void populateDatabase(QuestionDao questionDao) {
        // Preguntas de ejemplo



    }
}
