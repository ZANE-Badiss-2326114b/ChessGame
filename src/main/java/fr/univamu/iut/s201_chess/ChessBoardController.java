package fr.univamu.iut.s201_chess;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class ChessBoardController {

    @FXML
    private Group tileGroup;

    @FXML
    private Group pieceGroup;

    @FXML
    private Label opponentTimer;

    @FXML
    private Label playerTimer;

    private Timeline opponentTimeline;
    private Timeline playerTimeline;
    private int opponentTimeSeconds = 600; // 10 minutes in seconds
    private int playerTimeSeconds = 600; // 10 minutes in seconds

    public void initialize() {
        ChessGameController.setPieceGroup(pieceGroup);

        Tile[][] board = ChessGameController.getBoard();

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
                    pieceGroup.getChildren().add(piece);
                }
            }
        }

        startTimers();
    }

    private Piece makePiece(PieceType type, PieceColor color, int x, int y) {
        Piece piece = new Piece(type, color, x, y);
        piece.setOnMouseReleased(e -> {
            int newX = toBoard(piece.getLayoutX());
            int newY = toBoard(piece.getLayoutY());

            if (newX < 0 || newY < 0 || newX >= ChessGame.WIDTH || newY >= ChessGame.HEIGHT) {
                piece.abortMove();
                return;
            }

            if (piece.getColor() == ChessGameController.getTurnColor() && piece.isValidMove(newX, newY, ChessGameController.getBoard())) {
                piece.move(newX, newY, ChessGameController.getBoard(), pieceGroup);
                ChessGameController.switchTurn();
                switchTimers();
            } else {
                piece.abortMove();
            }
        });
        return piece;
    }

    private int toBoard(double pixel) {
        return (int) (pixel + ChessGame.TILE_SIZE / 2) / ChessGame.TILE_SIZE;
    }

    private void startTimers() {
        opponentTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            opponentTimeSeconds--;
            updateTimerLabel(opponentTimer, opponentTimeSeconds);
            if (opponentTimeSeconds <= 0) {
                endGame(PieceColor.WHITE);
            }
        }));
        opponentTimeline.setCycleCount(Animation.INDEFINITE);

        playerTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            playerTimeSeconds--;
            updateTimerLabel(playerTimer, playerTimeSeconds);
            if (playerTimeSeconds <= 0) {
                endGame(PieceColor.BLACK);
            }
        }));
        playerTimeline.setCycleCount(Animation.INDEFINITE);

        opponentTimeline.play();
    }

    private void switchTimers() {
        if (ChessGameController.getTurnColor() == PieceColor.WHITE) {
            opponentTimeline.play();
            playerTimeline.pause();
        } else {
            opponentTimeline.pause();
            playerTimeline.play();
        }
    }

    private void updateTimerLabel(Label label, int timeSeconds) {
        int minutes = timeSeconds / 60;
        int seconds = timeSeconds % 60;
        label.setText(String.format("%02d:%02d", minutes, seconds));
    }

    private void endGame(PieceColor winner) {
        ChessGameController.endGame(winner);
        opponentTimeline.stop();
        playerTimeline.stop();
    }

    @FXML
    private void handleNewGame() {
        // Logic for starting a new game
    }

    @FXML
    private void handleChangeGameDuration() {
        // Logic for changing the game duration
    }

    @FXML
    private void handleStartGame() {
        // Logic for starting the game
    }

    @FXML
    private void handleTournaments() {
        // Logic for showing tournaments
    }
}