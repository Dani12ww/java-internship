package main.java.com.survey;

import java.util.*;

class Candidate {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Map<Question, String> answers;

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Candidate(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.answers = new HashMap<>();
    }

    public void answerQuestion(Question question, String answer) {
        answers.put(question, answer);
        question.recordAnswer(answer);
    }

    public void displayAnswers() {
        System.out.println("\nCandidate: " + firstName + " " + lastName);
        for (Map.Entry<Question, String> entry : answers.entrySet()) {
            System.out.println(entry.getKey().getText() + " -> " + entry.getValue());
        }
    }
}
