package com.alejandrobel.prograquest;

import android.content.Context;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.alejandrobel.prograquest.QuestionDao;
import com.alejandrobel.prograquest.ScoreDao;
import com.alejandrobel.prograquest.Question;
import com.alejandrobel.prograquest.Score;
import com.alejandrobel.prograquest.JsonHelper;

import java.util.List;

@Database(entities = {Question.class, Score.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract QuestionDao questionDao();
    public abstract ScoreDao scoreDao();  // Incluimos el ScoreDao

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "app_database")
                            .addCallback(roomCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new Thread(() -> {
                populateDatabase(INSTANCE.questionDao(), contextInstance);
            }).start();
        }
    };

    private static void populateDatabase(QuestionDao questionDao, Context context) {
        List<Question> questions = JsonHelper.loadQuestionsFromJson(context);
        questionDao.insertAll(questions);
    }

    private static Context contextInstance;

    public static void initializeContext(Context context) {
        contextInstance = context.getApplicationContext();
    }
}