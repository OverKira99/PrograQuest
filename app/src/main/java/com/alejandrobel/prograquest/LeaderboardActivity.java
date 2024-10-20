package com.alejandrobel.prograquest;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {

    private RecyclerView recyclerViewLeaderboard;
    private LeaderboardAdapter leaderboardAdapter;
    private FirebaseFirestore db;
    private List<ResultModel> resultsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        // Enlazar RecyclerView
        recyclerViewLeaderboard = findViewById(R.id.recyclerViewLeaderboard);
        recyclerViewLeaderboard.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar Firestore
        db = FirebaseFirestore.getInstance();

        // Cargar la tabla de clasificación
        loadLeaderboardData();
    }

    private void loadLeaderboardData() {
        db.collection("results")
                .orderBy("score", Query.Direction.DESCENDING) // Ordenar por puntaje descendente
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    resultsList.clear();
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        ResultModel result = document.toObject(ResultModel.class);
                        resultsList.add(result);
                    }

                    leaderboardAdapter = new LeaderboardAdapter(resultsList);
                    recyclerViewLeaderboard.setAdapter(leaderboardAdapter);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(LeaderboardActivity.this, "Error al cargar clasificación", Toast.LENGTH_SHORT).show();
                });
    }
}