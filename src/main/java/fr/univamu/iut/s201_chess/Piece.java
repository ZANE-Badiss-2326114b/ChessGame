package fr.univamu.iut.s201_chess;

import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;

public class Piece extends StackPane {
    private PieceType type;
    private PieceColor color;
    private double mouseX, mouseY;
    private double oldX, oldY;
    private Tile tile;

    public Piece(PieceType type, PieceColor color, int x, int y) {
        this.type = type;
        this.color = color;

        initMove(x, y); // Utilisation de la méthode initMove pour initialiser la position

        Ellipse bg = new Ellipse(ChessGame.TILE_SIZE * 0.3125, ChessGame.TILE_SIZE * 0.26);
        bg.setFill(color == PieceColor.WHITE ? Color.WHITE : Color.BLACK);
        bg.setStroke(Color.BLACK);
        bg.setStrokeWidth(ChessGame.TILE_SIZE * 0.03);

        bg.setTranslateX((ChessGame.TILE_SIZE - ChessGame.TILE_SIZE * 0.3125 * 2) / 2);
        bg.setTranslateY((ChessGame.TILE_SIZE - ChessGame.TILE_SIZE * 0.26 * 2) / 2);

        Text text = new Text(type.toString().substring(0, 1));
        text.setFill(color == PieceColor.WHITE ? Color.BLACK : Color.WHITE);
        text.setTranslateX((ChessGame.TILE_SIZE - ChessGame.TILE_SIZE * 0.3125 * 2) / 2);
        text.setTranslateY((ChessGame.TILE_SIZE - ChessGame.TILE_SIZE * 0.26 * 2) / 2);

        //ImageView img = new ImageView();
        //img.setImage(new Image(getClass().getResourceAsStream("/img/blackPieces/bb.png")));

        getChildren().addAll(bg, text);


        //getChildren().add(img);

        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        setOnMouseDragged(e -> {
            relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
        });
    }

    // Méthode utilisée uniquement lors de l'initialisation
    private void initMove(int x, int y) {
        oldX = x * ChessGame.TILE_SIZE;
        oldY = y * ChessGame.TILE_SIZE;
        relocate(oldX, oldY);
    }

    public void move(int x, int y, Tile[][] board,  Group pieceGroup) {
        int oldTileX = (int) (oldX / ChessGame.TILE_SIZE);
        int oldTileY = (int) (oldY / ChessGame.TILE_SIZE);

        oldX = x * ChessGame.TILE_SIZE;
        oldY = y * ChessGame.TILE_SIZE;

        // Capture logic
        Tile targetTile = board[x][y];
        if (targetTile.hasPiece() && targetTile.getPiece().getColor() != this.color) {
            Piece capturedPiece = targetTile.getPiece();
            pieceGroup.getChildren().remove(capturedPiece);
        }

        // Update board positions
        board[oldTileX][oldTileY].setPiece(null);
        board[x][y].setPiece(this);

        relocate(oldX, oldY);
    }


    public void abortMove() {
        relocate(oldX, oldY);
    }

    public PieceType getType() {
        return type;
    }

