public class Piece {
    private final String color;  // "Black" or "White"

    public Piece(String color) {
        if (!color.equals("Black") && !color.equals("White")) {
            throw new IllegalArgumentException("Color must be either Black or White");
        }
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return color;
    }
} 