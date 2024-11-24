package com.alejandrobel.prograquest;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "questions")
public class Question {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String question;
    private String codeExample;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String correctOption;
    private String topic; // Campo para almacenar el tema

    // Constructor vacío requerido por Room y Gson
    public Question() {
    }



    // Constructor completo
    public Question(String question, String codeExample, String option1, String option2, String option3,String option4, String correctOption, String topic) {
        this.question = question;
        this.codeExample = codeExample;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctOption = correctOption;
        this.topic = topic;
    }



    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCodeExample() {
        return codeExample;
    }

    public void setCodeExample(String codeExample) {
        this.codeExample = codeExample;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }
    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(String correctOption) {
        this.correctOption = correctOption;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<String> getAnswers() {
        List<String> answers = new ArrayList<>();
        if (option1 != null && !option1.isEmpty()) answers.add(option1);
        if (option2 != null && !option2.isEmpty()) answers.add(option2);
        if (option3 != null && !option3.isEmpty()) answers.add(option3);
        if (option4 != null && !option4.isEmpty()) answers.add(option4);
        return answers;
    }
}
