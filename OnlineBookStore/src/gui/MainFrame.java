package gui;

import java.awt.*;
import javax.swing.*;
import models.*;
import services.*;

public class MainFrame extends JFrame {
    private UserPanel userPanel;
    private CatalogPanel catalogPanel;
    private CartPanel cartPanel;
    private AdminPanel adminPanel;

    public MainFrame() {
        setTitle("Mind Exercise – Online Bookstore");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen

        // Set Nimbus Look & Feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set background
        getContentPane().setBackground(UIStyle.bgColor);

        // Initialize services
        InventoryService inventoryService = new InventoryService();
        UserService userService = new UserService();

        // Add books with prices
        inventoryService.addBook(new Book("Java 101", "Sun Dev", 2, 399.00));
        inventoryService.addBook(new Book("DSA Made Easy", "Algo Guru", 3, 499.00));
        inventoryService.addBook(new Book("Clean Code", "Robert C. Martin", 4, 799.00));
        inventoryService.addBook(new Book("Head First Java", "Kathy Sierra", 5, 699.00));
        inventoryService.addBook(new Book("Effective Java", "Joshua Bloch", 3, 899.00));
        inventoryService.addBook(new Book("Java Concurrency", "Brian Goetz", 2, 749.00));
        inventoryService.addBook(new Book("Algorithms", "Sedgewick", 3, 599.00));
        inventoryService.addBook(new Book("Spring in Action", "Craig Walls", 4, 829.00));
        inventoryService.addBook(new Book("Design Patterns", "GoF", 2, 899.00));

        // Initialize panels
        userPanel = new UserPanel(userService, this);
        catalogPanel = new CatalogPanel(inventoryService, this);
        cartPanel = new CartPanel(this);
        adminPanel = new AdminPanel(inventoryService, this);

        // Set layout and add panels
        setLayout(new CardLayout());
        add(userPanel, "user");
        add(catalogPanel, "catalog");
        add(cartPanel, "cart");
        add(adminPanel, "admin");

        // Start with user screen
        showPanel("user");
    }

    public void showPanel(String name) {
        if ("cart".equals(name)) cartPanel.updateCartView();
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), name);
    }

    public CatalogPanel getCatalogPanel() {
        return catalogPanel;
    }

    public CartPanel getCartPanel() {
        return cartPanel;
    }

    public AdminPanel getAdminPanel() {
        return adminPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
