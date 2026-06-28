package modelPackage;

public class Board {
    private Symbol[][] board;
    private int gridSize;
    private WinType winnerType;
    private int winnerPosition;

    public void initialization(int gridSize)  {
        this.gridSize = gridSize;
        reset();
    }
    public void reset() {
        if(gridSize >= 1) {
            board = new Symbol[gridSize][gridSize];
            for (int x = 0; x < gridSize; x++) {
                for (int y = 0; y < gridSize; y++) {
                    board[x][y] = Symbol.EMPTY;
                }
            }
            winnerType = null;
            winnerPosition = -1;
        }
    }

    public int getGridSize(){
        return gridSize;
    }
    public WinType getWinnerType(){
        return winnerType;
    }
    public int getWinnerPosition(){
        return winnerPosition;
    }
    private Symbol getSymbolWinner() {
        if(isPionWinner(Symbol.X)) {
            return Symbol.X;
        }
        if(isPionWinner(Symbol.O)) {
            return Symbol.O;
        }
        return Symbol.EMPTY;
    }

    public void setCell(int x, int y, boolean isSymbolX) {
        board[x][y] = isSymbolX ? Symbol.X : Symbol.O;
    }

    public boolean isCellEmpty(int x, int y) {
        return board[x][y] == Symbol.EMPTY;
    }

    public GameState getGameState() {
        Symbol winner = getSymbolWinner();
        if (winner == Symbol.X) {
            return GameState.WINNER_X;
        }
        if (winner == Symbol.O) {
            return GameState.WINNER_O;
        }
        if (isGridFull()) {
            return GameState.DRAW;
        }
        return GameState.ONGOING;
    }

    public boolean isGridFull(){
        for(int x = 0; x < gridSize; x++) {
            for(int y = 0; y < gridSize; y++) {
                if(board[x][y] == Symbol.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isPionWinner(Symbol pion) {
        for(int y = 0; y < gridSize; y++) {
            int nbSuite = 0;
            for(int x = 0; x < gridSize; x++) {
                nbSuite = (board[x][y] == pion) ? nbSuite + 1 : 0;
            }

            if(nbSuite == gridSize) {
                winnerType = WinType.ROW;
                winnerPosition = y;
                return true;
            }
        }

        for(int x = 0; x < gridSize; x++) {
            int nbSuite = 0;
            for(int y = 0; y < gridSize; y++) {
                nbSuite = (board[x][y] == pion) ? nbSuite + 1 : 0;
            }

            if(nbSuite == gridSize) {
                winnerType = WinType.COLUMN;
                winnerPosition = x;
                return true;
            }
        }

        int nbSuite = 0;
        for(int i = 0; i < gridSize; i++) {
            nbSuite = (board[i][i] == pion) ? nbSuite + 1 : 0;
        }
        if(nbSuite == gridSize) {
            winnerType = WinType.DIAGONAL;
            return true;
        }

        nbSuite = 0;
        for(int i = 0; i < gridSize; i++) {
            nbSuite = (board[gridSize- (i+1)][i] == pion) ? nbSuite + 1 : 0;
        }
        if(nbSuite == gridSize) {
            winnerType = WinType.REVERSE_DIAGONAL;
            return true;
        }

        return false;
    }
}
