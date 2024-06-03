module your_package_name.chessgame {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
                    requires org.kordamp.bootstrapfx.core;
                requires com.almasb.fxgl.all;
    
    opens fr.univamu.iut.s201_chess to javafx.fxml;
    exports fr.univamu.iut.s201_chess;
}