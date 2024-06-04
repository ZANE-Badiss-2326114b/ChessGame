package fr.univamu.iut.s201_chess;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class ChessGameController {
    @FXML
    private GridPane chessBoard;
    @FXML
    private Group tileGroup;
    @FXML
    private Group pieceGroup;


    private Tile[][] board = new Tile[ChessGame.WIDTH][ChessGame.HEIGHT];
    private Piece selectedPiece = null;

    @FXML
    public void initialize() {
        for (int y = 0; y < ChessGame.HEIGHT; y++) {
            for (int x = 0; x < ChessGame.WIDTH; x++) {
                Tile tile = new Tile((x + y) % 2 == 0, x, y);
                board[x][y] = tile;
                tileGroup.getChildren().add(tile);
                Piece piece = null;

                if (y == 1) {
                    piece = makePiece(PieceType.PAWN, PieceColor.BLACK, x, y);
                } else if (y == 6) {
                    piece = makePiece(PieceType.PAWN, PieceColor.WHITE, x, y);
                } else if (y == 0 || y == 7) {
                    PieceColor color = (y == 0) ? PieceColor.BLACK : PieceColor.WHITE;
                    if (x == 0 || x == 7) {
                        piece = makePiece(PieceType.ROOK, color, x, y);
                    } else if (x == 1 || x == 6) {
                        piece = makePiece(PieceType.KNIGHT, color, x, y);
                    } else if (x == 2 || x == 5) {
                        piece = makePiece(PieceType.BISHOP, color, x, y);
                    } else if (x == 3) {
                        piece = makePiece(PieceType.QUEEN, color, x, y);
                    } else if (x == 4) {
                        piece = makePiece(PieceType.KING, color, x, y);
                    }
                }

                if (piece != null) {
                    tile.setPiece(piece);
                    piece.setTile(tile);
                    pieceGroup.getChildren().add(piece);
                }
            }
        }
    }

    private Piece makePiece(PieceType type, PieceColor color, int x, int y) {
        Piece piece = new Piece(type, color, x, y);
        piece.setOnMouseReleased(event -> {
            int newX = (int) (event.getSceneX() / ChessGame.TILE_SIZE);
            int newY = (int) (event.getSceneY() / ChessGame.TILE_SIZE);

            if (isValidMove(piece, newX, newY)) {
                piece.move(newX, newY, board, pieceGroup);
                board[newX][newY].setPiece(piece);
                board[(int)(piece.getOldX() / ChessGame.TILE_SIZE)][(int)(piece.getOldY() / ChessGame.TILE_SIZE)].setPiece(null);
            } else {
                piece.abortMove();
            }
        });
        return piece;
    }

    private boolean isValidMove(Piece piece, int newX, int newY) {
        if (newX < 0 || newX >= ChessGame.WIDTH || newY < 0 || newY >= ChessGame.HEIGHT) {
            return false;
        }
        Tile targetTile = board[newX][newY];
        if (targetTile.hasPiece() && targetTile.getPiece().getColor() == piece.getColor()) {
            return false;
        }
        return piece.isValidMove(newX, newY, board);
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        int x = (int) (event.getSceneX() / ChessGame.TILE_SIZE);
        int y = (int) (event.getSceneY() / ChessGame.TILE_SIZE);

        if (selectedPiece != null) {
            Tile targetTile = board[x][y];
            if (targetTile != null && targetTile != selectedPiece.getTile()) {
                if (isValidMove(selectedPiece, x, y)) {
                    Tile oldTile = selectedPiece.getTile();
                    oldTile.setPiece(null);
                    selectedPiece.move(x, y, board, pieceGroup);
                    targetTile.setPiece(selectedPiece);
                    selectedPiece.setTile(targetTile);
                }
                selectedPiece = null;
            }
        } else {
            Tile tile = board[x][y];
            if (tile.hasPiece()) {
                selectedPiece = tile.getPiece();
            }
        }
    }
}
