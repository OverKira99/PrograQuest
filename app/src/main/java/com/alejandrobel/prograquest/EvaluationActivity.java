package com.alejandrobel.prograquest;


import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EvaluationActivity extends AppCompatActivity {


    private TextView textViewQuestion, textViewCode;
    private RadioGroup radioGroupAnswers;
    private RadioButton radioButtonAnswer1, radioButtonAnswer2, radioButtonAnswer3;
    private Button buttonSubmit;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String correctOption;
    private int userScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);


        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        textViewQuestion = findViewById(R.id.textViewQuestion);
        textViewCode = findViewById(R.id.textViewCode);
        radioGroupAnswers = findViewById(R.id.radioGroupAnswers);
        radioButtonAnswer1 = findViewById(R.id.radioButtonAnswer1);
        radioButtonAnswer2 = findViewById(R.id.radioButtonAnswer2);
        radioButtonAnswer3 = findViewById(R.id.radioButtonAnswer3);
        buttonSubmit = findViewById(R.id.buttonSubmit);


        loadQuestionData();

        // Enviar respuesta
        buttonSubmit.setOnClickListener(v -> {
            int selectedId = radioGroupAnswers.getCheckedRadioButtonId();
            RadioButton selectedRadioButton = findViewById(selectedId);

            if (selectedRadioButton != null && selectedRadioButton.getText().toString().equals(correctOption)) {
                Toast.makeText(EvaluationActivity.this, "¡Correcto!", Toast.LENGTH_SHORT).show();
                userScore += 10;  // Incrementar el puntaje
            } else {
                Toast.makeText(EvaluationActivity.this, "Incorrecto, intenta de nuevo.", Toast.LENGTH_SHORT).show();
            }

            // Guardar resultados después de enviar la respuesta
            saveEvaluationResult(userScore);
        });
    }

    // Método para cargar pregunta de Firestore
    private void loadQuestionData() {
        db.collection("questions").document("question1")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        textViewQuestion.setText(documentSnapshot.getString("question"));
                        textViewCode.setText(documentSnapshot.getString("code_example"));
                        radioButtonAnswer1.setText(documentSnapshot.getString("option_1"));
                        radioButtonAnswer2.setText(documentSnapshot.getString("option_2"));
                        radioButtonAnswer3.setText(documentSnapshot.getString("option_3"));
                        correctOption = documentSnapshot.getString("correct_option");
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(EvaluationActivity.this, "Error al cargar pregunta", Toast.LENGTH_SHORT).show();
                });
    }

    // Método para guardar resultados en Firestore
    private void saveEvaluationResult(int score) {
        String userId = mAuth.getCurrentUser().getUid();
        String userEmail = mAuth.getCurrentUser().getEmail();

        // Crear un mapa con los datos del usuario
        Map<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("email", userEmail);
        result.put("score", score);
        result.put("timestamp", FieldValue.serverTimestamp()); // Añadir timestamp para ordenación

        // Guardar el resultado en Firestore (colección 'results')
        db.collection("results").document(userId)
                .set(result)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(EvaluationActivity.this, "Resultado guardado", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(EvaluationActivity.this, "Error al guardar resultado", Toast.LENGTH_SHORT).show();
                });
    }
}