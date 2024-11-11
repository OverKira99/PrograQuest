package com.alejandrobel.prograquest;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface QuestionDao {

    @Insert
    void insert(Question question);

    @Query("SELECT * FROM questions WHERE topic = :topic")
    List<Question> getQuestionsByTopic(String topic);


    @Query("SELECT * FROM questions WHERE id = :id")
    Question getQuestionById(int id);

    @Query("SELECT * FROM questions")
    List<Question> getAllQuestions();
}
