/* Sean Zamora
 * INEW 2338 - Advanced JAVA Programming
 * 10/29/24
 * Create a simple expense tracker in Java, utilizing a GUI to interface with
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class Expense{
    double amount;
    String category;
    String description;

    public Expense(double amount, String category, String description){
        this.amount = amount;
        this.category = category;
        this.description = description;
    }

    public double getAmount(){
        return amount;
    }

    public String getCategory(){
        return category;
    }

    public String getDescription(){
        return description;
    }

    public String toString(){
        return String.format("Category: %s, Amount: $%.2f, Description: %s", category, amount, description);
    }
}

class ExpenseTracker{
    ArrayList<Expense> expenses;

    public ExpenseTracker(){
        expenses = new ArrayList<>();
    }

    public void addExpense(Expense expense){
        expenses.add(expense);
    }

    public ArrayList<Expense> getExpenses(){
        return expenses;
    }

    public double getTotalExpenses(){
        double total = 0;
        for(Expense expense : expenses){
            total += expense.getAmount();
        }
        return total;
    }
}

public class ExpenseTrackerGUI extends JFrame{
    JTextField amountField;
    JTextField categoryField;
    JTextField descriptionField;
    JTextArea expenseArea;
    ExpenseTracker expenseTracker;

    public ExpenseTrackerGUI(){
        expenseTracker = new ExpenseTracker();
        setTitle("Personal Expense Tracker");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Create components
        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField(10);
        JLabel categoryLabel = new JLabel("Category:");
        categoryField = new JTextField(10);
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextField(10);
        JButton addButton = new JButton("Add Expense");
        JButton showButton = new JButton("Show Expenses");
        expenseArea = new JTextArea(10, 30);
        expenseArea.setEditable(false);

        // Add components to frame
        add(amountLabel);
        add(amountField);
        add(categoryLabel);
        add(categoryField);
        add(descriptionLabel);
        add(descriptionField);
        add(addButton);
        add(showButton);
        add(new JScrollPane(expenseArea));

        // Add action listeners
        addButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                addExpense();
            }
        });

        showButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                showExpenses();
            }
        });
    }

    void addExpense(){
        try{
            double amount = Double.parseDouble(amountField.getText());
            String category = categoryField.getText();
            String description = descriptionField.getText();

            if(amount < 0){
                throw new NumberFormatException();
            }

            Expense expense = new Expense(amount, category, description);
            expenseTracker.addExpense(expense);
            JOptionPane.showMessageDialog(this, "Expense added successfully!");

            //Clear fields
            amountField.setText("");
            categoryField.setText("");
            descriptionField.setText("");
        } catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this, "Invalid input! Please enter a positive number for amount.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showExpenses(){
        StringBuilder expenseList = new StringBuilder();
        for(Expense expense : expenseTracker.getExpenses()){
            expenseList.append(expense.toString()).append("\n");
        }
        expenseArea.setText(expenseList.toString());
        expenseArea.append(String.format("Total Expenses: $%.2f", expenseTracker.getTotalExpenses()));
    }
    
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            ExpenseTrackerGUI gui = new ExpenseTrackerGUI();
            gui.setVisible(true);
        });
    }
}