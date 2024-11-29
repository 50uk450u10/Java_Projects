/* Sean Zamora
 * INEW-2338
 * 11/26/24
 * use generics to ensure type safety when managing the product catalog and implement custom exceptions to handle errors, such as invalid product data or exceeding inventory limits.
 */

 import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

 // Generic Product Catalog Class to store and manage products
 class ProductCatalog<T>{
    private final List<T> products = new ArrayList<>(); // List to store products

    // Method to add a product to the catalog
    public void addProduct(T product) throws DuplicateProductException{
        // Check if the product already exists in the catalog
        if(products.contains(product)){
            throw new DuplicateProductException("Product already exists in the catalog.");
        }
        // Add product to the list
        products.add(product);
    }

    // Method to retrieve all products in the catalog
    public List<T> getProducts(){
        return products;
    }

    // Method to remove a product from the catalog
    public void removeProduct(T product) throws ProductNotFoundException{
        // If the product is not found, throw an exception
        if(!products.remove(product)){
            throw new ProductNotFoundException("Product not found in the catalog.");
        }
    }
 }

 // Product class to represent an individual product with a name and price
 class Product{
    private final String name;
    private final double price;

    // Constructor to initialize the product with a name and price
    public Product(String name, double price){
        this.name = name;
        this.price = price;
    }

    // Override toString() to display product details as a string
    @Override
    public String toString(){
        return "Product{name='" + name + "', price=" + price + '}';
    }

    // Override equals() to compare products based on their name (ignores price)
    @Override
    public boolean equals(Object obj){
        if(this == obj) return true; // Check if both objects are the same instance
        if(obj == null || getClass() != obj.getClass()) return false; // Ensure object is of type Product
        Product product = (Product) obj;
        return name.equals(product.name); // Compare by product name only
    }
 }

// Custom Exception to handle duplicate products
class DuplicateProductException extends Exception{
    public DuplicateProductException(String message){
        super(message);
    }
}

//Custom Exception to handle the case when a product is not found in the catalog
class ProductNotFoundException extends Exception{
    public ProductNotFoundException(String message){
        super(message);
    }
}

// Main Application with GUI
public class ProductCatalogManager{
    private static final ProductCatalog<Product> catalog = new ProductCatalog<>(); // Catalog instance

    public static void main(String[] args) {
        // Use SwingUtilities to safely create the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            // Create the main JFrame window for the application
            JFrame frame = new JFrame("Product Catalog Manager");
            frame.setSize(400, 300);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            // Create a panel to hold all components
            JPanel panel = new JPanel();
            panel.setLayout(null);
            frame.add(panel);

            JLabel nameLabel = new JLabel("Product Name:");
            nameLabel.setBounds(10, 20, 100, 25);
            panel.add(nameLabel);

            JTextField nameField = new JTextField(20);
            nameField.setBounds(120, 20, 160, 25);
            panel.add(nameField);

            JLabel priceLabel = new JLabel("Price:");
            priceLabel.setBounds(10, 60, 100, 25);
            panel.add(priceLabel);

            JTextField priceField = new JTextField(20);
            priceField.setBounds(120, 60, 160, 25);
            panel.add(priceField);

            JButton addButton = new JButton("Add Product");
            addButton.setBounds(10, 100, 150, 25);
            panel.add(addButton);

            JButton viewButton = new JButton("View Catalog");
            viewButton.setBounds(180, 100, 150, 25);
            panel.add(viewButton);

            JButton removeButton = new JButton("Remove Product");
            removeButton.setBounds(10, 140, 150, 25);
            panel.add(removeButton);

            // Action listener for Add Product button
            addButton.addActionListener(e -> {
                String name = nameField.getText();
                double price;
                try{
                    price = Double.parseDouble(priceField.getText());
                    Product product = new Product(name, price);
                    catalog.addProduct(product);
                    JOptionPane.showMessageDialog(panel, "Product added successfully.");
                } catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(panel, "Invalid price. Please enter a number.");
                } catch (DuplicateProductException ex){
                    JOptionPane.showMessageDialog(panel, ex.getMessage());
                }
            });

            // Action listener for View Catalog button
            viewButton.addActionListener(e -> {
                List<Product> products = catalog.getProducts();
                if(products.isEmpty()){
                    JOptionPane.showMessageDialog(panel, "The catalog is empty.");
                } else{
                    StringBuilder productList = new StringBuilder();
                    for(Product product : products){
                        productList.append(product).append("\n");
                    }
                    JOptionPane.showMessageDialog(panel, productList.toString());
                }
            });

            // Action listener for Remove Product button
            removeButton.addActionListener(e -> {
                String name = nameField.getText();
                Product product = new Product(name, 0); // Price is irrelevant for removal
                try{
                    catalog.removeProduct(product);
                    JOptionPane.showMessageDialog(panel, "Product removed successfully.");
                } catch (ProductNotFoundException ex){
                    JOptionPane.showMessageDialog(panel, ex.getMessage());
                }
            });
        });
    }
}