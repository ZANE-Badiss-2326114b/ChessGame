package fr.univamu.iut.s201_chess;


import fr.univamu.iut.s201_chess.ButtonController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileWriter;
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


        String player1;
        String player2;
        Random r = new Random();

        if(player1Name == null || player1Name.getText().isEmpty()){
            player1 = "Guest" + r.nextLong((9999999999L - 1000000000L)-1);
        }
        else{
            player1 = player1Name.getText();
        }
        if(player1Name == null || player1Name.getText().isEmpty()){
            player2 = "Guest" + r.nextLong((9999999999L - 1000000000L)-1);
            while(player1.equals(player2)){
                player2 = "Guest" + r.nextLong((9999999999L - 1000000000L)-1);

            }
        }
        else {
            player2 = player2Name.getText();
        }

        File myObj = new File("Players.txt");
        try {
            myObj.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();

        }

        FileWriter writer = null;
        try {
            writer = new FileWriter("Players.txt");
        writer.write(player1 + "\n" + player2);
        writer.close();
        System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        // Set the logo image
        logo.setImage(new Image(getClass().getResource("/fr/univamu/iut/s201_chess/HomePage/icons/logo.png").toExternalForm()));
    }
}
