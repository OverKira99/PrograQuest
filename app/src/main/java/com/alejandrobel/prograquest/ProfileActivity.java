package com.alejandrobel.prograquest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfileActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView imageProfile;
    private TextView textViewEmail;
    private Button buttonChangeImage, buttonLogout, buttonLeaderboard;
    private ProgressBar progressBar;
    private Switch switchTheme;

    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseUser currentUser;
    private Uri imageUri;

    private boolean isDarkTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        loadTheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        currentUser = mAuth.getCurrentUser();


        imageProfile = findViewById(R.id.imageProfile);
        textViewEmail = findViewById(R.id.textViewEmail);
        buttonChangeImage = findViewById(R.id.buttonChangeImage);
        buttonLogout = findViewById(R.id.buttonLogout);
        buttonLeaderboard = findViewById(R.id.buttonLeaderboard);
        switchTheme = findViewById(R.id.switchTheme);
        progressBar = findViewById(R.id.progressBar);


        if (currentUser != null) {
            textViewEmail.setText(currentUser.getEmail());
            loadProfileImage();
        }


        buttonChangeImage.setOnClickListener(v -> openFileChooser());


        switchTheme.setChecked(isDarkTheme);
        switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                setTheme(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                setTheme(AppCompatDelegate.MODE_NIGHT_NO);
            }
            saveTheme(isChecked);
            recreate();
        });


        buttonLeaderboard.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, LeaderboardActivity.class);
            startActivity(intent);
        });

        buttonLogout.setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }


    private void loadProfileImage() {
        StorageReference profileRef = storageReference.child("profile_images/" + currentUser.getUid() + ".jpg");

        profileRef.getDownloadUrl().addOnSuccessListener(uri -> {
            Glide.with(this).load(uri).into(imageProfile); // Usar Glide para cargar la imagen
        }).addOnFailureListener(e -> {
            Toast.makeText(ProfileActivity.this, "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
        });
    }

    // Método para abrir el selector de archivos (galería)
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageProfile.setImageURI(imageUri);
            uploadProfileImage(); // Subir la nueva imagen
        }
    }

    // Método para subir la nueva imagen de perfil a Firebase Storage
    private void uploadProfileImage() {
        if (imageUri != null) {
            progressBar.setVisibility(View.VISIBLE);

            // Referencia a donde se almacenará la imagen de perfil en Firebase Storage
            StorageReference fileReference = storageReference.child("profile_images/" + currentUser.getUid() + ".jpg");

            fileReference.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(ProfileActivity.this, "Imagen de perfil actualizada", Toast.LENGTH_SHORT).show();
                        });
                    })
                    .addOnFailureListener(e -> {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ProfileActivity.this, "Error al subir la imagen", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    // Guardar la preferencia del tema en SharedPreferences
    private void saveTheme(boolean isDarkTheme) {
        SharedPreferences sharedPreferences = getSharedPreferences("user_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("dark_theme", isDarkTheme);
        editor.apply();
    }

    // Cargar el tema desde SharedPreferences
    private void loadTheme() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_preferences", MODE_PRIVATE);
        isDarkTheme = sharedPreferences.getBoolean("dark_theme", false);
        if (isDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}