package unibanden.ist.gorillas.frontend;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import unibanden.ist.gameengine.Engine;
import unibanden.ist.gorillas.game.WinListener;

/**
 * Responsible: Emil Dissing s214912
 * Controller for the level-view.fxml
 */
public class LevelController implements Initializable, WinListener {
    @FXML
    public Canvas canvas;
    
    @FXML
	public AnchorPane pane;
	@FXML
	private Label winLabel;
    
    private Stage stage;
	private Scene scene;
	private Parent root;

	
    /*
     * switches to the main menu when ran
     * @param event this is ran when a button is clicked
     */
	public void back(ActionEvent event) throws IOException {
		//loads main menu fxml
		Parent root = FXMLLoader.load(getClass().getResource("/unibanden/ist/gorillas/frontend/main-menu.fxml"));
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		String css = this.getClass().getResource("/unibanden/ist/gorillas/frontend/menu.css").toExternalForm();
    	scene.getStylesheets().add(css);
		stage.setScene(scene);
		//stops the game
		Engine.stop();
		stage.show();
    }
    
	/*
	 * starts the game
	 */
	public void start(){
		System.out.println(canvas.getWidth() + " x " + canvas.getHeight());
		if (!Engine.getTurnedOn()){
			MenuMaster.startGame(canvas.getScene(), canvas, this);
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		//sets canvas size
		canvas.setWidth(MenuMaster.width);
		canvas.setHeight(MenuMaster.height);
		winLabel.setText("");
	}
	
	/*
	 * Writes player x won when a player wins
	 */
	@Override
	public void playerWon(int id) {
		winLabel.setText("Player " + id + " won!");
	}
}