    public PieceColor getColor() {
        return color;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public boolean isValidMove(int newX, int newY, Tile[][] board) {
        switch (this.getType()) {
            case ROOK:
                return isValidRookMove(newX, newY, board);
            case KNIGHT:
                return isValidKnightMove(newX, newY, board);
            case BISHOP:
                return isValidBishopMove(newX, newY, board);
            case QUEEN:
                return isValidQueenMove(newX, newY, board);
            case KING:
                return isValidKingMove(newX, newY, board);
            case PAWN:
                return isValidPawnMove(newX, newY, board);
            default:
                return false;
        }
    }

    private boolean isValidPawnMove(int newX, int newY, Tile[][] board) {
        int currentX = (int) (oldX / ChessGame.TILE_SIZE);
        int currentY = (int) (oldY / ChessGame.TILE_SIZE);
        int deltaY = newY - currentY;
        int deltaX = newX - currentX;

        if (color == PieceColor.WHITE) {
            // White pawns move up the board (decrease in Y)
            if (deltaX == 0) {
                // Moving forward
                if (deltaY == -1 && !board[newX][newY].hasPiece()) {
                    return true;
                } else if (currentY == 6 && deltaY == -2 && !board[newX][newY].hasPiece() && !board[newX][newY + 1].hasPiece()) {
                    return true;
                }
            } else if (Math.abs(deltaX) == 1 && deltaY == -1 && board[newX][newY].hasPiece() && board[newX][newY].getPiece().getColor() != this.color) {
                // Capturing diagonally
                return true;
            }
        } else if (color == PieceColor.BLACK) {
            // Black pawns move down the board (increase in Y)
            if (deltaX == 0) {
                // Moving forward
                if (deltaY == 1 && !board[newX][newY].hasPiece()) {
                    return true;
                } else if (currentY == 1 && deltaY == 2 && !board[newX][newY].hasPiece() && !board[newX][newY - 1].hasPiece()) {
                    return true;
                }
            } else if (Math.abs(deltaX) == 1 && deltaY == 1 && board[newX][newY].hasPiece() && board[newX][newY].getPiece().getColor() != this.color) {
                // Capturing diagonally
                return true;
            }
        }

        return false;
    }

    private boolean isValidRookMove(int newX, int newY, Tile[][] board) {
        int currentX = (int) (oldX / ChessGame.TILE_SIZE);
        int currentY = (int) (oldY / ChessGame.TILE_SIZE);

        // Rook moves in straight lines only
        if (currentX != newX && currentY != newY) {
            return false;
        }

        // Check path for any pieces
        if (currentX == newX) {
            // Vertical move
            int step = (newY > currentY) ? 1 : -1;
            for (int y = currentY + step; y != newY; y += step) {
                if (board[currentX][y].hasPiece()) {
                    return false;
                }
            }
        } else if (currentY == newY) {
            // Horizontal move
            int step = (newX > currentX) ? 1 : -1;
            for (int x = currentX + step; x != newX; x += step) {
                if (board[x][currentY].hasPiece()) {
                    return false;
                }
            }
        }

        // Destination must be empty or occupied by an enemy piece
        Tile targetTile = board[newX][newY];
        if (targetTile.hasPiece() && targetTile.getPiece().getColor() == this.color) {
            return false;
        }

        return true;
    }


    private boolean isValidKnightMove(int newX, int newY, Tile[][] board) {
        int currentX = (int) (oldX / ChessGame.TILE_SIZE);
        int currentY = (int) (oldY / ChessGame.TILE_SIZE);

        int deltaX = Math.abs(newX - currentX);
        int deltaY = Math.abs(newY - currentY);

        // Knights move in an L-shape: 2 by 1 or 1 by 2
        if ((deltaX == 2 && deltaY == 1) || (deltaX == 1 && deltaY == 2)) {
            // Destination must be empty or occupied by an enemy piece
            Tile targetTile = board[newX][newY];
            if (!targetTile.hasPiece() || targetTile.getPiece().getColor() != this.color) {
                return true;
            }
        }

        return false;
    }


    private boolean isValidBishopMove(int newX, int newY, Tile[][] board) {
        int currentX = (int) (oldX / ChessGame.TILE_SIZE);
        int currentY = (int) (oldY / ChessGame.TILE_SIZE);

        int deltaX = Math.abs(newX - currentX);
        int deltaY = Math.abs(newY - currentY);

        // Bishops move diagonally, so deltaX must equal deltaY
        if (deltaX != deltaY) {
            return false;
        }

        // Check path for any pieces
        int stepX = (newX > currentX) ? 1 : -1;
        int stepY = (newY > currentY) ? 1 : -1;
        int x = currentX + stepX;
        int y = currentY + stepY;

        while (x != newX && y != newY) {
            if (board[x][y].hasPiece()) {
                return false;
            }
            x += stepX;
            y += stepY;
        }

        // Destination must be empty or occupied by an enemy piece
        Tile targetTile = board[newX][newY];
        if (targetTile.hasPiece() && targetTile.getPiece().getColor() == this.color) {
            return false;
        }

        return true;
    }


    private boolean isValidQueenMove(int newX, int newY, Tile[][] board) {
        int currentX = (int) (oldX / ChessGame.TILE_SIZE);
        int currentY = (int) (oldY / ChessGame.TILE_SIZE);

        int deltaX = Math.abs(newX - currentX);
        int deltaY = Math.abs(newY - currentY);

        // Check if it's a valid rook move or a valid bishop move
        return isValidRookMove(newX, newY, board) || isValidBishopMove(newX, newY, board);
    }


    private boolean isValidKingMove(int newX, int newY, Tile[][] board) {
        int currentX = (int) (oldX / ChessGame.TILE_SIZE);
        int currentY = (int) (oldY / ChessGame.TILE_SIZE);

        int deltaX = Math.abs(newX - currentX);
        int deltaY = Math.abs(newY - currentY);

        // King moves only one square in any direction
        if (deltaX <= 1 && deltaY <= 1) {
            // Destination must be empty or occupied by an enemy piece
            Tile targetTile = board[newX][newY];
            if (!targetTile.hasPiece() || targetTile.getPiece().getColor() != this.color) {
                return true;
            }
        }

        return false;
    }

}