package fr.univamu.iut.s201_chess.HomePage;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HomePageController {

    @FXML
    private ImageView logo;

    @FXML
    public void initialize() {
        // Set the logo image
        logo.setImage(new Image(getClass().getResourceAsStream("icons/logo.png")));
        logo.setFitHeight(300);
        logo.setFitWidth(949);

    }


}