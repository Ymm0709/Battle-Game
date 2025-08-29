public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        StdDrawPlus display = new StdDrawPlus(board);
        display.setVisible(true);

        while (!board.isGameOver()) {
            display.refresh();
            int[] move = display.waitForClick();
            int row = move[0];
            int col = move[1];
            
            if (board.makeMove(row, col)) {
                display.refresh();
            }
        }
        
        // Show final state
        display.refresh();
    }
} 