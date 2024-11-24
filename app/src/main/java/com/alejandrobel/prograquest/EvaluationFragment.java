package com.alejandrobel.prograquest;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EvaluationFragment extends Fragment {


    private List<QuestionSet> questionSets;
    private List<Question> currentQuestions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private Spinner evaluationSpinner;
    private String[] evaluationTypes = {"IF", "FOR", "WHILE", "SWITCH"};
    private TextView questionTextView;
    private RadioGroup answerRadioGroup;
    private Button nextButton;

    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_evaluation, container, false);

        evaluationSpinner = view.findViewById(R.id.evaluationSpinner);
        questionTextView = view.findViewById(R.id.questionTextView);
        answerRadioGroup = view.findViewById(R.id.answerRadioGroup);
        nextButton = view.findViewById(R.id.nextButton);


        loadQuestionsFromJson();
        setupSpinner();
        setupNextButton();

        return view;
    }


    private void loadQuestionsFromJson() {
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.preguntas_app);
            InputStreamReader reader = new InputStreamReader(inputStream);
            Gson gson = new Gson();
            Type listType = new TypeToken<List<QuestionSet>>() {
            }.getType();
            questionSets = gson.fromJson(reader, listType);

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error al cargar las preguntas", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, evaluationTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        evaluationSpinner.setAdapter(adapter);

        evaluationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedTopic = evaluationTypes[position];
                setupEvaluation(selectedTopic);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void setupEvaluation(String selectedTopic) {
        // Obtener referencia a SharedPreferences
        SharedPreferences preferences = requireContext().getSharedPreferences("Evaluations", Context.MODE_PRIVATE);

        // Verificar si ya completó la evaluación del tema
        boolean isCompleted = preferences.getBoolean(selectedTopic, false);
        if (isCompleted) {
            int savedScore = preferences.getInt(selectedTopic + "_score", 0);
            int totalQuestions = preferences.getInt(selectedTopic + "_totalQuestions", 0);

            // Mostrar el resultado almacenado
            questionTextView.setText("Evaluación completada");
            questionTextView.setGravity(Gravity.CENTER);
            answerRadioGroup.setVisibility(View.GONE);
            nextButton.setVisibility(View.GONE);

            // Mostrar el resultado en pantalla
            TextView resultTextView = new TextView(getContext());
            resultTextView.setText("Puntuación: " + savedScore + " de " + totalQuestions);
            resultTextView.setGravity(Gravity.CENTER);
            ((ViewGroup) getView()).addView(resultTextView);

            Toast.makeText(getContext(), "Ya completaste esta evaluación.", Toast.LENGTH_SHORT).show();
            return; // Salir del método para no iniciar una nueva evaluación
        }

        // Continuar con la evaluación si no está completada
        currentQuestions = getQuestionsByTopic(selectedTopic);
        if (currentQuestions == null || currentQuestions.isEmpty()) {
            questionTextView.setText("Evaluación finalizada. Tu puntuación: " + score + " de " + currentQuestions.size());
            Toast.makeText(getContext(), "No se encontraron preguntas para el tema seleccionado", Toast.LENGTH_SHORT).show();
            return;
        }

        // Reset evaluation state
        currentQuestionIndex = 0;
        score = 0;

        // Shuffle and limit to 5 questions
        Collections.shuffle(currentQuestions);
        currentQuestions = currentQuestions.subList(0, Math.min(currentQuestions.size(), 5));

        displayQuestion();
    }

    private List<Question> getQuestionsByTopic(String topic) {
        for (QuestionSet set : questionSets) {
            if (set.getTopic().equalsIgnoreCase(topic)) {
                return set.getQuestions();
            }
        }
        return null;
    }

    private void setupNextButton() {
        nextButton.setOnClickListener(v -> onNextButtonClick());
    }

    private void displayQuestion() {
        if (currentQuestionIndex < currentQuestions.size()) {
            Question question = currentQuestions.get(currentQuestionIndex);
            questionTextView.setText(question.getQuestion());

            answerRadioGroup.removeAllViews();

            List<String> answers = question.getAnswers();
            if (answers.isEmpty()) {
                Toast.makeText(getContext(), "No hay opciones disponibles", Toast.LENGTH_SHORT).show();
                return;
            }

            for (String answer : answers) {
                RadioButton rb = new RadioButton(getContext());
                rb.setText(answer);
                rb.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
                rb.setTextColor(getResources().getColor(android.R.color.black));
                rb.setTextSize(16); // Ajusta el tamaño del texto si es necesario
                answerRadioGroup.addView(rb);
            }

            answerRadioGroup.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.VISIBLE);
        } else {
            finishEvaluation();
        }
    }

    private void onNextButtonClick() {
        if (answerRadioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getContext(), "Por favor, selecciona una respuesta", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedRb = getView().findViewById(answerRadioGroup.getCheckedRadioButtonId());
        String selectedAnswer = selectedRb.getText().toString();

        if (selectedAnswer.equals(currentQuestions.get(currentQuestionIndex).getCorrectOption())) {
            score++;
        }

        currentQuestionIndex++;
        if (currentQuestionIndex < currentQuestions.size()) {
            displayQuestion();
        } else {
            finishEvaluation();
        }
    }

    private void finishEvaluation() {
        questionTextView.setText("Evaluación completada");
        answerRadioGroup.setVisibility(View.GONE);
        nextButton.setVisibility(View.GONE);

        // Guardar que el usuario completó la evaluación y el resultado
        SharedPreferences preferences = requireContext().getSharedPreferences("Evaluations", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String selectedTopic = evaluationSpinner.getSelectedItem().toString();

        editor.putBoolean(selectedTopic + "_completed", true); // Marcar el tema como completado
        editor.putInt(selectedTopic + "_score", score); // Guardar el puntaje
        editor.putInt(selectedTopic + "_totalQuestions", currentQuestions.size()); // Guardar total de preguntas
        editor.apply();

        // Mostrar un mensaje con la nota obtenida

        questionTextView.setText("Evaluación finalizada. Tu puntuación: " + score + " de " + currentQuestions.size());


    }
}