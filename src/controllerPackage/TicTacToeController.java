package controllerPackage;

import java.util.Random;
import modelPackage.*;

public class TicTacToeController {

    private static TicTacToeController instance;
    private Board board = new Board();

    private TicTacToeController() {}

    public static TicTacToeController getInstance() {
        if (instance == null) {
            instance = new TicTacToeController();
        }
        return instance;
    }

    public void initialization(int gridSize)  {
        board.initialization(gridSize);
    }
    public void restartTicTacToe() {
        board.reset();
    }

    public GameState getGameState() {
        return board.getGameState();
    }
    public WinType getWinnerType() {
        return board.getWinnerType();
    }
    public int getWinnerPosition() {
        return board.getWinnerPosition();
    }

    public void playByPlayer(int x, int y, boolean isSymbolX) {
        board.setCell(x, y, isSymbolX);
    }
    public int[] playByComputer(boolean isSymbolX) {
        if(board.isGridFull()) {
            return null;
        }
        Random generator = new Random();
        int x;
        int y;
        int gridSize = board.getGridSize();
        do {
            x = generator.nextInt(gridSize);
            y = generator.nextInt(gridSize);
        } while (!board.isCellEmpty(x, y));
        board.setCell(x, y, isSymbolX);
        int[] tab = {x, y};
        return tab;
    }
}
