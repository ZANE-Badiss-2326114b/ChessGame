package fr.univamu.iut.s201_chess;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {
    private Piece piece;

    public Tile(boolean light, int x, int y) {
        setWidth(ChessGame.TILE_SIZE);
        setHeight(ChessGame.TILE_SIZE);

        relocate(x * ChessGame.TILE_SIZE, y * ChessGame.TILE_SIZE);
        setFill(light ? Color.valueOf("#feb") : Color.valueOf("#582"));

        setOnMouseClicked(event -> {
            if (ChessGameController.getSelectedPiece() == null) {
                if (hasPiece() && piece.getColor() == ChessGameController.getTurnColor()) {
                    ChessGameController.setSelectedPiece(piece);
                    setFill(Color.valueOf("#6a0dad"));  // Highlight selected piece
                }
            } else {
                Piece selectedPiece = ChessGameController.getSelectedPiece();
                if (selectedPiece.isValidMove(x, y, ChessGameController.getBoard())) {
                    selectedPiece.move(x, y, ChessGameController.getBoard(), ChessGameController.getPieceGroup());
                    ChessGameController.switchTurn();
                }
                ChessGameController.setSelectedPiece(null);
                ChessGameController.updateTileColors();
            }
        });
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
