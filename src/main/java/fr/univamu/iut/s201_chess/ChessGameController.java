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