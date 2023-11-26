package com.diobriggs.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;

public class Start {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                StartPage startPage = new StartPage();
                startPage.setVisible(true);
            }
        });
    }
}

class StartPage extends JFrame {
    public StartPage() {
        setTitle("8192 Start Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 750);
        setLocationRelativeTo(null);

        // Create a panel with a custom background color
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw a gradient background
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(0xf28007), 0, getHeight(), new Color(0xf28007));
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Add padding

        // Create a title label
        JLabel titleLabel = new JLabel("8192");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        // Create a start button with custom styling
        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 18));
        startButton.setBackground(new Color(0xff1212)); // Green color
        startButton.setForeground(Color.BLACK);
        startButton.setFocusPainted(false);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the start page
                dispose();

                // Start the game
                Game game = new Game();
                JFrame gameWindow = new JFrame("8192");
                gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gameWindow.setResizable(false);
                gameWindow.add(game);
                gameWindow.pack();
                gameWindow.setLocationRelativeTo(null);
                gameWindow.setVisible(true);
                game.start();
            }
        });

        // Add components to the panel
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(startButton, BorderLayout.CENTER);

        // Add the panel to the frame
        add(panel);
    }
}