package com.alejandrobel.prograquest;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {

    private AppDatabase db;
    private ListView listViewScores;
    private Spinner spinnerTopics;
    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app_database")
                .allowMainThreadQueries()
                .build();

        listViewScores = findViewById(R.id.listViewScores);
        spinnerTopics = findViewById(R.id.spinnerTopics);

        spinnerTopics.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedTopic = parent.getItemAtPosition(position).toString();
                loadScores(selectedTopic);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No action needed
            }
        });
    }

    private void loadScores(String topic) {
        List<Score> scores = db.scoreDao().getScoresByTopic(topic);
        String[] scoreEntries = new String[scores.size()];

        for (int i = 0; i < scores.size(); i++) {
            Score score = scores.get(i);
            scoreEntries[i] = score.getUserName() + ": " + score.getScore();
        }

        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, scoreEntries);
        listViewScores.setAdapter(listAdapter);
    }
}