module fr.univamu.iut.s201_chess {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;


    opens fr.univamu.iut.s201_chess to javafx.fxml;
    exports fr.univamu.iut.s201_chess;
    opens fr.univamu.iut.s201_chess.HomePage to javafx.fxml;
    exports fr.univamu.iut.s201_chess.HomePage;

}