package unibanden.ist.gorillas.frontend;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import unibanden.ist.gorillas.game.GameModeClassic;
import unibanden.ist.gorillas.game.GameModeModern;
import unibanden.ist.gorillas.game.GameModeRumble;

/**
 * Responsible: Emil Dissing s214912
 * Controller for the mode-select.fxml
 */
public class ModeSelectController {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	/*
	 * creates new Modern GameMode in menu master
	 */
	public void modern(ActionEvent event) throws IOException {
		//creates an instance of modern gamemode
		MenuMaster.gameMode = new GameModeModern(MenuMaster.windMax, MenuMaster.generateAllowedPickupTypes());
		levelMenu(event);
	}
	
	/*
	 * creates new GameModeClassic GameMode in menu master
	 */
	public void classic(ActionEvent event) throws IOException {
		//creates an instance of classic gamemode
		MenuMaster.gameMode = new GameModeClassic(MenuMaster.windMax);
		levelMenu(event);
	}
	
	/*
	 * creates new Rumble GameMode in menu master
	 */
	public void rumble(ActionEvent event) throws IOException {
		//creates an instance of rumble gamemode
		MenuMaster.gameMode = new GameModeRumble(MenuMaster.generateAllowedPickupTypes()) ;
		levelMenu(event);
	}
	
	/*
	 * Swaps to the level select menu when ran
	 */
	public void levelMenu(ActionEvent event) throws IOException {
		//loads menu-level-select.fxml
		root = FXMLLoader.load(getClass().getResource("/unibanden/ist/gorillas/frontend/menu-level-select.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		String css = this.getClass().getResource("/unibanden/ist/gorillas/frontend/menu-level.css").toExternalForm();
    	scene.getStylesheets().add(css);
		stage.setScene(scene);
		stage.show();
		
	}
	
	/*
	 * Swaps to the main menu when ran
	 */
	public void menuMain(ActionEvent event) throws IOException {
		//loads main-menu.fxml
		root = FXMLLoader.load(getClass().getResource("/unibanden/ist/gorillas/frontend/main-menu.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		String css = this.getClass().getResource("/unibanden/ist/gorillas/frontend/menu.css").toExternalForm();
    	scene.getStylesheets().add(css);
		stage.setScene(scene);
		stage.show();
		
	}

}
