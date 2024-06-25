module unibanden.ist.gorillas {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
    requires java.desktop;


    opens unibanden.ist.gorillas.game to javafx.fxml;
    exports unibanden.ist.gameengine;
    opens unibanden.ist.gameengine to javafx.fxml;
    exports unibanden.ist.gorillas.frontend;
    opens unibanden.ist.gorillas.frontend to javafx.fxml;
    exports unibanden.ist.gorillas.game;
}