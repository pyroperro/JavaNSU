package tetris.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardsHandler {
    private static final String SCORES_FILE = "highscores.txt";
    private static final int MAX_ENTRIES = 20;
    private final List<HighscoreEntry> scores;

    public LeaderboardsHandler() {
        scores = new ArrayList<>();
        loadScores();
    }

    private void loadScores() {
        scores.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(SCORES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    int score = Integer.parseInt(parts[1].trim());
                    scores.add(new HighscoreEntry(name, score));
                }
            }
            sortAndTrim();
        } catch (Exception e) {
            System.out.println("No high scores found. Creating a new high score list.");
        }
    }

    private void sortAndTrim() {
        scores.sort((a, b) -> Integer.compare(b.score, a.score));
        if (scores.size() > MAX_ENTRIES) {
            scores.subList(MAX_ENTRIES, scores.size()).clear();
        }
    }

    private void saveScores() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORES_FILE))) {
            for (HighscoreEntry entry : scores) {
                writer.write(entry.name + ":" + entry.score);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving high scores: " + e.getMessage());
        }
    }

    public void addScore(String name, int score) {
        scores.add(new HighscoreEntry(name, score));
        sortAndTrim();
        saveScores();
    }

    public List<String> getHighScores() {
        List<String> scoreStrings = new ArrayList<>();
        for (HighscoreEntry entry : scores) {
            scoreStrings.add(entry.toString());
        }
        return scoreStrings;
    }

    public record HighscoreEntry(String name, int score) {
        @Override
        public String toString() {
            return name + ": " + score;
        }
    }
}
