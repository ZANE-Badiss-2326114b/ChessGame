package fr.univamu.iut.s201_chess;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

        move(x, y);

        //Ellipse bg = new Ellipse(ChessGame.TILE_SIZE * 0.3125, ChessGame.TILE_SIZE * 0.26);
        //bg.setFill(color == PieceColor.WHITE ? Color.WHITE : Color.BLACK);
        //bg.setStroke(Color.BLACK);
        //bg.setStrokeWidth(ChessGame.TILE_SIZE * 0.03);

        //bg.setTranslateX((ChessGame.TILE_SIZE - ChessGame.TILE_SIZE * 0.3125 * 2) / 2);
        //bg.setTranslateY((ChessGame.TILE_SIZE - ChessGame.TILE_SIZE * 0.26 * 2) / 2);

        Text text = new Text(type.toString().substring(0, 1));
        text.setFill(color == PieceColor.WHITE ? Color.BLACK : Color.WHITE);
        text.setTranslateX((ChessGame.TILE_SIZE - ChessGame.TILE_SIZE * 0.3125 * 2) / 2);
        text.setTranslateY((ChessGame.TILE_SIZE - ChessGame.TILE_SIZE * 0.26 * 2) / 2);

        ImageView img = new ImageView();
        img.setImage(new Image(getClass().getResourceAsStream("/img/blackPieces/bb.png")));




        getChildren().add(img);

        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        setOnMouseDragged(e -> {
            relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
        });
    }

    public void move(int x, int y) {
        oldX = x * ChessGame.TILE_SIZE;
        oldY = y * ChessGame.TILE_SIZE;
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
}

