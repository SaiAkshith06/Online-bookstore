package gui;

import java.awt.*;
import javax.swing.*;
import models.*;
import services.*;

public class AdminPanel extends JPanel {
    private InventoryService inventoryService;
    private JTextArea inventoryArea;

    public AdminPanel(InventoryService inventoryService, MainFrame mainFrame) {
        this.inventoryService = inventoryService;
        setLayout(new BorderLayout());
        setBackground(UIStyle.bgColor);

        JLabel heading = new JLabel("Admin Panel – Manage Inventory");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 18));
        heading.setForeground(UIStyle.accentColor);
        add(heading, BorderLayout.NORTH);

        inventoryArea = new JTextArea();
        inventoryArea.setEditable(false);
        inventoryArea.setFont(UIStyle.defaultFont);
        add(new JScrollPane(inventoryArea), BorderLayout.CENTER);

        JButton addButton = new JButton("Add Book");
        JButton removeButton = new JButton("Remove Book");
        JButton refreshButton = new JButton("Refresh");
        JButton backButton = new JButton("Back to User");

        for (JButton btn : new JButton[]{addButton, removeButton, refreshButton, backButton}) {
            btn.setFont(UIStyle.defaultFont);
            btn.setBackground(UIStyle.accentColor);
            btn.setForeground(Color.WHITE);
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(UIStyle.bgColor);
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add Book button
        addButton.addActionListener(e -> {
            String bookTitle = JOptionPane.showInputDialog(this, "Book Title:");
            String author = JOptionPane.showInputDialog(this, "Author:");
            String priceStr = JOptionPane.showInputDialog(this, "Price (₹):");
            String stockStr = JOptionPane.showInputDialog(this, "Stock:");

            if (bookTitle != null && author != null && priceStr != null && stockStr != null) {
                try {
                    double price = Double.parseDouble(priceStr);
                    int stock = Integer.parseInt(stockStr);
                    inventoryService.addBook(new Book(bookTitle, author, stock, price));
                    JOptionPane.showMessageDialog(this, "Book added.");
                    refreshInventory();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid number input.");
                }
            }
        });

        // Remove Book button
        removeButton.addActionListener(e -> {
            String bookTitle = JOptionPane.showInputDialog(this, "Enter book title to remove:");
            if (bookTitle != null && inventoryService.removeBook(bookTitle)) {
                JOptionPane.showMessageDialog(this, "Book removed.");
                refreshInventory();
            } else {
                JOptionPane.showMessageDialog(this, "Book not found.");
            }
        });

        refreshButton.addActionListener(e -> refreshInventory());
        backButton.addActionListener(e -> mainFrame.showPanel("user"));

        refreshInventory();
    }

    private void refreshInventory() {
        inventoryArea.setText("");
        for (Book book : inventoryService.getAllBooks()) {
            inventoryArea.append(book.getTitle() + " — ₹" + book.getPrice() + " (" + book.getStock() + " in stock)\n");
        }
    }
}
