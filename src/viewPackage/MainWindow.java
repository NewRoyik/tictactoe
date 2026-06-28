package viewPackage;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private JPanel mainPanel;
    private TicTacToePanel ticTacToePanel = new TicTacToePanel();
    private WelcomePanel welcomePanel = new WelcomePanel(this, ticTacToePanel);

    public MainWindow() {
        super("Tic-Tac-Toe");

        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("resources/tictactoePic.png").getImage());

        createMenuBar();

        mainPanel = new JPanel(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        showPanel(welcomePanel);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu basicMenu = new JMenu("TicTacToe");

        JMenuItem homeItem = new JMenuItem("Accueil");
        homeItem.addActionListener(e -> showPanel(welcomePanel));
        basicMenu.add(homeItem);

        JMenuItem helpItem = new JMenuItem("Aide");
        helpItem.addActionListener(e -> showHelpMessage());
        basicMenu.add(helpItem);
        basicMenu.addSeparator();

        JMenuItem quitItem = new JMenuItem("Quitter");
        quitItem.addActionListener(e -> System.exit(0));
        basicMenu.add(quitItem);

        menuBar.add(basicMenu);
        setJMenuBar(menuBar);
    }

    public void showPanel(JPanel panel) {
        mainPanel.removeAll();
        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showHelpMessage() {
        JOptionPane.showMessageDialog(
                this,
                "Bienvenue dans l'application du jeu TicTacToe\n\n" +
                        "Besoin d'aide ? Contactez moi :\n" +
                        "yorik.babuder@gmail.com",
                "Aide",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
