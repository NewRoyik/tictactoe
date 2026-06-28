package viewPackage;

import javax.swing.*;
import java.awt.*;
import java.net.URI;

public class WelcomePanel extends JPanel {
    public WelcomePanel(MainWindow mainWindow, TicTacToePanel ticTacToePanel) {
        setLayout(new BorderLayout());
        setBackground(Color.GRAY);


        JLabel titleLabel = new JLabel("TicTacToe", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));


        ImageIcon icon = new ImageIcon("resources/tictactoePic.png");
        JLabel imageLabel = new JLabel(icon, SwingConstants.CENTER);

        JButton startButton = new JButton("Jouer une partie !");
        startButton.setFont(new Font("Arial", Font.BOLD, 15));
        startButton.setBackground(Color.BLACK);
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setPreferredSize(new Dimension(200, 50));
        startButton.addActionListener(e -> mainWindow.showPanel(ticTacToePanel));

        JButton rulesButton = new JButton("Règles complètes");
        rulesButton.setFont(new Font("Arial", Font.PLAIN, 15));
        rulesButton.setPreferredSize(new Dimension(200, 50));
        rulesButton.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://www.regles.com/jeux/tic-tac-toe.html"));
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(this, "Problème avec l'URL", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonsPanel.setOpaque(false);
        buttonsPanel.add(startButton);
        buttonsPanel.add(rulesButton);


        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(titleLabel, BorderLayout.NORTH);
        centerPanel.add(imageLabel, BorderLayout.CENTER);
        centerPanel.add(buttonsPanel, BorderLayout.SOUTH);
        centerPanel.setOpaque(false);

        add(centerPanel, BorderLayout.CENTER);
    }

}
