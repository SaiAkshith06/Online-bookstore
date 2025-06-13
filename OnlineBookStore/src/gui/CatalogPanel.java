package gui;

import java.awt.*;
import java.io.File;
import javax.swing.*;
import models.*;
import services.*;

public class CatalogPanel extends JPanel {
    private InventoryService inventoryService;
    private User user;
    private JPanel booksPanel;

    public CatalogPanel(InventoryService inventoryService, MainFrame mainFrame) {
        this.inventoryService = inventoryService;
        UIStyle.stylePanel(this);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Browse Books");
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        label.setForeground(UIStyle.accentColor);
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label, BorderLayout.NORTH);

        booksPanel = new JPanel(new GridLayout(0, 3, 20, 20));
        UIStyle.stylePanel(booksPanel);
        JScrollPane scrollPane = new JScrollPane(booksPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        JButton cartButton = new JButton("Go to Cart");
        UIStyle.styleButton(cartButton);
        cartButton.addActionListener(e -> {
            if (user != null) {
                mainFrame.showPanel("cart");
            }
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(UIStyle.bgColor);
        bottomPanel.add(cartButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void setUser(User user) {
        this.user = user;
        refreshCatalog();
    }

    private void refreshCatalog() {
        booksPanel.removeAll();

        for (Book book : inventoryService.getAllBooks()) {
            JPanel card = new JPanel();
            card.setLayout(new BorderLayout(5, 5));
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

            // ✅ Load image using file path for dev mode
            String fileName = book.getTitle().toLowerCase().replace(" ", "_") + ".jpg";
            String imagePath = "src/resources/images/" + fileName;
            JLabel imageLabel;

            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                ImageIcon icon = new ImageIcon(imagePath);
                Image scaled = icon.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH);
                imageLabel = new JLabel(new ImageIcon(scaled));
            } else {
                imageLabel = new JLabel("No Image");
                imageLabel.setHorizontalAlignment(JLabel.CENTER);
            }

            JLabel titleLabel = new JLabel("<html><center>" + book.getTitle() + "<br>₹" + book.getPrice() + "</center></html>");
            titleLabel.setHorizontalAlignment(JLabel.CENTER);
            titleLabel.setFont(UIStyle.defaultFont);

            JButton addButton = new JButton("Add to Cart");
            UIStyle.styleButton(addButton);
            addButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            addButton.addActionListener(e -> {
                if (user != null) {
                    user.getCart().addBook(book);
                    JOptionPane.showMessageDialog(this, book.getTitle() + " added to cart.");
                }
            });

            JPanel bottom = new JPanel(new BorderLayout());
            bottom.setBackground(Color.WHITE);
            bottom.add(titleLabel, BorderLayout.CENTER);
            bottom.add(addButton, BorderLayout.SOUTH);

            card.add(imageLabel, BorderLayout.CENTER);
            card.add(bottom, BorderLayout.SOUTH);
            booksPanel.add(card);
        }

        booksPanel.revalidate();
        booksPanel.repaint();
    }
}
