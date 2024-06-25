package unibanden.ist.gorillas.frontend;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import unibanden.ist.gorillas.game.Input;
import unibanden.ist.gorillas.game.PickupType;

/**
 * Responsible: Emil Dissing s214912
 * Controller for the menu-options.fxml
 */
public class MenuOptionsController implements Initializable {
	@FXML
	private AnchorPane mainPane;
	@FXML
	private AnchorPane keysPane;
	@FXML
	private AnchorPane optionsPane;
	@FXML
	private TextField playerCountText;
	@FXML
	private TextField widthText;
	@FXML
	private TextField heightText;
	
	/*
	 * Swaps to the main menu and changes window size to the chosen size and saves all settings
	 * @param event this triggers when the a button is clicked with the function linked 
	 */
	public void menuMain(ActionEvent event) throws IOException {
		System.out.println("Click");
		//checks if the size is smaller than the minimum allowed
		try {
			MenuMaster.width = Math.max(Integer.parseInt(widthText.textProperty().getValue()), MenuMaster.minWidth);
			MenuMaster.height = Math.max(Integer.parseInt(heightText.textProperty().getValue()), MenuMaster.minHeight);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		//saves the settings
		MenuMaster.save();
		//loads main menu
		Parent root = FXMLLoader.load(getClass().getResource("/unibanden/ist/gorillas/frontend/main-menu.fxml"));
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setWidth(MenuMaster.width);
		stage.setHeight(MenuMaster.height + 39);
		Scene scene = new Scene(root);
		String css = this.getClass().getResource("/unibanden/ist/gorillas/frontend/menu.css").toExternalForm();
    	scene.getStylesheets().add(css);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Updating the field for player count.
	 * @param event This triggers as soon as a key has been pressed, so this holds a keycode. Therefore, it's always a single character.
	 */
	public void playerCountHandle(KeyEvent event) {
		try {
			int num = Integer.parseInt(event.getCode().name().substring(5));  // The name is DIGITx, so this just takes the x.
				if (num >=2) {
					MenuMaster.playerCount = num;
					playerCountText.setText(String.valueOf(num));
					KeyCode[][] newKeyCodes = new KeyCode[num][Input.values().length];
					for (int i = 0; i < Math.min(MenuMaster.keyCodes.length, newKeyCodes.length); i++) {
						for (int j = 0; j < Input.values().length; j++) {
							newKeyCodes[i][j] = MenuMaster.keyCodes[i][j];
						}
					}
					MenuMaster.keyCodes = newKeyCodes;
					drawKeyCodeOptions();
				}
		}
		catch(StringIndexOutOfBoundsException e){  // If something else than a number was typed.
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Not a number");
			alert.setContentText("Please enter a number between 2 and 9");
			alert.show();
			
		}
		catch(Exception e){ //other errors
			System.out.println(e);	
		}
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		playerCountText.setText(String.valueOf(MenuMaster.playerCount));
		widthText.setText(String.valueOf(MenuMaster.width));
		heightText.setText(String.valueOf(MenuMaster.height));
		drawKeyCodeOptions();
		drawRemainingOptions();
	}

	/**
	 * Draws the pane to show the keybinds for each player. Automatically shows the correct amount of players.
	 */
	private void drawKeyCodeOptions(){
		keysPane.getChildren().clear();

		// Start positions
		double xPosition = -340;
		double yPosition = 200;

		for (int i = 0; i < MenuMaster.keyCodes.length; i++) {  // For each player
			xPosition += 400;
			yPosition -= 180;

			// Label to display which player's settings are displayed.
			Label playerText = new Label();
			playerText.setLayoutX(xPosition);
			playerText.setLayoutY(yPosition);
			playerText.setText("Player " + (i + 1) + " settings");
			keysPane.getChildren().add(playerText);

			yPosition += 30;
			for (int j = 0; j < MenuMaster.keyCodes[i].length; j++) {  // For each input option.
				// Label with the name of the input.
				Label keyText = new Label();
				keyText.setLayoutX(xPosition);
				keyText.setLayoutY(yPosition+5);
				keyText.setText(Input.values()[j].name());
				keysPane.getChildren().add(keyText);

				// Textfield to edit the KeyCode of the input.
				TextField textField = new TextField();
				textField.layoutXProperty().setValue(xPosition + 100);
				textField.layoutYProperty().setValue(yPosition);
				if (MenuMaster.keyCodes[i][j] != null){
					textField.setText(MenuMaster.keyCodes[i][j].name());
				}
				textField.editableProperty().setValue(false);
				// These two are needed for the setOnKeyPressed event.
				int finalI = i;
				int finalJ = j;
				textField.setOnKeyPressed(new EventHandler<KeyEvent>() {  // Update MenuMaster's keyCodes.
					@Override
					public void handle(KeyEvent keyEvent) {
						MenuMaster.keyCodes[finalI][finalJ] = keyEvent.getCode();
						textField.setText(keyEvent.getCode().name());
					}
				});
				keysPane.getChildren().add(textField);
				yPosition += 30;
			}
		}
	}

	/**
	 * Draws gravity spinner, wind spinner and pickup checkboxes
	 */
	private void drawRemainingOptions(){
		Spinner<Double> gravity = new Spinner();
		//position
		gravity.setLayoutX(300);
		gravity.setLayoutY(0);
		// creates a value factory for the spinner that controls limits and more (min, max, start, step)
		SpinnerValueFactory<Double> gravityFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 40, MenuMaster.gravity, 1);
		gravity.setValueFactory(gravityFactory);
		//listener that checks if the value has changed
		gravity.valueProperty().addListener(new ChangeListener<Double>() {

			@Override
			public void changed(ObservableValue<? extends Double> arg0, Double arg1, Double arg2) {
				MenuMaster.gravity = gravity.getValue();
				
			}});
		optionsPane.getChildren().add(gravity);
		
		Label gravLabel = new Label();
		//position
		gravLabel.setLayoutX(200);
		gravLabel.setLayoutY(5);
		gravLabel.setText("Gravity");
		optionsPane.getChildren().add(gravLabel);
		
		
		Spinner<Double> windMax = new Spinner();
		//position
		windMax.setLayoutX(300);
		windMax.setLayoutY(40);
		// creates a value factory for the spinner that controls limits and more (min, max, start, step)
		SpinnerValueFactory<Double> vindFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 40, MenuMaster.windMax, 1);
		windMax.setValueFactory(vindFactory);
		//listener that checks if the value has changed
		windMax.valueProperty().addListener(new ChangeListener<Double>() {

			@Override
			public void changed(ObservableValue<? extends Double> arg0, Double arg1, Double arg2) {
				MenuMaster.windMax = windMax.getValue();
				
			}});
		optionsPane.getChildren().add(windMax);
		
		Label windLabel = new Label();
		//position
		windLabel.setLayoutX(200);
		windLabel.setLayoutY(45);
		windLabel.setText("Max Wind");
		optionsPane.getChildren().add(windLabel);


		Spinner<Integer> scale = new Spinner();
		//position
		scale.setLayoutX(300);
		scale.setLayoutY(80);
		// creates a value factory for the spinner that controls limits and more (min, max, start, step)
		SpinnerValueFactory<Integer> scaleFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, MenuMaster.scale, 1);
		scale.setValueFactory(scaleFactory);
		//listener that checks if the value has changed
		scale.valueProperty().addListener(new ChangeListener<Integer>() {

			@Override
			public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer arg2) {
				MenuMaster.scale = scale.getValue();

			}});
		optionsPane.getChildren().add(scale);

		Label scaleLabel = new Label();
		//position
		scaleLabel.setLayoutX(200);
		scaleLabel.setLayoutY(85);
		scaleLabel.setText("Scale");
		optionsPane.getChildren().add(scaleLabel);
		
		//start values
		int xValue = 40;
		int yValue = 120;
		
		for(int i = 0; i< PickupType.values().length; i++) { //per pickup
			
			CheckBox pickup = new CheckBox();
			xValue += 80;
			int finalI = i;
			//sets action on the checkbox
			pickup.setOnAction((event) -> {
				CheckBox e = (CheckBox) event.getSource();
				if(e.isSelected()) {
					MenuMaster.pickups[finalI] = true;
					
					
				}
				else {
					MenuMaster.pickups[finalI] = false;
				}
			});
			//position
			pickup.setLayoutX(xValue);
			pickup.setLayoutY(yValue);
			pickup.setText(PickupType.values()[i].name());
			pickup.setTextFill(Color.WHITE);
			pickup.setSelected(MenuMaster.pickups[i]);
			optionsPane.getChildren().add(pickup);
			
			PickupType.values()[i].name();
		}
		
		
		
	}
}
