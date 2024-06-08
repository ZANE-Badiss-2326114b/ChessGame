package fr.univamu.iut.s201_chess;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChessGame extends Application {
    public static final int TILE_SIZE = 60;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ChessGame.fxml"));
//        Parent 1v1 = FXMLLoader.load(getClass().getResource("ChessGame.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
