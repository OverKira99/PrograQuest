package com.alejandrobel.prograquest;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

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

        ImageView logoImageView = findViewById(R.id.logoImageView);
        TextView loadingTextView = findViewById(R.id.loadingTextView);

        // Animación de rotación para el logo
        ObjectAnimator rotation = ObjectAnimator.ofFloat(logoImageView, "rotation", 0f, 360f);
        rotation.setDuration(2000);
        rotation.setRepeatCount(ValueAnimator.INFINITE);
        rotation.start();

        // Animación de parpadeo para el texto de carga
        ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(loadingTextView, "alpha", 0f, 1f);
        fadeInOut.setDuration(1000);
        fadeInOut.setRepeatCount(ValueAnimator.INFINITE);
        fadeInOut.setRepeatMode(ValueAnimator.REVERSE);
        fadeInOut.start();

        // Redirigir a la siguiente actividad después de 3 segundos
        new android.os.Handler().postDelayed(() -> {
            Intent intent = new Intent(LoadScreenActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Cierra la actividad de splash para que no vuelva al retroceder.
        }, 2000); // 3 segundos de espera
    }

}
