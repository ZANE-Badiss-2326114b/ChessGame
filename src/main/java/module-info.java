module your_package_name.chessgame {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
                    requires org.kordamp.bootstrapfx.core;
                requires com.almasb.fxgl.all;
    
    opens your_package_name.chessgame to javafx.fxml;
    exports your_package_name.chessgame;
}