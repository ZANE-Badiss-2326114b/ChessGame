package fr.univamu.iut.s201_chess;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends StackPane {
    private Piece piece;

    public Tile(boolean light, int x, int y) {
        Rectangle bg = new Rectangle(ChessGame.TILE_SIZE, ChessGame.TILE_SIZE);
        bg.setFill(light ? Color.valueOf("#f0d9b5") : Color.valueOf("#b58863"));
        getChildren().addAll(bg);
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
}
