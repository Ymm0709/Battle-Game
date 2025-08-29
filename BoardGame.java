public abstract class BoardGame {
    protected static final int SIZE = 15;
    protected final Piece[][] grid;
    protected String currentPlayer;
    protected boolean gameOver;

    public BoardGame() {
        grid = new Piece[SIZE][SIZE];
        gameOver = false;
    }

    // 抽象方法，由子类实现具体的移动规则
    public abstract boolean makeMove(int row, int col);

    // 通用的位置检查方法
    protected boolean isValidPosition(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
    }

    // 通用的获取棋子方法
    public Piece getPiece(int row, int col) {
        return grid[row][col];
    }

    // 通用的获取当前玩家方法
    public String getCurrentPlayer() {
        return currentPlayer;
    }

    // 通用的游戏结束检查方法
    public boolean isGameOver() {
        return gameOver;
    }

    // 通用的获取棋盘大小方法
    public static int getSize() {
        return SIZE;
    }
} 