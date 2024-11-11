package com.alejandrobel.prograquest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class EvaluationFragment extends Fragment {

    private List<QuestionSet> questionSets; // Contendrá todas las preguntas categorizadas por tema

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_evaluation, container, false);

        loadQuestionsFromJson();

        // Prueba: Muestra las preguntas de un tema específico (IF)
        if (questionSets != null) {
            List<Question> ifQuestions = getQuestionsByTopic("IF");
            if (!ifQuestions.isEmpty()) {
                Toast.makeText(getContext(), "Cargadas " + ifQuestions.size() + " preguntas de IF", Toast.LENGTH_SHORT).show();
            }
        }

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

    private List<Question> getQuestionsByTopic(String topic) {
        for (QuestionSet set : questionSets) {
            if (set.getTopic().equalsIgnoreCase(topic)) {
                return set.getQuestions();
            }
        }
        return null;
    }
}
