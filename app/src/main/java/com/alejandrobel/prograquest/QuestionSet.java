package com.alejandrobel.prograquest;
import java.util.List;

public class QuestionSet {
    private String topic;
    private List<Question> questions;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
