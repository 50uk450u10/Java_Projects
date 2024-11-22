/*
 * Sean Zamora
 * INEW-2338
 * 11/19/24
 * The program should search the directory and all subdirectories for files matching the extension and display the file paths.
 */

 import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DirectorySearchTool {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Directory Search Tool");
            frame.setSize(500, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel panel = new JPanel();
            panel.setLayout(null);
            frame.add(panel);

            // Components for Directory Path
            JLabel dirLabel = new JLabel("Directory:");
            dirLabel.setBounds(10, 20, 80, 25);
            panel.add(dirLabel);

            JTextField dirField = new JTextField(20);
            dirField.setBounds(100, 20, 250, 25);
            panel.add(dirField);

            JButton browseButton = new JButton("Browse");
            browseButton.setBounds(360, 20, 100, 25);
            panel.add(browseButton);

            // Components for File Extension
            JLabel extLabel = new JLabel("Extension:");
            extLabel.setBounds(10, 60, 80, 25);
            panel.add(extLabel);

            JTextField extField = new JTextField(10);
            extField.setBounds(100, 60, 100, 25);
            panel.add(extField);

            // Search Button
            JButton searchButton = new JButton("Search");
            searchButton.setBounds(210, 60, 100, 25);
            panel.add(searchButton);

            // Text Area for Results
            JTextArea resultArea = new JTextArea();
            resultArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(resultArea);
            scrollPane.setBounds(10, 100, 450, 250);
            panel.add(scrollPane);

            // Browse Button Action
            browseButton.addActionListener(e -> {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int option = fileChooser.showOpenDialog(frame);
                if(option == JFileChooser.APPROVE_OPTION){
                    File selectedDirectory = fileChooser.getSelectedFile();
                    dirField.setText(selectedDirectory.getAbsolutePath());
                }
            });

            // Search Button Action
            searchButton.addActionListener(e -> {
                String dirPath = dirField.getText();
                String extension = extField.getText();

                if(dirPath.isEmpty() || extension.isEmpty()){
                    JOptionPane.showMessageDialog(panel, "Please enter both directory path and file extension.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                File directory = new File(dirPath);
                if(!directory.exists() || !directory.isDirectory()){
                    JOptionPane.showMessageDialog(panel, "Invalid directory path.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Perform search
                List<String> results = new ArrayList<>();
                searchDirectory(directory, extension, results);

                // Display results or no results message
                if(results.isEmpty()){
                    resultArea.setText("No files found with extension: " + extension);
                } else {
                    StringBuilder resultText = new StringBuilder("Files found:\n");
                    for(String filePath : results){
                        resultText.append(filePath).append("\n");
                    }
                    resultArea.setText(resultText.toString());
                }
            });
            frame.setVisible(true);
        });
    }

    // Recursive method to search directory
    private static void searchDirectory(File directory, String extension, List<String> results){
        // Ensure the directory is accessible and handle potential security errors
        try {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        searchDirectory(file, extension, results); // Recursive call for subdirectories
                    } else if (file.getName().endsWith(extension)) {
                        results.add(file.getAbsolutePath()); // Add matching file path to results
                    }
                }
            }
        } catch (SecurityException e) {
            System.out.println("Permission denied: " + directory.getAbsolutePath());
        }
    }
}