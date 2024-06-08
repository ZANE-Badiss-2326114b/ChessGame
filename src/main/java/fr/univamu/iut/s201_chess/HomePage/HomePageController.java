package fr.univamu.iut.s201_chess.HomePage;

import fr.univamu.iut.s201_chess.ButtonController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HomePageController {

    @FXML
    private ImageView logo;

    @FXML
    private Button B1v1;

    @FXML
    private Button Bc;

    public void loadPage(){
        ButtonController buttonController = new ButtonController();
        buttonController.initHomePage(B1v1, Bc);
    }



//    @FXML
//    public void 1v1() throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("ChessGame.fxml"));
//        Scene scene = new Scene(root);
//        Stage primaryStage = (Stage) B1v1.getScene().getWindow();
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//    @FXML
//    public void 1vC(ActionEvent event) {
//
//        Scene scene = new Scene(root);
//        Stage primaryStage = (Stage) B1v1.getScene().getWindow();
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }

    @FXML
    public void initialize() {



        // Set the logo image
        logo.setImage(new Image(getClass().getResourceAsStream("icons/logo.png")));
        logo.setFitHeight(300);
        logo.setFitWidth(949);

    }


}