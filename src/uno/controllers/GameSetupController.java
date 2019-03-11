package uno.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GameSetupController {
	
	private String[] names = new String[10];
	
    @FXML private TextField p1TextField;
    @FXML private TextField p2TextField;
    @FXML private TextField p3TextField;
    @FXML private TextField p4TextField;
    @FXML private TextField p5TextField;
    @FXML private TextField p6TextField;
    @FXML private TextField p7TextField;
    @FXML private TextField p8TextField;
    @FXML private TextField p9TextField;
    @FXML private TextField p10TextField;
    @FXML private Label label1;
    @FXML private Label label2;
    @FXML private Label label3;
    @FXML private Label label4;
    @FXML private Label label5;
    @FXML private Label label6;
	@FXML private Label label7;
	@FXML private Label label8;
	@FXML private Label label9;
	@FXML private Label label10;
	@FXML private Button startButton;
	@FXML private Label playerAmountLabel;
	@FXML private CheckBox drawSetting;
	@FXML private CheckBox stackSetting;
	
	@FXML void startGame(ActionEvent event) {
		GameController game = new GameController();
		getNames();
		
		if(checkPlayerAmount() > 1) {
			game.initializeGame(getPlayerNames(), drawSetting.isSelected(), stackSetting.isSelected());
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/uno/fxml/IntermediateScene.fxml"));
				Parent intermissionParent = loader.load();
				Scene intermissionScene = new Scene(intermissionParent);
				
				Stage window = (Stage) ((Node)event.getTarget()).getScene().getWindow();
				
				IntermissionController controller = loader.getController();
				controller.setupScene(game);
				window.setScene(intermissionScene);
				window.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			playerAmountLabel.setText("You must enter at least 2 players.");
		}
    }
	
	private void getNames() {
		if(p1TextField.getText().length() > 0) {
			names[0] = p1TextField.getText();
		}
		if(p2TextField.getText().length() > 0) {
			names[1] = p2TextField.getText();
		}
		if(p3TextField.getText().length() > 0) {
			names[2] = p3TextField.getText();
		}
		if(p4TextField.getText().length() > 0) {
			names[3] = p4TextField.getText();
		}
		if(p5TextField.getText().length() > 0) {
			names[4] = p5TextField.getText();
		}
		if(p6TextField.getText().length() > 0) {
			names[5] = p6TextField.getText();
		}
		if(p7TextField.getText().length() > 0) {
			names[6] = p7TextField.getText();
		}
		if(p8TextField.getText().length() > 0) {
			names[7] = p8TextField.getText();
		}
		if(p9TextField.getText().length() > 0) {
			names[8] = p9TextField.getText();
		}
		if(p10TextField.getText().length() > 0) {
			names[9] = p10TextField.getText();
		}
	}
	
	private int checkPlayerAmount() {
		int playerAmount = 0;
		for(String name : names) {
			if(name != null) {
				playerAmount++;
			}
		}
		
		return playerAmount;
	}
	
	public static void addTextLimiter(final TextField tf) {
		int maxLength = 10;
		
	    tf.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
	            if (tf.getText().length() > maxLength) {
	                String s = tf.getText().substring(0, maxLength);
	                tf.setText(s);
	            }
	        }
	    });
	}
	
	private String[] getPlayerNames() {
		String[] playerNames = new String[checkPlayerAmount()];
		ArrayList<String> theNames = new ArrayList<>();
		
		for(String name : names) {
			if(name != null) {
				theNames.add(name);
			}
		}
		for(int i = 0; i < theNames.size(); i++) {
			playerNames[i] = theNames.get(i);
		}
		
		return playerNames;
	}
	
	@FXML
	public void initialize() {
		addTextLimiter(p1TextField);
		addTextLimiter(p2TextField);
		addTextLimiter(p3TextField);
		addTextLimiter(p4TextField);
		addTextLimiter(p5TextField);
		addTextLimiter(p6TextField);
		addTextLimiter(p7TextField);
		addTextLimiter(p8TextField);
		addTextLimiter(p9TextField);
		addTextLimiter(p10TextField);
	}
}
