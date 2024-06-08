package fr.univamu.iut.s201_chess;


import fr.univamu.iut.s201_chess.ButtonController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.function.IntToLongFunction;

public class TypeNameController {

    @FXML
    private ImageView logo;

    @FXML
    private Button launchGame;

    @FXML
    private Button back;

    @FXML
    private TextField player1Name;

    @FXML
    private TextField player2Name;



    public void loadPage(){
        ButtonController buttonController = new ButtonController();
        buttonController.initTypeName(launchGame, back);
    }


    public void  getPlayersNickname() {
        String player1 = player1Name.getText();
        String player2 = player2Name.getText();
        try {
            File myObj = new File("Players.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        Random r = new Random();

        if(player1.isEmpty()){
            player1 = "Guest" + r.nextLong((9999999999L - 1000000000L)-1);
        }
        if(player2.isEmpty()){
            player2 = "Guest" + r.nextLong((9999999999L - 1000000000L)-1);
            while(player1.equals(player2)){
                player2 = "Guest" + r.nextLong((9999999999L - 1000000000L)-1);

            }
        }

    }

    @FXML
    public void initialize() {
        // Set the logo image
        logo.setImage(new Image(getClass().getResource("/fr/univamu/iut/s201_chess/HomePage/icons/logo.png").toExternalForm()));
    }
}
