package gui;

import java.awt.*;
import javax.swing.*;
import models.*;

public class CartPanel extends JPanel {
    private JTextArea cartArea;
    private User user;

    public CartPanel(MainFrame mainFrame) {
        setLayout(new BorderLayout());
        setBackground(UIStyle.bgColor);

        cartArea = new JTextArea();
        cartArea.setFont(UIStyle.defaultFont);
        cartArea.setEditable(false);

        JButton purchaseButton = new JButton("Purchase");
        JButton backButton = new JButton("Back to Catalog");

        purchaseButton.setFont(UIStyle.defaultFont);
        purchaseButton.setBackground(UIStyle.accentColor);
        purchaseButton.setForeground(Color.WHITE);

        backButton.setFont(UIStyle.defaultFont);
        backButton.setBackground(UIStyle.accentColor);
        backButton.setForeground(Color.WHITE);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(UIStyle.bgColor);
        buttonPanel.add(purchaseButton);
        buttonPanel.add(backButton);

        JLabel label = new JLabel("Your Cart:");
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        label.setForeground(UIStyle.accentColor);

        add(label, BorderLayout.NORTH);
        add(new JScrollPane(cartArea), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // ✅ Purchase button with payment simulation
        purchaseButton.addActionListener(e -> {
            if (user == null || user.getCart().getBooks().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Your cart is empty.", "Purchase", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String[] methods = {"UPI", "Credit/Debit Card", "Net Banking"};
            String method = (String) JOptionPane.showInputDialog(this, "Choose Payment Method:", "Payment",
                    JOptionPane.PLAIN_MESSAGE, null, methods, methods[0]);

            if (method == null) return;

            String inputDetails = null;

            if (method.equals("UPI")) {
                inputDetails = JOptionPane.showInputDialog(this, "Enter UPI ID (e.g. yourname@upi):");
            } else if (method.equals("Credit/Debit Card")) {
                inputDetails = JOptionPane.showInputDialog(this, "Enter Card Number (xxxx-xxxx-xxxx-xxxx):");
            } else if (method.equals("Net Banking")) {
                inputDetails = JOptionPane.showInputDialog(this, "Enter Bank Username:");
            }

            if (inputDetails == null || inputDetails.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Payment failed: No details entered.", "Payment Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            StringBuilder result = new StringBuilder();
            double total = 0;

            for (Book book : user.getCart().getBooks()) {
                synchronized (book) {
                    if (book.purchase()) {
                        result.append("Bought: ").append(book.getTitle())
                              .append(" — ₹").append(book.getPrice()).append("\n");
                        total += book.getPrice();
                    } else {
                        result.append("Out of stock: ").append(book.getTitle()).append("\n");
                    }
                }
            }

            result.append("\nTotal Paid: ₹").append(total);
            result.append("\nPayment Mode: ").append(method);
            result.append("\nPayment Info: ").append(inputDetails);
            result.append("\n\nThank you for your purchase, ").append(user.getUsername()).append("!");

            JOptionPane.showMessageDialog(this, result.toString(), "Purchase Summary", JOptionPane.INFORMATION_MESSAGE);

            user.getCart().getBooks().clear();  // Clear cart
            updateCartView();                   // Refresh view
        });

        // Back button logic
        backButton.addActionListener(e -> {
            mainFrame.showPanel("catalog");
        });
    }

    public void setUser(User user) {
        this.user = user;
        updateCartView();
    }

    public void updateCartView() {
        cartArea.setText("");
        double total = 0;

        if (user != null) {
            for (Book book : user.getCart().getBooks()) {
                cartArea.append(book.getTitle() + " — ₹" + book.getPrice() + "\n");
                total += book.getPrice();
            }
            cartArea.append("\nTotal: ₹" + total);
        }
    }
}
