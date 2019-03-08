package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class WinController {

	@FXML private HBox winnerHbox;
	@FXML private Label winnerName;
	@FXML private Button playAgain;
	@FXML private Button exitButton;
	
	@FXML void playAgain(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/FXML/GameSetup.fxml"));
			Parent winParent = loader.load();
			Scene winScene = new Scene(winParent);
			
			Stage window = (Stage) ((Node)event.getTarget()).getScene().getWindow();
			window.setWidth(1200);
			window.setScene(winScene);
			window.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML void exitApplication(ActionEvent event) {
		Stage window = (Stage) ((Node)event.getTarget()).getScene().getWindow();
		window.close();
	}
	
	public void setWinner(String name) {
		winnerName.setText(name);
		double width = 200 + (name.length() * 20);
		winnerHbox.setLayoutX(600-width);
	}
}
