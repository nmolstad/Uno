package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuController {
	
    @FXML private Button playButton;
	
	@FXML void goToGameSetup(ActionEvent event) {
		try {
			Parent gameSetupParent = FXMLLoader.load(getClass().getResource("/FXML/GameSetup.fxml"));
			Scene gameSetupScene = new Scene(gameSetupParent);
			
			Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			
			window.setScene(gameSetupScene);
			window.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
