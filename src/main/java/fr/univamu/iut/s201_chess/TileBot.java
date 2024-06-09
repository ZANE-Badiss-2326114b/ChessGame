package fr.univamu.iut.s201_chess;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TileBot extends Rectangle {
    private Piece piece;

    public TileBot(boolean light, int x, int y) {
        setWidth(ChessGame.TILE_SIZE);
        setHeight(ChessGame.TILE_SIZE);

        relocate(x * ChessGame.TILE_SIZE, y * ChessGame.TILE_SIZE);
        setFill(light ? Color.valueOf("#feb") : Color.valueOf("#582"));

        setOnMouseClicked(event -> {
            if (ChessBot.getSelectedPiece() == null) {
                if (hasPiece() && piece.getColor() == ChessBot.getTurnColor()) {
                    ChessBot.setSelectedPiece(piece);
                    setFill(Color.valueOf("#6a0dad"));  // Highlight selected piece
                }
            } else {
                Piece selectedPiece = ChessBot.getSelectedPiece();
                if (selectedPiece.isValidMove(x, y, ChessBot.getBoard())) {
                    selectedPiece.move(x, y, ChessBot.getBoard(), ChessBot.getPieceGroup());
                    ChessBot.switchTurn();

                    if (ChessBot.getTurnColor() == PieceColor.BLACK) {
                        Bot.makeMove();
                    }
                }
                ChessBot.setSelectedPiece(null);
                ChessBot.updateTileColors();
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

