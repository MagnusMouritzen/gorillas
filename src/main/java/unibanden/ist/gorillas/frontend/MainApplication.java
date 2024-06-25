package unibanden.ist.gorillas.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import unibanden.ist.gorillas.game.PlayerMovement;

import java.io.IOException;

/**
 * Responsible: Emil Dissing s214912
 * Starts the application
 */
public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
    	PlayerMovement.class.getResource("/unibanden/ist/gorillas/game/monke.col");
    	PlayerMovement.class.getResource("/unibanden/ist/gorillas/game/monke.vis");
    	//loads settings
        MenuMaster.initialise();
        //loads main-menu.fxml
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/unibanden/ist/gorillas/frontend/main-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        //sets icon
        Image icon = new Image(MainApplication.class.getResourceAsStream("/unibanden/ist/gorillas/frontend/monke.png"));
        stage.getIcons().add(icon);
        //sets window title
        stage.setTitle("Gorillas");
        //sets size (+39 is because of the menu bar at the top)
        stage.setWidth(MenuMaster.width);
        stage.setHeight(MenuMaster.height + 39);
        //makes it not resizable
        stage.setResizable(false);
        //loads css
        String css = this.getClass().getResource("/unibanden/ist/gorillas/frontend/menu.css").toExternalForm();
    	scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}