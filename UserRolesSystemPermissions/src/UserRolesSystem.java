/*
 * Sean Zamora
 * INEW 2338
 * 11/12/24
 * Extend the user authentication system to include user roles (e.g., Admin, Regular User, Guest) and permissions. Each role should have specific permissions, such as accessing, modifying, or deleting data.
 */

 import javax.swing.*;
 import java.util.HashMap;
 import java.util.Map;

 // Custom exception to handle role-based access violations
 class RoleAccessException extends Exception {
    // Constructor that takes a message
    public RoleAccessException(String message) {
        super(message);
    }
}

 abstract class User{
    protected String username;
    public User(String username) { this.username = username; }
    // Abstract method to get permissions of the user (must be implemented by subclasses)
    public abstract String getPermissions() throws RoleAccessException;
    // Abstract method to perform an action (must be implemented by subclasses)
    public abstract void performAction() throws RoleAccessException;
 }

 class Admin extends User{
    public Admin(String username) { super(username); }
    @Override
    public String getPermissions() { return "Admin: Full access"; }
    public void performAction() throws RoleAccessException{
        // Admin can perform all actions, so no exception is thrown
    }
 }

 class RegularUser extends User{
    public RegularUser(String username) { super(username); }
    @Override
    public String getPermissions() { return "User: Limited access"; }
    public void performAction() throws RoleAccessException{
        // Users have limited access, so they can't perform admin-level actions
        throw new RoleAccessException("You do not have sufficient permissions.");
    }
 }

 class Guest extends User{
    public Guest(String username) { super(username); }
    @Override
    public String getPermissions() { return "Guest: View-only access"; }
    public void performAction() throws RoleAccessException {
        // Guests can only view things, so they can't perform actions that require higher permissions
        throw new RoleAccessException("Guests cannot perform actions beyond viewing.");
    }
 }

 public class UserRolesSystem{
    // A map to store registered users
    private static Map<String, User> users = new HashMap<>();

    public static void main(String[] args){
        // Create a frame for the app window
        JFrame frame = new JFrame("User Roles System");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel to hold the components in the window
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    // Method to place all UI components on the panel
    private static void placeComponents(JPanel panel){
        panel.setLayout(null);

        // Label for the "Username" field
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        // Text field for entering the username
        JTextField userText = new JTextField(20);
        userText.setBounds(100, 20, 160, 25);
        panel.add(userText);

        // Label for the "Role" dropdown
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(10, 50, 80, 25);
        panel.add(roleLabel);

        // Dropdown to select the user role
        JComboBox<String> roleBox = new JComboBox<>(new String[]{ "Admin", "RegularUser", "Guest"});
        roleBox.setBounds(100, 50, 160, 25);
        panel.add(roleBox);

        // Button that registeres the user based on their username and role
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(10, 90, 120, 25);
        registerButton.addActionListener(e -> {
            String username = userText.getText();
            String role = (String) roleBox.getSelectedItem();
            User user;

            // Create the appropriate user object based on selected role
            if("Admin".equals(role)){
                user = new Admin(username);
            } else if ("RegularUser".equals(role)){
                user = new RegularUser(username);
            } else {
                user = new Guest(username);
            }
            users.put(username, user);
            JOptionPane.showMessageDialog(panel, username + " registered as " + role);
        });
        panel.add(registerButton);

        // Button to view the permissions of the user
        JButton viewPermissionsButton = new JButton("View Permissions");
        viewPermissionsButton.setBounds(150, 90, 140, 25);
        viewPermissionsButton.addActionListener(e -> {
            String username = userText.getText();
            User user = users.get(username);

            // Check if the user exists
            if (user != null) {
                try {
                    JOptionPane.showMessageDialog(panel, user.getPermissions());
                } catch (RoleAccessException ex) {
                    JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(panel, "User not found.");
            }
        });
        panel.add(viewPermissionsButton);

        // Button to perform an action
        JButton performActionButton = new JButton("Perform Action");
        performActionButton.setBounds(150, 120, 140, 25);
        performActionButton.addActionListener(e -> {
            String username = userText.getText();
            User user = users.get(username);

            // Check if user exists
            if (user != null) {
                try {
                    user.performAction(); // Check if the user can perform the action
                    JOptionPane.showMessageDialog(panel, "Action performed successfully.");
                } catch (RoleAccessException ex) {
                    JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(panel, "User not found.");
            }
        });
        panel.add(performActionButton);
    }
 }