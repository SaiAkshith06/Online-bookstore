package gui;

import java.awt.*;
import javax.swing.*;

public class UIStyle {
    public static final Color bgColor = new Color(245, 245, 245);
    public static final Color accentColor = new Color(56, 117, 215);
    public static final Font defaultFont = new Font("Segoe UI", Font.PLAIN, 14);

    public static void stylePanel(JPanel panel) {
        panel.setBackground(bgColor);
    }

    public static void styleButton(JButton button) {
        button.setBackground(accentColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(defaultFont);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
    }

    public static void styleComponent(JComponent comp) {
        comp.setFont(defaultFont);
        comp.setForeground(Color.DARK_GRAY);
    }
}
