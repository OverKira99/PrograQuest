package com.alejandrobel.prograquest;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface QuestionDao {

    // Método para insertar múltiples preguntas
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Question> questions);

    // Método para obtener preguntas por tema
    @Query("SELECT * FROM questions WHERE topic = :topic")
    List<Question> getQuestionsByTopic(String topic);

    // Método para obtener todas las preguntas
    @Query("SELECT * FROM questions")
    List<Question> getAllQuestions();

    // Método para eliminar todas las preguntas
    @Query("DELETE FROM questions")
    void deleteAllQuestions();
}