package gui;

import java.awt.*;
import javax.swing.*;
import models.*;
import services.*;

public class UserPanel extends JPanel {
    public UserPanel(UserService userService, MainFrame mainFrame) {
        setLayout(new GridBagLayout());
        setBackground(UIStyle.bgColor);

        JLabel titleLabel = new JLabel("Welcome to Mind Exercise – Online Bookstore");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(UIStyle.accentColor);

        JLabel nameLabel = new JLabel("Your Name:");
        JTextField nameField = new JTextField(20);

        JLabel emailLabel = new JLabel("Email Address:");
        JTextField emailField = new JTextField(20);

        JLabel phoneLabel = new JLabel("Phone Number:");
        JTextField phoneField = new JTextField(20);

        JButton startButton = new JButton("Start");
        JButton adminButton = new JButton("Admin Panel");

        // Style components
        UIStyle.styleComponent(nameLabel);
        UIStyle.styleComponent(emailLabel);
        UIStyle.styleComponent(phoneLabel);
        UIStyle.styleComponent(nameField);
        UIStyle.styleComponent(emailField);
        UIStyle.styleComponent(phoneField);

        startButton.setFont(UIStyle.defaultFont);
        startButton.setBackground(UIStyle.accentColor);
        startButton.setForeground(Color.WHITE);

        adminButton.setFont(UIStyle.defaultFont);
        adminButton.setBackground(Color.DARK_GRAY);
        adminButton.setForeground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.gridy = 0;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(nameLabel, gbc);
        gbc.gridx = 1;
        add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(emailLabel, gbc);
        gbc.gridx = 1;
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(phoneLabel, gbc);
        gbc.gridx = 1;
        add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(startButton, gbc);

        gbc.gridy = 5;
        add(adminButton, gbc);

        // ✅ Start button with email/phone validation
        startButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Missing Information", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // ✅ Email must end with @gmail.com
            if (!email.endsWith("@gmail.com")) {
                JOptionPane.showMessageDialog(this, "Please enter a valid Gmail address (ends with @gmail.com).", "Invalid Email", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // ✅ Phone must be numeric only
            if (!phone.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Please enter a valid phone number (digits only).", "Invalid Phone", JOptionPane.ERROR_MESSAGE);
                return;
            }

            User user = userService.registerUser(name, email, phone);
            mainFrame.getCatalogPanel().setUser(user);
            mainFrame.getCartPanel().setUser(user);
            mainFrame.showPanel("catalog");
        });

        // 🔐 Admin panel access with password
        adminButton.addActionListener(e -> {
            JPasswordField pwd = new JPasswordField();
            int option = JOptionPane.showConfirmDialog(this, pwd, "Enter Admin Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                String password = new String(pwd.getPassword());
                if (password.equals("admin123")) {
                    mainFrame.showPanel("admin");
                } else {
                    JOptionPane.showMessageDialog(this, "Incorrect password.", "Access Denied", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
