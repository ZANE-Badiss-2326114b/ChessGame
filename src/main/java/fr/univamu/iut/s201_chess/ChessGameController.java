package fr.univamu.iut.s201_chess;

import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;

public class ChessGameController {

    private static Group pieceGroup;


    private static Tile[][] board = new Tile[ChessGame.WIDTH][ChessGame.HEIGHT];
    private static Piece selectedPiece = null;
    private static PieceColor turnColor = PieceColor.WHITE;



    public static Piece getSelectedPiece() {
        return selectedPiece;
    }

    public static void setSelectedPiece(Piece piece) {
        selectedPiece = piece;
    }

    public static PieceColor getTurnColor() {
        return turnColor;
    }

    public static void switchTurn() {
        turnColor = (turnColor == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;
    }

    public static Tile[][] getBoard() {
        return board;
    }


    public static Group getPieceGroup() {
        return pieceGroup;
    }

    public static void setPieceGroup(Group pieceGroup) {
        ChessGameController.pieceGroup = pieceGroup;
    }

    public static void updateTileColors() {
        for (int y = 0; y < ChessGame.HEIGHT; y++) {
            for (int x = 0; x < ChessGame.WIDTH; x++) {
                Tile tile = board[x][y];
                boolean light = (x + y) % 2 == 0;
                tile.setFill(light ? Color.valueOf("#feb") : Color.valueOf("#582"));
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
        if (targetTile.hasPiece()) {
            // Vérifier si la pièce sur la case cible est une pièce ennemie
            if (targetTile.getPiece().getColor() != piece.getColor()) {
                return piece.isValidMove(newX, newY, board);
            } else {
                return false; // La case cible contient une pièce du même joueur
            }
        } else {
            return piece.isValidMove(newX, newY, board);
        }
    }

    public static void endGame(PieceColor winner) {
        String color;
        if (winner.toString() == "BLACK"){
            color = "Blancs";
        }
        else{
            color = "Noirs";
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Partie finie !");
        alert.setHeaderText(null);
        alert.setContentText("Partie finie ! Ce sont les " + color + " qui gagnent !");
        alert.showAndWait();
        System.exit(0);
    }
}