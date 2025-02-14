package main.java.com.survey;

import java.util.*;

class SurveyManager {
    private List<Survey> surveys;
    private List<Candidate> candidates;
    private Map<String, Integer> candidateSurveyCount;
    private Scanner scanner;

    public SurveyManager() {
        this.surveys = new ArrayList<>();
        this.candidates = new ArrayList<>();
        this.candidateSurveyCount = new HashMap<>();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("\n--- Survey Management ---");
            System.out.println("1. Create Survey");
            System.out.println("2. Take Survey");
            System.out.println("3. Show Survey Results");
            System.out.println("4. Find Most Active Candidate");
            System.out.println("5. Remove Low-Response Questions");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> createSurvey();
                case 2 -> takeSurvey();
                case 3 -> showResults();
                case 4 -> findMostActiveCandidate();
                case 5 -> removeLowResponseQuestions();
                case 6 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void createSurvey() {
        System.out.print("Enter survey title: ");
        String title = scanner.nextLine();
        System.out.print("Enter survey topic: ");
        String topic = scanner.nextLine();
        System.out.print("Enter survey description: ");
        String description = scanner.nextLine();

        Survey survey = new Survey(title, topic, description);
        surveys.add(survey);

        int questionCount = 0;
        while (questionCount < 40) {
            System.out.print("Enter question (must have at least 10, type 'done' after 10 to stop): ");
            String question = scanner.nextLine();

            if (question.equalsIgnoreCase("done")) {
                if (questionCount >= 10) break;
                System.out.println("You need at least 10 questions before finishing.");
                continue;
            }
            survey.addQuestion(question);
            questionCount++;
        }

        System.out.println("Survey created successfully with " + questionCount + " questions.");
    }

    private void takeSurvey() {
        if (surveys.isEmpty()) {
            System.out.println("No surveys available.");
            return;
        }

        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your phone number: ");
        String phone = scanner.nextLine();

        Candidate candidate = new Candidate(firstName, lastName, email, phone);
        candidates.add(candidate);

        String candidateKey = firstName + " " + lastName;
        candidateSurveyCount.put(candidateKey, candidateSurveyCount.getOrDefault(candidateKey, 0) + 1);

        for (Survey survey : surveys) {
            survey.displaySurvey();
            for (Question q : survey.getQuestions()) {
                System.out.println("Answer for: " + q.getText());
                System.out.println("1. Agree  2. Slightly Agree  3. Slightly Disagree  4. Disagree  5. Skip");
                int answerChoice = scanner.nextInt();
                scanner.nextLine();

                if (answerChoice == 5) {
                    System.out.println("Question skipped.");
                    q.incrementSkippedCount();
                    continue;
                }

                String answer = switch (answerChoice) {
                    case 1 -> "Agree";
                    case 2 -> "Slightly Agree";
                    case 3 -> "Slightly Disagree";
                    case 4 -> "Disagree";
                    default -> "";
                };

                candidate.answerQuestion(q, answer);
            }
        }
    }

    private void showResults() {
        if (surveys.isEmpty()) {
            System.out.println("No surveys available.");
            return;
        }

        for (Survey survey : surveys) {
            survey.displaySurvey();
            for (Question q : survey.getQuestions()) {
                q.displayResults();
                System.out.println("Most common answer: " + q.getMostGivenAnswer());
                System.out.println("-------------------------");
            }
        }
    }

    private void findMostActiveCandidate() {
        if (candidateSurveyCount.isEmpty()) {
            System.out.println("No surveys taken yet.");
            return;
        }

        String topCandidate = Collections.max(candidateSurveyCount.entrySet(), Map.Entry.comparingByValue()).getKey();
        int count = candidateSurveyCount.get(topCandidate);

        System.out.println("Candidate who has taken the most surveys: " + topCandidate + " (" + count + " surveys)");
    }

    private void removeLowResponseQuestions() {
        for (Survey survey : surveys) {
            survey.getQuestions().removeIf(q -> {
                int totalAttempts = q.getTotalResponses() + q.getSkippedCount();
                if (totalAttempts > 0 && q.getTotalResponses() < totalAttempts / 2) {
                    System.out.println("Removing question: " + q.getText() + " (answered by less than 50% of candidates)");
                    return true;
                }
                return false;
            });
        }
    }
}


