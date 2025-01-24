import java.io.*;
import java.util.*;

public class WordCounter {
    private HashMap<String, Integer> data;

    public WordCounter() {
        this.data = new HashMap<>();
    }

    public void readData(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int character;
            StringBuilder word = new StringBuilder();
            while ((character = reader.read()) != -1) {
                if (Character.isLetterOrDigit(character)) {
                    word.append((char)character);
                } else {
                    if (word.isEmpty()) continue;
                    String wordString = word.toString().toLowerCase();
                    data.put(wordString, data.getOrDefault(wordString, 0) + 1);
                    word.setLength(0);
                }
            }
            if (!word.isEmpty()) {
                String wordString = word.toString().toLowerCase();
                data.put(wordString, data.getOrDefault(wordString, 0) + 1);
            }
        } catch (FileNotFoundException exc) {
            System.err.println("File not found!");
        } catch (IOException exc) {
            System.err.println("Error while opening file: " + exc.getMessage());
        }
    }

    public void writeData(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            int totalWordCount = 0;
            for (int wordCount : data.values()) {
                totalWordCount += wordCount;
            }

            List<Map.Entry<String, Integer>> entryList = new ArrayList<>(data.entrySet());
            entryList.sort(Map.Entry.<String, Integer>comparingByValue().reversed());
            for (Map.Entry<String, Integer> entry : entryList) {
                String line = String.format("%s,%d,%.2f\n", entry.getKey(), entry.getValue(), ((double)  entry.getValue() / totalWordCount * 100));
                writer.write(line);
            }
        } catch (IOException exc) {
            System.err.println("Error writing file: " + exc.getMessage());
        }
    }
}
