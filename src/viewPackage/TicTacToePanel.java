package viewPackage;

import javax.swing.*;
import java.awt.*;
import controllerPackage.TicTacToeController;
import modelPackage.GameState;
import modelPackage.WinType;

public class TicTacToePanel extends JPanel {
    private Boolean hasPlayerSelectedX = null;
    private JButton[][] buttons;
    private int gridSize = 3;
    private JPanel mainPanel;
    private JPanel centerPanel;
    private TicTacToeController controller = TicTacToeController.getInstance();

    public TicTacToePanel() {
        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY);

        JLabel titleLabel = new JLabel("TicTacToe", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JLabel pawnLabel = new JLabel("Choisissez votre pion");
        pawnLabel.setFont(new Font("Arial", Font.PLAIN, 15));

        JButton xButton = new JButton("X");
        xButton.setFont(new Font("Arial", Font.PLAIN, 30));
        xButton.setForeground(Color.BLUE);
        xButton.setBackground(new Color(173,216,230));
        xButton.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 139), 2));
        xButton.setPreferredSize(new Dimension(80, 60));

        JButton oButton = new JButton("O");
        oButton.setFont(new Font("Arial", Font.PLAIN, 30));
        oButton.setForeground(Color.RED);
        oButton.setBackground(new Color(255, 127, 127));
        oButton.setBorder(BorderFactory.createLineBorder(new Color(139, 0, 0), 2));
        oButton.setPreferredSize(new Dimension(80, 60));

        xButton.addActionListener(e -> {
            hasPlayerSelectedX = true;
            xButton.setFont(new Font("Arial", Font.BOLD, 45));
            oButton.setFont(new Font("Arial", Font.PLAIN, 30));
        });
        oButton.addActionListener(e -> {
            hasPlayerSelectedX = false;
            oButton.setFont(new Font("Arial", Font.BOLD, 45));
            xButton.setFont(new Font("Arial", Font.PLAIN, 30));
        });

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        buttonsPanel.setOpaque(false);
        buttonsPanel.add(pawnLabel);
        buttonsPanel.add(xButton);
        buttonsPanel.add(oButton);


        JLabel sizeLabel = new JLabel("Taille");
        sizeLabel.setFont(new Font("Arial", Font.PLAIN, 15));

        JComboBox<Integer> sizeComboBox = new JComboBox<>(new Integer[]{3, 4, 5, 6, 7, 8, 9});
        sizeComboBox.setFont(new Font("Arial", Font.PLAIN, 15));
        sizeComboBox.setPreferredSize(new Dimension(200, 50));

        JPanel sizePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        sizePanel.setOpaque(false);
        sizePanel.add(sizeLabel);
        sizePanel.add(sizeComboBox);


        JPanel topLabelPanel = new JPanel(new BorderLayout());
        topLabelPanel.setOpaque(false);
        topLabelPanel.add(titleLabel, BorderLayout.NORTH);
        topLabelPanel.add(buttonsPanel, BorderLayout.CENTER);
        topLabelPanel.add(sizePanel, BorderLayout.SOUTH);

        JButton playButton = new JButton("Jouer");
        playButton.setFont(new Font("Arial", Font.PLAIN, 15));
        playButton.setForeground(Color.WHITE);
        playButton.setBackground(Color.BLACK);
        playButton.setPreferredSize(new Dimension(300, 50));
        playButton.addActionListener(e -> {
            if(hasPlayerSelectedX == null) {
                JOptionPane.showMessageDialog(null, "Veuillez choisir votre pion");
            }
            else {
                gridSize = (Integer) sizeComboBox.getSelectedItem();
                createGrid();
                controller.initialization(gridSize);
                resetGrid();
                if(!hasPlayerSelectedX) {
                    placeComputerSymbol();
                }
                xButton.setFont(new Font("Arial", Font.PLAIN, 30));
                xButton.setEnabled(false);
                oButton.setFont(new Font("Arial", Font.PLAIN, 30));
                oButton.setEnabled(false);
                sizeComboBox.setEnabled(false);
                playButton.setEnabled(false);
            }
        });

        JButton restartButton = new JButton("Nouvelle partie");
        restartButton.setPreferredSize(new Dimension(180, 50));
        restartButton.addActionListener(e -> {
            if(centerPanel != null) {
                controller.restartTicTacToe();
                xButton.setEnabled(true);
                oButton.setEnabled(true);
                sizeComboBox.setEnabled(true);
                playButton.setEnabled(true);
                hasPlayerSelectedX = null;
                xButton.setFont(new Font("Arial", Font.PLAIN, 30));
                oButton.setFont(new Font("Arial", Font.PLAIN, 30));
                resetGrid();
                disableGrid();
            }
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setOpaque(false);
        bottomPanel.add(playButton);
        bottomPanel.add(restartButton);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        mainPanel.add(topLabelPanel, BorderLayout.NORTH);
        createGrid();
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void createGrid(){
        if(centerPanel != null) {
            mainPanel.remove(centerPanel);
        }
        centerPanel = new JPanel(new GridLayout(gridSize, gridSize, 5, 5));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttons = new JButton[gridSize][gridSize];
        for(int x = 0; x < gridSize; x++) {
            for(int y = 0; y < gridSize; y++) {
                int posX = x;
                int posY = y;
                buttons[x][y] = new JButton();
                buttons[x][y].addActionListener(e -> placePlayerSymbol(posX, posY));
                buttons[x][y].setEnabled(false);
                buttons[x][y].setFont(new Font("Arial", Font.BOLD, 24));
                centerPanel.add(buttons[x][y]);
            }
        }
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showGameState() {
        switch (controller.getGameState()) {
            case WINNER_X :
                showColorGridWinner();
                JOptionPane.showMessageDialog(this, "X a gagné la partie.", "Gagnant", JOptionPane.INFORMATION_MESSAGE);
                disableGrid();
                break;

            case WINNER_O :
                showColorGridWinner();
                JOptionPane.showMessageDialog(this, "O a gagné la partie.", "Gagnant", JOptionPane.INFORMATION_MESSAGE);
                disableGrid();
                break;

            case DRAW :
                JOptionPane.showMessageDialog(this, "C'est une égalité", "Egalite", JOptionPane.INFORMATION_MESSAGE);
                disableGrid();
                break;

            case ONGOING :
                break;
        }
    }

    private void placePlayerSymbol(int posX, int posY) {
        controller.playByPlayer(posX, posY, hasPlayerSelectedX);
        buttons[posX][posY].setText(hasPlayerSelectedX ? "X" : "O");
        buttons[posX][posY].setEnabled(false);
        if(controller.getGameState() == GameState.ONGOING) {
            placeComputerSymbol();
        }
        else {
            showGameState();
        }
    }
    private void placeComputerSymbol() {
        int ordi[] = controller.playByComputer(!hasPlayerSelectedX);
        if (ordi == null) {
            showGameState();
        }
        else {
            buttons[ordi[0]][ordi[1]].setText(hasPlayerSelectedX ? "O" : "X");
            buttons[ordi[0]][ordi[1]].setEnabled(false);
            showGameState();
        }
    }

    private void disableGrid() {
        for(int x = 0; x < gridSize; x++) {
            for(int y = 0; y < gridSize; y++) {
                buttons[x][y].setEnabled(false);
            }
        }
    }
    private void resetGrid() {
        if(buttons != null) {
            for(int x = 0; x < gridSize; x++) {
                for(int y = 0; y < gridSize; y++) {
                    buttons[x][y].setEnabled(true);
                    buttons[x][y].setText("");
                    buttons[x][y].setBackground(null);
                }
            }
        }
    }

    private void showColorGridWinner(){
        WinType winnerType = controller.getWinnerType();
        int start;
        switch(winnerType){
            case COLUMN:
                start = controller.getWinnerPosition();
                for(int y = 0; y < gridSize; y++) {
                    buttons[start][y].setBackground(Color.GREEN);
                }
                break;

            case ROW:
                start = controller.getWinnerPosition();
                for(int x = 0; x < gridSize; x++) {
                    buttons[x][start].setBackground(Color.GREEN);
                }
                break;

            case DIAGONAL:
                for(int i = 0; i < gridSize; i++) {
                    buttons[i][i].setBackground(Color.GREEN);
                }
                break;

            case REVERSE_DIAGONAL:
                for(int i = 0; i < gridSize; i++) {
                    buttons[gridSize- (i+1)][i].setBackground(Color.GREEN);
                }
                break;
        }
    }
}
