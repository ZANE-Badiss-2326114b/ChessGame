package fr.univamu.iut.s201_chess;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Piece extends StackPane {
    private PieceType type;
    private PieceColor color;
    private double mouseX, mouseY;
    private double oldX, oldY;

    public Piece(PieceType type, PieceColor color, int x, int y) {
        this.type = type;
        this.color = color;

        initMove(x, y);

        ImageView img = new ImageView();
        if (color == PieceColor.BLACK) {
            switch (type) {
                case KING -> img.setImage(new Image(getClass().getResourceAsStream("/fr/univamu/iut/s201_chess/img/blackPieces/bk.png")));
                case PAWN -> img.setImage(new Image(getClass().getResourceAsStream("/fr/univamu/iut/s201_chess/img/blackPieces/bp.png")));
                case ROOK -> img.setImage(new Image(getClass().getResourceAsStream("/fr/univamu/iut/s201_chess/img/blackPieces/br.png")));
                case QUEEN -> img.setImage(new Image(getClass().getResourceAsStream("/fr/univamu/iut/s201_chess/img/blackPieces/bq.png")));
                case BISHOP -> img.setImage(new Image(getClass().getResourceAsStream("/fr/univamu/iut/s201_chess/img/blackPieces/bb.png")));
                case KNIGHT -> img.setImage(new Image(getClass().getResourceAsStream("/fr/univamu/iut/s201_chess/img/blackPieces/bn.png")));
            }
        } else if (color == PieceColor.WHITE) {
            switch (type) {
                case KING -> img.setImage(new Image(getClass().getResourceAsStream("/fr/univamu/iut/s201_chess/img/whitePieces/wk.png")));
                case PAWN -> img.setImage(new Image(getClass().getResourceAsStream("/fr/univamu/iut/s201_chess/img/whitePieces/wp.png")));
                case ROOK -> img.setImage(new Image(getClass().getResourceAsStream("/fr/univamu/iut/s201_chess/img/whitePieces/wr.png")));
                case QUEEN -> img.setImage(new Image(getClass().getResourceAsStream("/fr/univamu/iut/s201_chess/img/whitePieces/wq.png")));
                case BISHOP -> img.setImage(new Image(getClass().getResourceAsStream("/fr/univamu/iut/s201_chess/img/whitePieces/wb.png")));
                case KNIGHT -> img.setImage(new Image(getClass().getResourceAsStream("/fr/univamu/iut/s201_chess/img/whitePieces/wn.png")));
            }
        }
        img.setFitHeight(60);
        img.setFitWidth(60);
        getChildren().add(img);

        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        setOnMouseDragged(e -> {
            relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
        });
    }

    private void initMove(int x, int y) {
        oldX = x * ChessGame.TILE_SIZE;
        oldY = y * ChessGame.TILE_SIZE;
        relocate(oldX, oldY);
    }

    public void move(int x, int y, Tile[][] board, Group pieceGroup) {
        int oldTileX = (int) (oldX / ChessGame.TILE_SIZE);
        int oldTileY = (int) (oldY / ChessGame.TILE_SIZE);

        oldX = x * ChessGame.TILE_SIZE;
        oldY = y * ChessGame.TILE_SIZE;

        Tile targetTile = board[x][y];
        if (targetTile.hasPiece() && targetTile.getPiece().getColor() != this.color) {
            Piece capturedPiece = targetTile.getPiece();
            if(capturedPiece.getType() == PieceType.KING){
                pieceGroup.getChildren().remove(capturedPiece);
                ChessGameController.endGame(capturedPiece.getColor());
            }
            else{
                pieceGroup.getChildren().remove(capturedPiece);
            }
        }

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
        }
        return false;
    }
    private boolean isValidPawnMove(int newX, int newY, Tile[][] board) {
        int currentX = (int) (oldX / ChessGame.TILE_SIZE);
        int currentY = (int) (oldY / ChessGame.TILE_SIZE);
        int deltaY = newY - currentY;
        int deltaX = newX - currentX;

        if (color == PieceColor.WHITE) {
            if (deltaX == 0) {
                if (deltaY == -1 && !board[newX][newY].hasPiece()) {
                    return true;
                } else if (currentY == 6 && deltaY == -2 && !board[newX][newY].hasPiece() && !board[newX][newY + 1].hasPiece()) {
                    return true;
                }
            } else if (Math.abs(deltaX) == 1 && deltaY == -1 && board[newX][newY].hasPiece() && board[newX][newY].getPiece().getColor() != this.color) {
                return true;
            }
        } else if (color == PieceColor.BLACK) {
            if (deltaX == 0) {
                if (deltaY == 1 && !board[newX][newY].hasPiece()) {
                    return true;
                } else if (currentY == 1 && deltaY == 2 && !board[newX][newY].hasPiece() && !board[newX][newY - 1].hasPiece()) {
                    return true;
                }
            } else if (Math.abs(deltaX) == 1 && deltaY == 1 && board[newX][newY].hasPiece() && board[newX][newY].getPiece().getColor() != this.color) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidRookMove(int newX, int newY, Tile[][] board) {
        int currentX = (int) (oldX / ChessGame.TILE_SIZE);
        int currentY = (int) (oldY / ChessGame.TILE_SIZE);

        if (currentX != newX && currentY != newY) {
            return false;
        }

        if (currentX == newX) {
            int step = (newY > currentY) ? 1 : -1;
            for (int y = currentY + step; y != newY; y += step) {
                if (board[currentX][y].hasPiece()) {
                    return false;
                }
            }
        } else if (currentY == newY) {
            int step = (newX > currentX) ? 1 : -1;
            for (int x = currentX + step; x != newX; x += step) {
                if (board[x][currentY].hasPiece()) {
                    return false;
                }
            }
        }

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

        if ((deltaX == 2 && deltaY == 1) || (deltaX == 1 && deltaY == 2)) {
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

        if (deltaX != deltaY) {
            return false;
        }

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

        Tile targetTile = board[newX][newY];
        if (targetTile.hasPiece() && targetTile.getPiece().getColor() == this.color) {
            return false;
        }

        return true;
    }

    private boolean isValidQueenMove(int newX, int newY, Tile[][] board) {
        return isValidRookMove(newX, newY, board) || isValidBishopMove(newX, newY, board);
    }

    private boolean isValidKingMove(int newX, int newY, Tile[][] board) {
        int currentX = (int) (oldX / ChessGame.TILE_SIZE);
        int currentY = (int) (oldY / ChessGame.TILE_SIZE);

        int deltaX = Math.abs(newX - currentX);
        int deltaY = Math.abs(newY - currentY);

        if (deltaX <= 1 && deltaY <= 1) {
            Tile targetTile = board[newX][newY];
            if (!targetTile.hasPiece() || targetTile.getPiece().getColor() != this.color) {
                return true;
            }
        }

        return false;
    }
}