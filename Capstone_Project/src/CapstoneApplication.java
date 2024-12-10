/* Sean Zamora
 * INEW-2338
 * 12/9/24
 * Integrate components from previous modules into a fully functional portfolio application.
 * This application should feature user authentication, role-based access control, inventory management, logging, 
 * a directory search tool, and a social media-style feed. Additionally, implement multithreading for concurrent data processing, 
 * integrate a relational database for persistent storage, and apply design patterns for modular architecture.
 */

// Import necessary libraries for GUI, data structures, file handling, and Swing components
import java.awt.*;
import java.util.List;
import java.util.*;
import java.io.*;
import javax.swing.*;

// Class representing a user with a username, password, and role (e.g., regular user or admin)
class User {
    String username, password, role;

    // Constructor to initialize the user details
    public User(String username, String password, String role){
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getter methods for username, password, and role
    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getRole(){
        return role;
    }
}

// Class representing a product with a name and price
class Product {
    String name;
    double price;

    // Constructor to initialize product name and price
    public Product(String name, double price){
        this.name = name;
        this.price = price;
    }

    // Override the toString method to display product details
    @Override
    public String toString() {
        return String.format("Product{name='%s', price=%.2f}", name, price);
    }

}

// Main application class for the Capstone application
public class CapstoneApplication {
    // Static variables to store user data, posts, and products
    static Map<String, User> users = new HashMap<>();
    static List<String> posts = new ArrayList<>();
    static List<Product> products = new ArrayList<>();
    static final String LOG_FILE = "log.txt"; // Log file for storing actions

