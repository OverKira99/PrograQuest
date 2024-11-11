package com.alejandrobel.prograquest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class EvaluationFragment extends Fragment {
    private AppDatabase db;
    private Question currentQuestion;
    private TextView textViewQuestion, textViewCode;
    private RadioGroup radioGroupAnswers;
    private RadioButton radioButtonAnswer1, radioButtonAnswer2, radioButtonAnswer3;
    private Button buttonSubmit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_evaluation, container, false);

        db = Room.databaseBuilder(requireContext(), AppDatabase.class, "app_database")
                .allowMainThreadQueries()
                .build();

        textViewQuestion = view.findViewById(R.id.textViewQuestion);
        textViewCode = view.findViewById(R.id.textViewCode);
        radioGroupAnswers = view.findViewById(R.id.radioGroupAnswers);
        radioButtonAnswer1 = view.findViewById(R.id.radioButtonAnswer1);
        radioButtonAnswer2 = view.findViewById(R.id.radioButtonAnswer2);
        radioButtonAnswer3 = view.findViewById(R.id.radioButtonAnswer3);
        buttonSubmit = view.findViewById(R.id.buttonSubmit);

        loadQuestionData(1);

        buttonSubmit.setOnClickListener(v -> {
            int selectedId = radioGroupAnswers.getCheckedRadioButtonId();
            RadioButton selectedRadioButton = view.findViewById(selectedId);

            if (selectedRadioButton != null && selectedRadioButton.getText().toString().equals(currentQuestion.getCorrectOption())) {
                Toast.makeText(getContext(), "Correcto", Toast.LENGTH_SHORT).show();
                saveScore(10);
            } else {
                Toast.makeText(getContext(), "Incorrecto", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void loadQuestionData(int id) {
        currentQuestion = db.questionDao().getQuestionById(id);
        if (currentQuestion != null) {
            textViewQuestion.setText(currentQuestion.getQuestion());
            textViewCode.setText(currentQuestion.getCodeExample());
            radioButtonAnswer1.setText(currentQuestion.getOption1());
            radioButtonAnswer2.setText(currentQuestion.getOption2());
            radioButtonAnswer3.setText(currentQuestion.getOption3());
        }
    }

    private void saveScore(int scoreValue) {
        Score score = new Score();
        score.setUserName("Usuario");
        score.setScore(scoreValue);
        db.scoreDao().insert(score);
    }
}
}