package fr.univamu.iut.s201_chess;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class ChessGame extends Application {
    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    private Tile[][] board = new Tile[WIDTH][HEIGHT];
    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        root.getChildren().addAll(tileGroup, pieceGroup);

        ChessGameController.setBoard(board);
        ChessGameController.setPieceGroup(pieceGroup);

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
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
                    pieceGroup.getChildren().add(piece);
                }
            }
        }

        return root;
    }

    private Piece makePiece(PieceType type, PieceColor color, int x, int y) {
        Piece piece = new Piece(type, color, x, y);
        piece.setOnMouseReleased(e -> {
            int newX = toBoard(piece.getLayoutX());
            int newY = toBoard(piece.getLayoutY());

            if (newX < 0 || newY < 0 || newX >= WIDTH || newY >= HEIGHT) {
                piece.abortMove();
                return;
            }

            if (piece.getColor() == ChessGameController.getTurnColor() && piece.isValidMove(newX, newY, board)) {
                piece.move(newX, newY, board, pieceGroup);
                ChessGameController.switchTurn();
            } else {
                piece.abortMove();
            }
        });
        return piece;
    }

    private int toBoard(double pixel) {
        return (int) (pixel + TILE_SIZE / 2) / TILE_SIZE;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Scene scene = new Scene(createContent());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
