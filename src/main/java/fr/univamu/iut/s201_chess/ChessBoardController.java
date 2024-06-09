package fr.univamu.iut.s201_chess;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.util.Duration;
import javafx.scene.control.ComboBox;

public class ChessBoardController {

    @FXML
    private Group tileGroup;

    @FXML
    private Group pieceGroup;

    @FXML
    private Label opponentTimer;

    @FXML
    private Label playerTimer;

    @FXML
    private ComboBox<String> gameDurationComboBox;

    private Timeline opponentTimeline;
    private Timeline playerTimeline;
    private int opponentTimeSeconds;
    private int playerTimeSeconds;

    public void initialize() {
        ChessGameController.setPieceGroup(pieceGroup);
        initializeBoard();
        initializeComboBox();
        startTimers();
    }

    private void initializeComboBox() {
        gameDurationComboBox.getItems().addAll("1 min" ,"3 min", "5 min", "10 min", "15 min" );
        gameDurationComboBox.setValue("10 min"); // Default duration
    }

    private void initializeBoard() {
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
                ChessGameController.moveRandomPiece(PieceColor.BLACK);
                opponentTimeSeconds = getSelectedDuration(); // Reset the timer
                ChessGameController.switchTurn();
            }
        }));
        opponentTimeline.setCycleCount(Animation.INDEFINITE);

        playerTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            playerTimeSeconds--;
            updateTimerLabel(playerTimer, playerTimeSeconds);
            if (playerTimeSeconds <= 0) {
                ChessGameController.moveRandomPiece(PieceColor.WHITE);
                playerTimeSeconds = getSelectedDuration();// Reset the timer
                ChessGameController.switchTurn();

            }
        }));
        playerTimeline.setCycleCount(Animation.INDEFINITE);
    }

    private void switchTimers() {
        if (ChessGameController.getTurnColor() == PieceColor.WHITE) {
            opponentTimeline.pause();
            playerTimeline.play();
        } else {
            opponentTimeline.play();
            playerTimeline.pause();
        }
    }

    private void updateTimerLabel(Label label, int timeSeconds) {
        int minutes = timeSeconds / 60;
        int seconds = timeSeconds % 60;
        label.setText(String.format("%02d:%02d", minutes, seconds));
    }

    private void resetBoard() {
        tileGroup.getChildren().clear();
        pieceGroup.getChildren().clear();
        initializeBoard();
        ChessGameController.restartGame();
    }

    private void resetTimers() {
        int duration = getSelectedDuration();
        opponentTimeSeconds = duration;
        playerTimeSeconds = duration;
        updateTimerLabel(opponentTimer, opponentTimeSeconds);
        updateTimerLabel(playerTimer, playerTimeSeconds);

        opponentTimeline.stop();
        playerTimeline.stop();
    }

    private int getSelectedDuration() {
        String selected = gameDurationComboBox.getValue();
        switch (selected) {
            case "1 min":
                return 10;
            case "3 min":
                return 180;
            case "5 min":
                return 300;
            case "10 min":
                return 600;
            case "15 min":
                return 900;
            case "20 min":
                return 1200;
            default:
                return 600; // Default to 10 minutes if not recognized
        }
    }

    @FXML
    private void handleStartGame() {
        resetBoard();
        resetTimers();
    }

    @FXML
    private void handleTournaments() {
        // Logic for showing tournaments
    }




}



