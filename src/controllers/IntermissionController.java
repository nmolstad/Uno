package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.Card;
import models.Player;

public class IntermissionController {

	private GameController game;
	private ObservableList<Player> players;
	
	@FXML private HBox currentPlayerHbox;
	@FXML private Label currentPlayerName;
	@FXML private TableView<Player> playersInfo;
	@FXML private TableColumn<Player, String> names;
	@FXML private TableColumn<Player, Integer> numberOfCards;
	@FXML private ImageView turnRotation;
	@FXML private ImageView currentCardImage;
    @FXML private ImageView drawPile1;
    @FXML private ImageView drawPile2;
    @FXML private ImageView drawPile3;
    @FXML private ImageView drawPile4;
    @FXML private Label feed1;
    @FXML private Label feed2;
    @FXML private Label feed3;
    @FXML private Label feed4;
    @FXML private Label feed5;
    @FXML private Label effect1;
    @FXML private Label effect2;
    @FXML private Label effect3;
    @FXML private Label effect4;
    @FXML private Label effect5;
    @FXML private ImageView feedCard1;
    @FXML private ImageView feedCard2;
    @FXML private ImageView feedCard3;
    @FXML private ImageView feedCard4;
    @FXML private ImageView feedCard5;
    @FXML private Button readyButton;
	
	public void setupScene(GameController currentGame) {
		game = currentGame;
		currentPlayerName.setText(game.getCurrentPlayer().getName());
		names.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
		numberOfCards.setCellValueFactory(new PropertyValueFactory<Player, Integer>("numberOfCards"));
		double height = 26 + (game.getPlayers().length * 24);
		playersInfo.setPrefHeight(height);
		playersInfo.setItems(getPlayers());
		setDrawPileCards();
		setFeedMessages();
		setTurnRotationImage();
		centerCurrentPlayerHbox();
	}
	
	@FXML void playerReady(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/FXML/PlayerTurnScene.fxml"));
			Parent playerTurnParent = loader.load();
			Scene playerTurnScene = new Scene(playerTurnParent);
			
			Stage window = (Stage) ((Node)event.getTarget()).getScene().getWindow();
			
			PlayerTurnController controller = loader.getController();
			controller.setupScene(game, window);
			window.setWidth(1200);
			window.setScene(playerTurnScene);
			window.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setDrawPileCards() {
		ArrayList<Card> previousCards = game.getLastSeveralCards();
		ArrayList<Integer> previousRotations = game.getLastSeveralCardRotations();
		
		int pileSize = 0;
		for(Card card : previousCards) {
			if(card != null) {
				pileSize++;
			}
		}
		
		switch(pileSize) {
		case 5:
			drawPile4.setImage(new Image("file:images/"+ previousCards.get(0).getCardName() + ".jpg"));
			drawPile4.setRotate(previousRotations.get(0));
		case 4:
			drawPile3.setImage(new Image("file:images/"+ previousCards.get(1).getCardName() + ".jpg"));
			drawPile3.setRotate(previousRotations.get(1));
		case 3:
			drawPile2.setImage(new Image("file:images/"+ previousCards.get(2).getCardName() + ".jpg"));
			drawPile2.setRotate(previousRotations.get(2));
		case 2:
			drawPile1.setImage(new Image("file:images/"+ previousCards.get(3).getCardName() + ".jpg"));
			drawPile1.setRotate(previousRotations.get(3));
		case 1:
			currentCardImage.setImage(new Image("file:images/"+ previousCards.get(4).getCardName() + ".jpg"));
			currentCardImage.setRotate(previousRotations.get(4));
			break;
		}
	}
	
	private void setFeedMessages() {
		ArrayList<String> messages = game.getFeedMessages();
		ArrayList<Card> cards = game.getLastSeveralCards();
		ArrayList<String> effects = game.getFeedEffects();
		
		int feedActivity = 0;
		for(String message : messages) {
			if(message != null) {
				feedActivity++;
			}
		}
		
		switch(feedActivity) {
		case 5:
			feed5.setText(messages.get(0));
			feedCard5.setImage(new Image("file:images/"+ cards.get(0).getCardName() + ".jpg"));
			effect5.setText(effects.get(0));
		case 4:
			feed4.setText(messages.get(1));
			feedCard4.setImage(new Image("file:images/"+ cards.get(1).getCardName() + ".jpg"));
			effect4.setText(effects.get(1));
		case 3:
			feed3.setText(messages.get(2));
			feedCard3.setImage(new Image("file:images/"+ cards.get(2).getCardName() + ".jpg"));
			effect3.setText(effects.get(2));
		case 2:
			feed2.setText(messages.get(3));
			feedCard2.setImage(new Image("file:images/"+ cards.get(3).getCardName() + ".jpg"));
			effect2.setText(effects.get(3));
		case 1:
			feed1.setText(messages.get(4));
			feedCard1.setImage(new Image("file:images/"+ cards.get(4).getCardName() + ".jpg"));
			effect1.setText(effects.get(4));
			break;
		}
	}
	
	private void centerCurrentPlayerHbox() {
		double width = 85 + (game.getCurrentPlayer().getName().length() * 15);
		currentPlayerHbox.setLayoutX(600-width);
	}
	
	private void setTurnRotationImage() {
		if(game.getTurnRotation()) {
			turnRotation.setImage(new Image("file:images/clockwise_turn_rotation.png"));
		} else {
			turnRotation.setImage(new Image("file:images/counter_clockwise_turn_rotation.png"));
		}
	}
	
	public ObservableList<Player> getPlayers() {
		players = FXCollections.observableArrayList();
		for(Player player : game.getPlayers()) {
			players.add(player);
		}
		
		return players;
	}
}
