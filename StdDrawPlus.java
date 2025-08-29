import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class StdDrawPlus extends JFrame {
    private static final int CELL_SIZE = 40;
    private static final int PADDING = 20;
    private Board board;
    private boolean waitingForClick;
    private int lastClickedRow;
    private int lastClickedCol;

    public StdDrawPlus(Board board) {
        this.board = board;
        this.waitingForClick = false;
        
        setTitle("Gomoku - Five in a Row");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        int size = (Board.getSize() * CELL_SIZE) + (2 * PADDING);
        setSize(size, size);
        setResizable(false);

        GamePanel gamePanel = new GamePanel();
        add(gamePanel);
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (waitingForClick && !board.isGameOver()) {
                    int x = e.getX() - PADDING;
                    int y = e.getY() - PADDING;
                    lastClickedCol = x / CELL_SIZE;
                    lastClickedRow = y / CELL_SIZE;
                    if (isValidPosition(lastClickedRow, lastClickedCol)) {
                        waitingForClick = false;
                    }
                }
            }
        });
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < Board.getSize() && col >= 0 && col < Board.getSize();
    }

    public int[] waitForClick() {
        waitingForClick = true;
        while (waitingForClick) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return new int[]{lastClickedRow, lastClickedCol};
    }

    private class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw the wooden background
            g2d.setColor(new Color(222, 184, 135));
            g2d.fillRect(0, 0, getWidth(), getHeight());

            // Draw the grid lines
            g2d.setColor(Color.BLACK);
            for (int i = 0; i < Board.getSize(); i++) {
                // Vertical lines
                g2d.drawLine(PADDING + i * CELL_SIZE, PADDING,
                           PADDING + i * CELL_SIZE, PADDING + (Board.getSize() - 1) * CELL_SIZE);
                // Horizontal lines
                g2d.drawLine(PADDING, PADDING + i * CELL_SIZE,
                           PADDING + (Board.getSize() - 1) * CELL_SIZE, PADDING + i * CELL_SIZE);
            }

            // Draw the pieces
            for (int row = 0; row < Board.getSize(); row++) {
                for (int col = 0; col < Board.getSize(); col++) {
                    int x = col * CELL_SIZE + PADDING;
                    int y = row * CELL_SIZE + PADDING;

                    Piece piece = board.getPiece(row, col);
                    if (piece != null) {
                        if (piece.getColor().equals("Black")) {
                            g2d.setColor(Color.BLACK);
                        } else {
                            g2d.setColor(Color.WHITE);
                        }
                        g2d.fillOval(x - CELL_SIZE/2 + 2, y - CELL_SIZE/2 + 2, CELL_SIZE - 4, CELL_SIZE - 4);
                        
                        // Add a thin border to white pieces
                        if (piece.getColor().equals("White")) {
                            g2d.setColor(Color.BLACK);
                            g2d.setStroke(new BasicStroke(1));
                            g2d.drawOval(x - CELL_SIZE/2 + 2, y - CELL_SIZE/2 + 2, CELL_SIZE - 4, CELL_SIZE - 4);
                        }
                    }
                }
            }

            // Draw current player indicator and game status
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            String status = board.isGameOver() 
                ? "Game Over! " + board.getCurrentPlayer() + " Wins!"
                : "Current Player: " + board.getCurrentPlayer();
            g2d.drawString(status, PADDING, getHeight() - PADDING/2);
        }
    }

    public void refresh() {
        repaint();
    }
} 