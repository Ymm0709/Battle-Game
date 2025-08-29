public class Board extends BoardGame {
    private static final int SIZE = 15;  // Standard Gomoku board size
    private static final int CONNECT_TO_WIN = 5;

    public Board() {
        super();
        currentPlayer = "Black";  // Black goes first
    }

    @Override
    public boolean makeMove(int row, int col) {
        if (!isValidPosition(row, col) || gameOver) {
            return false;
        }

        // Check if the position is empty
        if (grid[row][col] != null) {
            return false;
        }

        // Place the piece
        grid[row][col] = new Piece(currentPlayer);
        
        // Check for captures
        checkAndCapture(row, col);
        
        // Check for win
        if (checkWin(row, col)) {
            gameOver = true;
            return true;
        }

        // Switch player
        currentPlayer = currentPlayer.equals("Black") ? "White" : "Black";
        return true;
    }

    private void checkAndCapture(int row, int col) {
        String currentColor = grid[row][col].getColor();
        String oppositeColor = currentColor.equals("Black") ? "White" : "Black";
        
        // Check all eight directions for captures
        int[][] directions = {
            {0, 1},   // right
            {0, -1},  // left
            {1, 0},   // down
            {-1, 0},  // up
            {1, 1},   // diagonal down-right
            {-1, -1}, // diagonal up-left
            {1, -1},  // diagonal down-left
            {-1, 1}   // diagonal up-right
        };

        for (int[] dir : directions) {
            checkLineCapture(row, col, dir[0], dir[1], currentColor, oppositeColor);
        }
    }

    private void checkLineCapture(int row, int col, int rowDir, int colDir, String currentColor, String oppositeColor) {
        // Look for patterns in both directions
        checkCapturePattern(row, col, rowDir, colDir, currentColor, oppositeColor);
        checkCapturePattern(row, col, -rowDir, -colDir, currentColor, oppositeColor);
    }

    private void checkCapturePattern(int row, int col, int rowDir, int colDir, String currentColor, String oppositeColor) {
        // Check for pattern: currentColor - oppositeColor - currentColor
        int r1 = row + rowDir;
        int c1 = col + colDir;
        int r2 = r1 + rowDir;
        int c2 = c1 + colDir;
        
        if (isValidPosition(r1, c1) && isValidPosition(r2, c2) &&
            grid[r1][c1] != null && grid[r2][c2] != null &&
            grid[r1][c1].getColor().equals(oppositeColor) &&
            grid[r2][c2].getColor().equals(currentColor)) {
            // Capture the middle piece
            grid[r1][c1] = new Piece(currentColor);
        }
    }

    private boolean checkWin(int row, int col) {
        return checkDirection(row, col, 1, 0) ||   // vertical
               checkDirection(row, col, 0, 1) ||   // horizontal
               checkDirection(row, col, 1, 1) ||   // diagonal down-right
               checkDirection(row, col, 1, -1);    // diagonal down-left
    }

    private boolean checkDirection(int row, int col, int rowDir, int colDir) {
        String color = grid[row][col].getColor();
        int count = 1;
        
        // Check in positive direction
        int r = row + rowDir;
        int c = col + colDir;
        while (isValidPosition(r, c) && grid[r][c] != null && 
               grid[r][c].getColor().equals(color)) {
            count++;
            r += rowDir;
            c += colDir;
        }
        
        // Check in negative direction
        r = row - rowDir;
        c = col - colDir;
        while (isValidPosition(r, c) && grid[r][c] != null && 
               grid[r][c].getColor().equals(color)) {
            count++;
            r -= rowDir;
            c -= colDir;
        }
        
        return count >= CONNECT_TO_WIN;
    }

    public Piece getPiece(int row, int col) {
        return grid[row][col];
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public static int getSize() {
        return SIZE;
    }
} 