package fr.univamu.iut.s201_chess.HomePage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePage extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Font.loadFont(getClass().getResourceAsStream("/fr/univamu/iut/s201_chess/font/Montserrat-ExtraBold.ttf"), 20);

        Parent root = FXMLLoader.load(this.getClass().getResource("HomePage.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Chess.com");
        scene.getStylesheets().add(getClass().getResource("/fr/univamu/iut/s201_chess/css/HomePage.css").toExternalForm());


        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}