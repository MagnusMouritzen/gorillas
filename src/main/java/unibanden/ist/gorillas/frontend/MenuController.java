package unibanden.ist.gorillas.frontend;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;

/**
 * Responsible: Emil Dissing s214912
 * Controller for the main-menu.fxml
 */
public class MenuController {
	
	@FXML
	private AnchorPane scenePane;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	
	
	/*
	 * Swaps to the mode menu when ran
	 */
	public void modeMenu(ActionEvent event) throws IOException {
		//loads mode-select.fxml
		root = FXMLLoader.load(getClass().getResource("/unibanden/ist/gorillas/frontend/mode-select.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		String css = this.getClass().getResource("/unibanden/ist/gorillas/frontend/menu-level.css").toExternalForm();
    	scene.getStylesheets().add(css);
		stage.setScene(scene);
		stage.show();
		
	}
	
	/*
	 * Swaps to the options menu when ran
	 */
	public void optionMenu(ActionEvent event) throws IOException {
		//loads menu-options.fxml
		root = FXMLLoader.load(getClass().getResource("/unibanden/ist/gorillas/frontend/menu-options.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		String css = this.getClass().getResource("/unibanden/ist/gorillas/frontend/options.css").toExternalForm();
    	scene.getStylesheets().add(css);
		stage.setScene(scene);
		stage.show();
	}
	
	/*
	 * closes the program when ran
	 */
	public void Exit(ActionEvent event) {
		stage = (Stage) scenePane.getScene().getWindow();
		stage.close();
	}
	
	
}
