package com.alejandrobel.prograquest;

import android.os.Bundle;
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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
            Type listType = new TypeToken<List<QuestionSet>>() {}.getType();
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
        currentQuestions = getQuestionsByTopic(selectedTopic);
        if (currentQuestions == null || currentQuestions.isEmpty()) {
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
            for (String answer : question.getAnswers()) {
                RadioButton rb = new RadioButton(getContext());
                rb.setText(answer);
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

        TextView resultTextView = new TextView(getContext());
        resultTextView.setText("Puntuación: " + score + " de " + currentQuestions.size());
        ((ViewGroup) getView()).addView(resultTextView);

        Toast.makeText(getContext(), "Evaluación finalizada", Toast.LENGTH_SHORT).show();
    }
}