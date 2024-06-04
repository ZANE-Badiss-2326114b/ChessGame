package fr.univamu.iut.s201_chess.chessPage;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends StackPane {
    private int x, y;
    private Piece piece;

    public Tile(boolean light, int x, int y) {
        this.x = x;
        this.y = y;
        Rectangle rect = new Rectangle(ChessGame.TILE_SIZE, ChessGame.TILE_SIZE);
        rect.setFill(light ? Color.WHITE : Color.GREEN);  // Change colors here
        getChildren().add(rect);

        setTranslateX(x * ChessGame.TILE_SIZE);
        setTranslateY(y * ChessGame.TILE_SIZE);
    }

    public boolean hasPiece() {
        return piece != null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
