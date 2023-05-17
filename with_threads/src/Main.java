import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("My Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(2000, 800);

        JLabel welcomeLabel = new JLabel("Please, select the text file!");
        JButton button = new JButton("Select File");
        button.setPreferredSize(new Dimension(100, 30));
        JPanel verticalLabels = new JPanel(new GridLayout(5, 1, 0, 10));

        // create layout
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 0, 0);
        frame.add(welcomeLabel, gbc);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 0, 10, 0);
        frame.add(button, gbc);

        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 0, 10, 0);
        frame.add(verticalLabels, gbc);

        button.addActionListener(e -> {
            try {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();

                    // Read file contents
                    StringBuilder stringBuilder = new StringBuilder();
                    try (Scanner fileScanner = new Scanner(file)) {
                        while (fileScanner.hasNextLine()) {
                            stringBuilder.append(fileScanner.nextLine());
                        }
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                        return;
                    }

                    String text = stringBuilder.toString();

                    // Perform operations with the file contents
                    List<String> stopWords = Arrays.asList("a", "an", "the", "and", "or", "but");
                    // Convert text to lowercase
                    String cleanedText = text.toLowerCase();
                    // Remove punctuation marks
                    cleanedText = cleanedText.replaceAll("[\\p{Punct}]", "");
                    // Split text into words
                    String[] words = cleanedText.split("\\s+");
                    // Remove stop words
                    List<String> filteredWords = new ArrayList<>(Arrays.asList(words));
                    filteredWords.removeAll(stopWords);
                    // Join the remaining words into a string
                    String processedText = String.join(" ", filteredWords);

                    Registry registry = LocateRegistry.getRegistry("localhost", 4010);
                    Encyclopedia analyzer = (Encyclopedia) registry.lookup("Encyclopedia");

                    // Find number of letters
                    int numberOfLetters = analyzer.count(text);
                    verticalLabels.add(new JLabel("Number of letters: " + numberOfLetters)).setPreferredSize(new Dimension(1800, 50));

                    // Find repeated words
                    String repeatedWords = analyzer.repeatedWords(processedText);
                    verticalLabels.add(new JLabel("Repeated words: " + repeatedWords)).setPreferredSize(new Dimension(1800, 50));

                    // Find the longest word
                    String longestWord = analyzer.longest(processedText);
                    verticalLabels.add(new JLabel("Longest word: " + longestWord)).setPreferredSize(new Dimension(1800, 50));

                    // Find the shortest word
                    String shortestWord = analyzer.shortest(processedText);
                    verticalLabels.add(new JLabel("shortest word: " + shortestWord)).setPreferredSize(new Dimension(1800, 50));
                    // Find word frequency
                    String wordFrequency = analyzer.repeat(processedText);
                    verticalLabels.add(new JLabel("Word frequency: " + wordFrequency)).setPreferredSize(new Dimension(1800, 50));

                    // Update the frame to reflect the changes
                    frame.revalidate();
                    frame.repaint();
                }
            } catch (Exception ex) {
                System.err.println("RMI Client exception: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        frame.setVisible(true);
    }
}



