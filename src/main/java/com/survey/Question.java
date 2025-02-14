package com.survey;
import java.util.*;

class Question {
    private String text;
    private Map<String, Integer> responses;
    private int totalResponses = 0;
    private int skippedCount = 0;

    public Question(String text) {
        this.text = text;
        this.responses = new HashMap<>();
        responses.put("Agree", 0);
        responses.put("Slightly Agree", 0);
        responses.put("Slightly Disagree", 0);
        responses.put("Disagree", 0);
    }

    public String getText() {
        return text;
    }

    public void recordAnswer(String answer) {
        if (responses.containsKey(answer)) {
            responses.put(answer, responses.get(answer) + 1);
            totalResponses++;
        }
    }

    public void incrementSkippedCount() {
        skippedCount++;
    }

    public int getTotalResponses() {
        return totalResponses;
    }

    public int getSkippedCount() {
        return skippedCount;
    }

    public void displayResults() {
        System.out.println("Results for: " + text);
        for (Map.Entry<String, Integer> entry : responses.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public String getMostGivenAnswer() {
        return responses.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();
    }
}

