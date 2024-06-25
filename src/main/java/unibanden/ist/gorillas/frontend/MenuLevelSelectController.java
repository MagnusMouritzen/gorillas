package unibanden.ist.gorillas.frontend;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import unibanden.ist.gorillas.game.MapCity;
import unibanden.ist.gorillas.game.MapHill;
import unibanden.ist.gorillas.game.MapPlain;

/**
 * Responsible: Emil Dissing s214912
 * Controller for the menu-level-select.fxml
 */
public class MenuLevelSelectController {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	
	/*
	 * creates a new CityMap in MenuMaster
	 */
	public void city(ActionEvent event) throws IOException {
		//creates a City Map
		MenuMaster.map = new MapCity();
		loadGame(event);
	}
	
	/*
	 * creates a new PlainMap in MenuMaster
	 */
	public void plain(ActionEvent event) throws IOException {
		//created a Plain Map
		MenuMaster.map = new MapPlain();
		loadGame(event);
	}
	
	/*
	 * creates a new HillMap in MenuMaster
	 */
	public void hill(ActionEvent event) throws IOException {
		//creates a Hill Map
		MenuMaster.map = new MapHill();
		loadGame(event);
	}

	/*
	 * Swaps to level view and starts the game
	 */
	public void loadGame(ActionEvent event) throws IOException {
		//loads level view
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/unibanden/ist/gorillas/frontend/level-view.fxml"));
		root = loader.load();
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		String css = this.getClass().getResource("/unibanden/ist/gorillas/frontend/level.css").toExternalForm();
    	scene.getStylesheets().add(css);
		stage.setScene(scene);
		stage.show();
		//runs the start game function
		((LevelController)loader.getController()).start();
	}
	
	/*
	 * Swaps to the main menu when ran
	 */
	public void menuMain(ActionEvent event) throws IOException {
		//loads main-menu-fxml
		root = FXMLLoader.load(getClass().getResource("/unibanden/ist/gorillas/frontend/main-menu.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		String css = this.getClass().getResource("/unibanden/ist/gorillas/frontend/menu.css").toExternalForm();
    	scene.getStylesheets().add(css);
		stage.setScene(scene);
		stage.show();
		
	}

}
