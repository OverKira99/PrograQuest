package com.alejandrobel.prograquest;

import android.content.Intent;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfileFragment extends Fragment {
    private ImageView imageProfile;
    private TextView textViewEmail;
    private Button buttonChangeImage, buttonLogout, buttonLeaderboard;
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
        buttonLeaderboard = view.findViewById(R.id.buttonLeaderboard);


        // Cargar datos y configurar listeners
        loadProfileData();
        buttonChangeImage.setOnClickListener(v -> openFileChooser());
        buttonLogout.setOnClickListener(v -> logout());

        return view;
    }


    private void loadProfileData() {
        // Configurar la lógica de cargar perfil
    }

    private void openFileChooser() {
        // Selección de imagen
    }

    private void logout() {
        mAuth.signOut();
        startActivity(new Intent(getContext(), LoginActivity.class));
        requireActivity().finish();
    }

}