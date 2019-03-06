package controllers;

import javafx.stage.Stage;

public class ViewController {

	Stage window;
	GameController game;
	MainMenuController mainMenu;
	GameSetupController gameSetup;
	PlayerTurnController playerTurn;
	IntermissionController intermission;
	
	public ViewController(Stage window) {
		this.window = window;
	}
}
