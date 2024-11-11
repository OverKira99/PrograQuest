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
        questionDao.insert(new Question("¿Qué hace un bucle IF?", "", "Controla una condición", "Repite un bloque", "Define un método", "Controla una condición", "IF"));
        questionDao.insert(new Question("¿Qué hace un bucle FOR?", "", "Repite un bloque un número determinado de veces", "Evalúa una condición", "Llama a una función", "Repite un bloque un número determinado de veces", "FOR"));
        questionDao.insert(new Question("¿Qué hace SWITCH?", "", "Evalúa varias condiciones", "Repite un bloque de código", "Termina un programa", "Evalúa varias condiciones", "SWITCH"));
    }
}
