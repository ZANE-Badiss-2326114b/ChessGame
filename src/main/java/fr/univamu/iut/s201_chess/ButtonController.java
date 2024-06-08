package fr.univamu.iut.s201_chess;



import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import fr.univamu.iut.s201_chess.TypeNameController;

import java.io.IOException;

public class ButtonController {


    public void changeScene(String sceneName, Button button) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneName));
            Stage stage = (Stage) button.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initHomePage(Button B1v1, Button Bc) {

        B1v1.setOnMouseClicked(actionEvent -> {
            changeScene("TypeName.fxml", B1v1);
        });
        Bc.setOnMouseClicked(actionEvent -> {
            changeScene("TypeName.fxml", Bc);
        });
    }

    public void initTypeName(Button launchGame, Button back) {

        launchGame.setOnMouseClicked(actionEvent -> {
            TypeNameController typeNameController = new TypeNameController();
            typeNameController.getPlayersNickname();
            changeScene("ChessGame.fxml", launchGame);


        });
        back.setOnMouseClicked(actionEvent -> {
            changeScene("Homepage/HomePage.fxml", back);
        });
    }


}
