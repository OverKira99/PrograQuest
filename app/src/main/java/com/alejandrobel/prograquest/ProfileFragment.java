package com.alejandrobel.prograquest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    private ImageView imageProfile;
    private TextView textViewEmail;
    private Button buttonChangeImage, buttonLogout, buttonReset;
    private ProgressBar progressBar;
    private Switch switchTheme;

    private FirebaseAuth mAuth;
    private StorageReference storageReference;
    private Uri imageUri;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("profile_images");

        imageProfile = view.findViewById(R.id.imageProfile);
        textViewEmail = view.findViewById(R.id.textViewEmail);
        buttonChangeImage = view.findViewById(R.id.buttonChangeImage);
        buttonLogout = view.findViewById(R.id.buttonLogout);



        // Cargar datos y configurar listeners
        loadProfileData();
        buttonChangeImage.setOnClickListener(v -> openFileChooser());
        buttonLogout.setOnClickListener(v -> logout());
        return view;
    }


    private void loadProfileData() {
        // Configurar la lógica de cargar perfil

        // Mostrar el correo electrónico del usuario autenticado
        String email = mAuth.getCurrentUser().getEmail();
        textViewEmail.setText(email);

        // Obtener la referencia de la imagen del perfil en Firebase Storage
        String userId = mAuth.getCurrentUser().getUid(); // UID del usuario actual
        StorageReference profileImageRef = storageReference.child(userId + ".jpg"); // Asegúrate de guardar las imágenes con este formato

        // Descargar y mostrar la imagen usando Glide o Picasso
        profileImageRef.getDownloadUrl().addOnSuccessListener(uri -> {
            Glide.with(getContext())
                    .load(uri)
                    .placeholder(R.drawable.default_profile_image) // Imagen por defecto
                    .into(imageProfile);
        }).addOnFailureListener(e -> {
            // Manejar errores (por ejemplo, si no hay imagen guardada)
            imageProfile.setImageResource(R.drawable.default_profile_image);
        });
    }

    private void resetEvaluation(String selectedTopic) {
        SharedPreferences preferences = requireContext().getSharedPreferences("Evaluations", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.remove(selectedTopic + "_completed");
        editor.remove(selectedTopic + "_score");
        editor.remove(selectedTopic + "_totalQuestions");
        editor.apply();

        Toast.makeText(getContext(), "La evaluación ha sido reiniciada.", Toast.LENGTH_SHORT).show();
    }


    private void openFileChooser() {
        // Selección de imagen
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"), 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            uploadImageToFirebase();
        }
    }

    private void uploadImageToFirebase() {
        if (imageUri != null) {
            progressBar.setVisibility(View.VISIBLE); // Muestra un ProgressBar mientras se sube la imagen
            String userId = mAuth.getCurrentUser().getUid(); // UID del usuario actual
            StorageReference fileRef = storageReference.child(userId + ".jpg");

            fileRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                progressBar.setVisibility(View.GONE); // Oculta el ProgressBar
                Toast.makeText(getContext(), "Imagen subida exitosamente", Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    Glide.with(getContext())
                            .load(uri)
                            .placeholder(R.drawable.default_profile_image)
                            .into(imageProfile);
                });
            }).addOnFailureListener(e -> {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error al subir la imagen", Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void logout() {
        mAuth.signOut();
        startActivity(new Intent(getContext(), LoginActivity.class));
        requireActivity().finish();
    }

}