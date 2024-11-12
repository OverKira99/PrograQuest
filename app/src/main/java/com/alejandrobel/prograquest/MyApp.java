package com.alejandrobel.prograquest;

import android.app.Application;

import androidx.room.Room;

import com.google.firebase.FirebaseApp;

public class MyApp extends Application {
    private static AppDatabase appDatabase;
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseApp.initializeApp(this);

        // Inicializa Room Database
        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app_database")
                .allowMainThreadQueries() // Solo para pruebas, evita en producción
                .build();
    }

    public static AppDatabase getDatabase() {
        return appDatabase;
    }
}

