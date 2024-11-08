package com.alejandrobel.prograquest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoadScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen);

        // Simula un retraso al iniciar session
        new Handler().postDelayed(() -> {

            // pasa a la siguiente activity
            startActivity(new Intent(LoadScreenActivity.this, LoginActivity.class));
            finish(); // Cerrar la SplashActivity
        }, 200);
    }

}
