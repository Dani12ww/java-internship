package com.survey;
import org.apache.commons.lang3.StringUtils;
import java.util.*;

class Survey {
    private String title;
    private String topic;
    private String description;
    private List<Question> questions;

    public Survey(String title, String topic, String description) {
        this.title = title;
        this.topic = topic;
        this.description = description;
        this.questions = new ArrayList<>();
    }

    public void addQuestion(String text) {
        if (StringUtils.isBlank(text)) {
            System.out.println("Error: Question cannot be blank.");
            return;
        }

        if (questions.size() >= 40) {
            System.out.println("Cannot add more than 40 questions.");
            return;
        }

        for (Question q : questions) {
            if (q.getText().equalsIgnoreCase(text)) {
                System.out.println("Question already exists.");
                return;
            }
        }
        questions.add(new Question(text));
    }


    public void removeQuestion(String text) {
        questions.removeIf(q -> q.getText().equalsIgnoreCase(text));
    }

    public void displaySurvey() {
        System.out.println("\nSurvey: " + title + "\nTopic: " + topic + "\nDescription: " + description);
        for (int i = 0; i < questions.size(); i++) {
            System.out.println((i + 1) + ". " + questions.get(i).getText());
        }
    }

    public List<Question> getQuestions() {
        return questions;
    }
}

