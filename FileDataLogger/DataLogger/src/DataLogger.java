/*
 * Sean Zamora
 * INEW-2338
 * 11/5/24
 * Design a data logger program that records and retrieves user actions from a file.
 */

import javax.swing.*;
import java.io.*;
//import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DataLogger {
    static final String FILE_PATH = "log.txt";

    public static void main(String[] args) {

        //Creation of GUI
        JFrame frame = new JFrame("Data Logger");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    //Fill out GUI panel with components
    static void placeComponents(JPanel panel){
        panel.setLayout(null);

        //Prompt user to enter a log
        JLabel logLabel = new JLabel("Enter log entry:");
        logLabel.setBounds(10, 20, 100, 25);
        panel.add(logLabel);

        //Create new text object for an entry
        JTextField logText = new JTextField(20);
        logText.setBounds(120, 20, 160, 25);
        panel.add(logText);

        //Button created to add the entered text to the log
        JButton addLogButton = new JButton("Add Log");
        addLogButton.setBounds(290, 20, 90, 25);
        addLogButton.addActionListener(e -> {
            String logEntry = logText.getText();
            addLog(logEntry);
            JOptionPane.showMessageDialog(panel, "Log added!");
        });
        panel.add(addLogButton);

        //Button created to display stored logs from associated text file
        JButton showLogsButton = new JButton("Show Logs");
        showLogsButton.setBounds(10, 60, 100, 25);
        showLogsButton.addActionListener(e -> showLogs(panel));
        panel.add(showLogsButton);

        // Button created to delete a log
        JButton deleteLogButton = new JButton("Delete Log");
        deleteLogButton.setBounds(10, 100, 120, 25);
        deleteLogButton.addActionListener(e -> {
            String logToDelete = JOptionPane.showInputDialog(panel, "Enter log number to delete:");
            if (logToDelete != null) {
                try {
                    int logIndex = Integer.parseInt(logToDelete) - 1; // Convert to 0-based index
                    deleteLog(logIndex);
                    JOptionPane.showMessageDialog(panel, "Log deleted!");
                } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(panel, "Invalid log number!");
                }
            }
        });
        panel.add(deleteLogButton);

        // Button to clear all logs
        JButton clearLogsButton = new JButton("Clear All Logs");
        clearLogsButton.setBounds(140, 100, 120, 25);
        clearLogsButton.addActionListener(e -> {
            int confirmation = JOptionPane.showConfirmDialog(panel, "Are you sure you want to clear all logs?", "Confirm Clear", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                clearLogs();
                JOptionPane.showMessageDialog(panel, "All logs cleared!");
            }
        });
        panel.add(clearLogsButton);
    }

    //Method to add logs to text file
    static void addLog(String logEntry){
        //try-catch exception handling for errors
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))){
            //This method may only write to the file
            writer.write(logEntry);
            writer.newLine();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    //Method to display stored logs from text file
    static void showLogs(JPanel panel){
        List<String> logs = new ArrayList<>();
        //try-catch exception handling for errors
        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))){
            String line;
            //while loop continues to read lines from the text file until a blank line is read
            while((line = reader.readLine()) != null){
                logs.add(line);
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(panel, String.join("\n", logs));
    }

    // Method to delete a specific log entry by index
    static void deleteLog(int logIndex) {
        List<String> logs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logs.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Remove the log entry at the specified index
        if (logIndex >= 0 && logIndex < logs.size()) {
            logs.remove(logIndex);
        } else {
            return; // If the index is out of bounds, return without making changes
        }

        // Rewrite the file without the deleted log
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String log : logs) {
                writer.write(log);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to clear all logs from the file
    static void clearLogs() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            // Simply overwrite the file with nothing to clear it
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
