package fr.univamu.iut.s201_chess;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bot {
        private static final Random random = new Random();

        public static void makeMove() {
            List<Move> validMoves = getAllValidMoves(PieceColor.BLACK);
            if (validMoves.isEmpty()) {
                ChessBot.endGame(PieceColor.WHITE);
                return;
            }

            Move move = validMoves.get(random.nextInt(validMoves.size()));
            move.getPiece().move(move.getNewX(), move.getNewY(), ChessBot.getBoard(), ChessBot.getPieceGroup());
            ChessBot.switchTurn();
        }

        private static List<Move> getAllValidMoves(PieceColor color) {
            List<Move> validMoves = new ArrayList<>();
            Tile[][] board = ChessBot.getBoard();

            for (int y = 0; y < ChessGame.HEIGHT; y++) {
                for (int x = 0; x < ChessGame.WIDTH; x++) {
                    if (board[x][y].hasPiece() && board[x][y].getPiece().getColor() == color) {
                        Piece piece = board[x][y].getPiece();
                        for (int newY = 0; newY < ChessGame.HEIGHT; newY++) {
                            for (int newX = 0; newX < ChessGame.WIDTH; newX++) {
                                if (piece.isValidMove(newX, newY, board)) {
                                    validMoves.add(new Move(piece, newX, newY));
                                }
                            }
                        }
                    }
                }
            }

            return validMoves;
        }
    }

    class Move {
        private Piece piece;
        private int newX;
        private int newY;

        public Move(Piece piece, int newX, int newY) {
            this.piece = piece;
            this.newX = newX;
            this.newY = newY;
        }

        public Piece getPiece() {
            return piece;
        }

        public int getNewX() {
            return newX;
        }

        public int getNewY() {
            return newY;
        }
    }
