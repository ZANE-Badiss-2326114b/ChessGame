package fr.univamu.iut.s201_chess;

import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChessBot {

    private static Group pieceGroup;
    private static Tile[][] board = new Tile[ChessGame.WIDTH][ChessGame.HEIGHT];
    private static Piece selectedPiece = null;
    private static PieceColor turnColor = PieceColor.WHITE;
    private static Bot bot = new Bot();


    public static Piece getRandomPiece(PieceColor color) {
        List<Piece> pieces = new ArrayList<>();
        for (int y = 0; y < ChessGame.HEIGHT; y++) {
            for (int x = 0; x < ChessGame.WIDTH; x++) {
                Tile tile = board[x][y];
                if (tile.hasPiece() && tile.getPiece().getColor() == color) {
                    pieces.add(tile.getPiece());
                }
            }
        }
        if (pieces.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return pieces.get(random.nextInt(pieces.size()));
    }

    public static void moveRandomPiece(PieceColor color) {
        Piece piece = getRandomPiece(color);
        if (piece == null) {
            return;
        }

        List<int[]> validMoves = new ArrayList<>();
        for (int y = 0; y < ChessGame.HEIGHT; y++) {
            for (int x = 0; x < ChessGame.WIDTH; x++) {
                if (piece.isValidMove(x, y, board)) {
                    validMoves.add(new int[]{x, y});
                }
            }
        }

        if (!validMoves.isEmpty()) {
            Random random = new Random();
            int[] move = validMoves.get(random.nextInt(validMoves.size()));
            piece.move(move[0], move[1], board, pieceGroup);
        }
    }

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
        if (turnColor == PieceColor.BLACK) {
            bot.makeMove();
        }
    }

    public static void restartGame() {
        turnColor = PieceColor.WHITE;
    }

    public static Tile[][] getBoard() {
        return board;
    }

    public static Group getPieceGroup() {
        return pieceGroup;
    }

    public static void setPieceGroup(Group pieceGroup) {
        ChessBot.pieceGroup = pieceGroup;
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

    public static void endGame(PieceColor winner) {
        String color = (winner == PieceColor.WHITE) ? "Noirs" : "Blancs";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Partie finie !");
        alert.setHeaderText(null);
        alert.setContentText("Partie finie ! Ce sont les " + color + " qui gagnent !");
        alert.showAndWait();
        System.exit(0);
    }
}