    public static void main(String[] args) {
        // Start the Swing GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            // Create a JFrame for the main application window
            JFrame frame = new JFrame("Capstone Application");
            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            // Create and display the login panel
            JPanel loginPanel = createLoginPanel(frame);
            frame.add(loginPanel);

            frame.setVisible(true); // Make the frame visible
        });
    }

    // Method to create the login panel
    static JPanel createLoginPanel(JFrame frame) {
        JPanel panel = new JPanel();
        panel.setLayout(null); // No layout manager for custom placement of components

        // Label and text field for the username
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(10, 20, 100, 25);
        panel.add(userLabel);
        JTextField userField = new JTextField(20);
        userField.setBounds(120, 20, 200, 25);
        panel.add(userField);

        // Label and password field for the password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 60, 100, 25);
        panel.add(passwordLabel);
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBounds(120, 60, 200, 25);
        panel.add(passwordField);
        
        // Login and Register buttons
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 100, 150, 25);
        panel.add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(180, 100, 150, 25);
        panel.add(registerButton);

        // Action listener for the Login button
        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passwordField.getPassword());
            if(users.containsKey(username) && users.get(username).getPassword().equals(password)){
                JOptionPane.showMessageDialog(panel, "Login successful!");
                logAction(username + " logged in"); // Log the login action
                // Switch to the dashboard panel
                frame.getContentPane().removeAll();
                frame.add(createDashboardPanel(frame, users.get(username)));
                frame.revalidate();
                frame.repaint();
            } else {
                JOptionPane.showMessageDialog(panel, "Invalid credentials!");
                logAction("Failed login attempt for username: " + username);
            }
        });

        // Action listener for the Register button
        registerButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passwordField.getPassword());
            if(!users.containsKey(username)){
                users.put(username, new User(username, password, "RegularUser"));
                JOptionPane.showMessageDialog(panel, "User registered successfully!");
                logAction("User " + username + " registered.");
            } else {
                JOptionPane.showMessageDialog(panel, "Username already exists!");
            }
        });

        return panel; // Return the login panel
    }

    // Method to create the dashboard panel
    static JPanel createDashboardPanel(JFrame frame, User user){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 3, 10, 10)); // Use a grid layout for buttons

        // Create buttons for different actions in the dashboard
        JButton productButton = new JButton("Product Catalog");
        JButton postsButton = new JButton("Social Feed");
        JButton searchButton = new JButton("Directory Search");
        JButton logButton = new JButton("View Logs");
        JButton logoutButton = new JButton("Logout");

        // Action listener for the Product Catalog button
        productButton.addActionListener(e -> {
            JFrame productFrame = createProductCatalogFrame();
            productFrame.setVisible(true);
        });

        // Action listener for the Social Feed button
        postsButton.addActionListener(e -> {
            JFrame postsFrame = createSocialFeedFrame();
            postsFrame.setVisible(true);
        });

        // Action listener for the Directory Search button
        searchButton.addActionListener(e -> {
            JFrame searchFrame = createDirectorySearchFrame();
            searchFrame.setVisible(true);
        });

        // Action listener for the View Logs button
        logButton.addActionListener(e -> {
            try {
                List<String> logs = readLogs(); // Read logs from file
                JTextArea logArea = new JTextArea(String.join("\n", logs));
                JScrollPane scrollPane = new JScrollPane(logArea);
                scrollPane.setPreferredSize(new Dimension(400, 300));
                JOptionPane.showMessageDialog(frame, scrollPane, "Application Logs", JOptionPane.INFORMATION_MESSAGE);
            } catch(IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error reading log file!");
            }
        });

        // Action listener for the Logout button
        logoutButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            frame.add(createLoginPanel(frame)); // Switch back to the login panel
            frame.revalidate();
            frame.repaint();
        });

        // Add buttons to the panel
        panel.add(productButton);
        panel.add(postsButton);
        panel.add(searchButton);
        panel.add(logButton);
        panel.add(logoutButton);

        return panel; // Return the dashboard panel
    }

    // Method to create the Product Catalog frame
    static JFrame createProductCatalogFrame(){
        JFrame frame = new JFrame("Product Catalog");
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Text area to display product catalog
        JTextArea productArea = new JTextArea();
        productArea.setEditable(false);
        refreshProductArea(productArea); // Refresh the product list

        // Input panel to add new products
        JPanel inputPanel = new JPanel();
        JTextField nameField = new JTextField(10);
        JTextField priceField = new JTextField(5);
        JButton addButton = new JButton("Add Product");

        // Add components to the input panel
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Price:"));
        inputPanel.add(priceField);
        inputPanel.add(addButton);

        // Action listener for the Add Product button
        addButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                products.add(new Product(name, price)); // Add the product to the list
                refreshProductArea(productArea); // Refresh the product list display
            } catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid price!");
            }
        });

        // Add the components to the frame
        panel.add(new JScrollPane(productArea), BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.SOUTH);

        frame.add(panel);
        return frame; // Return the product catalog frame
    }

    // Method to create the Social Feed frame
    static JFrame createSocialFeedFrame(){
        JFrame frame = new JFrame("Social Feed");
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Text area to display posts
        JTextArea postArea = new JTextArea();
        postArea.setEditable(false);
        refreshPostArea(postArea); // Refresh the posts list

        // Input panel to post new messages
        JPanel inputPanel = new JPanel();
        JTextField postField = new JTextField(20);
        JButton postButton = new JButton("Post");

        // Add components to the input panel
        inputPanel.add(new JLabel("Post:"));
        inputPanel.add(postField);
        inputPanel.add(postButton);

        // Action listener for the Post button
        postButton.addActionListener(e -> {
            String post = postField.getText();
            posts.add(post); // Add the new post to the list
            refreshPostArea(postArea); // Refresh the post list display
        });

        // Add the components to the frame
        panel.add(new JScrollPane(postArea), BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.SOUTH);

        frame.add(panel);
        return frame; // Return the social feed frame
    }

    // Method to create the Directory Search frame
    static JFrame createDirectorySearchFrame(){
        JFrame frame = new JFrame("Directory Search");
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        JLabel pathLabel = new JLabel("Path:");
        JTextField pathField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JTextArea resultArea = new JTextArea();

        // Add components to the panel
        panel.add(pathLabel);
        panel.add(pathField);
        panel.add(searchButton);
        panel.add(new JScrollPane(resultArea));

        // Action listener for the Search button
        searchButton.addActionListener(e -> {
            String path = pathField.getText();
            File dir = new File(path);
            if(dir.exists() && dir.isDirectory()){
                StringBuilder results = new StringBuilder();
                searchDirectory(dir, results); // Perform the directory search
                resultArea.setText(results.toString());
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid directory!");
            }
        });

        frame.add(panel);
        return frame; // Return the directory search frame
    }

    // Method to refresh the product display in the product catalog
    static void refreshProductArea(JTextArea area){
        StringBuilder content = new StringBuilder();
        for(Product product : products){
            content.append(product).append("\n"); // Append each product's details
        }
        area.setText(content.toString()); // Set the updated content in the text area
    }

    // Method to refresh the post display in the social feed
    static void refreshPostArea(JTextArea area){
        StringBuilder content = new StringBuilder();
        for(String post : posts){
            content.append(post).append("\n"); // Append each post
        }
        area.setText(content.toString()); // Set the updated content in the text area
    }

    // Recursive method to search through a directory and its subdirectories
    static void searchDirectory(File dir, StringBuilder results){
        for(File file : dir.listFiles()){
            if(file.isDirectory()){
                searchDirectory(file, results); // Recursively search subdirectories
            } else{
                results.append(file.getAbsolutePath()).append("\n"); // Add file path to results
            }
        }
    }

    // Method to log an action to the log file
    static void logAction(String action){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))){
            writer.write(action); // Write action to the log file
            writer.newLine();
        } catch(IOException e){
            System.out.println("Error writing to log file.");
        }
    }

    // Method to read the logs from the log file
    static List<String> readLogs() throws IOException{
        List<String> logs = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE))){
            String line;
            while((line = reader.readLine()) != null){
                logs.add(line); // Read each line from the log file
            }
        }
        return logs; // Return the list of logs
    }
}