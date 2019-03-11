package controllers;

import java.io.IOException;
import java.util.ArrayList;

import enums.CardSuit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.Card;
import models.Player;

public class PlayerTurnController {
	private GameController game;
	private int currentCardDisplay = 0;
	private ObservableList<Player> players;
	private Stage window;
	
	@FXML private HBox chooseColor;
	@FXML private HBox currentPlayerCards;
	@FXML private ImageView leftArrow;
	@FXML private ImageView rightArrow;
	@FXML private ImageView cardImage0;
    @FXML private ImageView cardImage1;
    @FXML private ImageView cardImage2;
    @FXML private ImageView cardImage3;
    @FXML private ImageView cardImage4;
    @FXML private ImageView cardImage5;
    @FXML private ImageView cardImage6;
    @FXML private ImageView cardImage7;
    @FXML private ImageView cardImage8;
    @FXML private ImageView cardImage9;
    @FXML private ImageView turnRotation;
    @FXML private ImageView currentCardImage;
    @FXML private ImageView drawPile1;
    @FXML private ImageView drawPile2;
    @FXML private ImageView drawPile3;
    @FXML private ImageView drawPile4;
    @FXML private TableView<Player> playersInfo;
    @FXML private TableColumn<Player, String> names;
    @FXML private TableColumn<Player, Integer> numberOfCards;
    @FXML private Label currentPlayerName;
    @FXML private Button nextCardSet;
    @FXML private Button previousCardSet;
    @FXML private Button playCardButton;
    @FXML private Button drawCardButton;
    @FXML private Button redButton;
    @FXML private Button blueButton;
    @FXML private Button greenButton;
    @FXML private Button yellowButton;
    @FXML private Label wildCardPrompt;
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
    
    private void playCard(int index) {
    	if(game.playCard(currentCardDisplay + index)) {
			if(!wildCardClicked()) {
				updateScene();
				game.setCurrentPlayer();
				if(game.getWinner() == null) {
					goToIntermediateScene();
				} else {
					goToWinScreen();
				}
			}
		}
		
    }
    
    private boolean wildCardClicked() {
		boolean isWild = game.getCurrentCard().getSuit() == null;
		if(isWild && game.getCurrentPlayer().getHand().size() > 0) {
			disableArrowButtons();
			setChooseColorVisibility(true);
			
			redButton.setOnAction(e -> setWildCardColor(CardSuit.RED));
			yellowButton.setOnAction(e -> setWildCardColor(CardSuit.YELLOW));
			greenButton.setOnAction(e -> setWildCardColor(CardSuit.GREEN));
			blueButton.setOnAction(e -> setWildCardColor(CardSuit.BLUE));
		}
		if(game.getWinner() != null) {
			goToWinScreen();
		}
		return isWild;
	}
    
    private void setWildCardColor(CardSuit color) {
		game.setColor(color);
		updateScene();
		game.setCurrentPlayer();;
		goToIntermediateScene();
	}
    
    private void setFirstWild(CardSuit color) {
    	game.setColor(color);
    	setPlayerCardImages();
    	setChooseColorVisibility(false);
    }
    
    @FXML void drawCard() {
    	if(game.getMultiDrawSetting()) {
    		game.drawCard(1);
    		updateScene();
    		centerCards();
    	} else {
    		game.drawCard(1);
    		updateScene();
    		centerCards();
    		if(!game.checkForMatch()) {
    			game.setCurrentPlayer();
    			goToIntermediateScene();
    		}
    	}
    }
	
	public void setupScene(GameController currentGame, Stage currentWindow) {
		window = currentWindow;
		game = currentGame;
		currentPlayerName.setText(game.getCurrentPlayer().getName());
		names.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
		numberOfCards.setCellValueFactory(new PropertyValueFactory<Player, Integer>("numberOfCards"));
		double height = 26 + (game.getPlayers().length * 24);
		playersInfo.setPrefHeight(height);
		playersInfo.setItems(getPlayers());
		setPlayerCardImages();
		setChooseColorVisibility(false);
		setArrowVisibility();
		setTurnRotationImage();
		centerCards();
		if(game.getCurrentCard().getSuit() == null) {
			setChooseColorVisibility(true);
			redButton.setOnAction(e -> setFirstWild(CardSuit.RED));
			yellowButton.setOnAction(e -> setFirstWild(CardSuit.YELLOW));
			greenButton.setOnAction(e -> setFirstWild(CardSuit.GREEN));
			blueButton.setOnAction(e -> setFirstWild(CardSuit.BLUE));
		}
		checkForMatch();
		
		cardImage0.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> playCard(0));
		cardImage1.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> playCard(1));
		cardImage2.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> playCard(2));
		cardImage3.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> playCard(3));
		cardImage4.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> playCard(4));
		cardImage5.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> playCard(5));
		cardImage6.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> playCard(6));
		cardImage7.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> playCard(7));
		cardImage8.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> playCard(8));
		cardImage9.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> playCard(9));
		setDrawPileCards();
		setFeedMessages();
	}
	
	private void updateScene() {
		updatePlayersInfo();
		setPlayerCardImages();
		setChooseColorVisibility(false);
		setArrowVisibility();
		setDrawPileCards();
		setFeedMessages();
		setTurnRotationImage();
		checkForMatch();
	}
	
	@FXML void showPreviousCardSet() {
		if(currentCardDisplay > 0) {
			currentCardDisplay -= 10;
			updateScene();
			
			double width = 1060;
			double windowWidth = 1200;
			currentPlayerCards.setLayoutX((windowWidth/2) - (width/2));
		}
	}
	
	@FXML void showNextCardSet() {
		if(currentCardDisplay < ((game.getCurrentPlayer().getHand().size()-1) / 10) * 10) {
			currentCardDisplay += 10;
			updateScene();
			centerCards();
		}
	}
	
	private void updatePlayersInfo() {
		players.removeAll(players);
		names.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
		numberOfCards.setCellValueFactory(new PropertyValueFactory<Player, Integer>("numberOfCards"));
		playersInfo.setItems(getPlayers());
	}
	
	public ObservableList<Player> getPlayers() {
		players = FXCollections.observableArrayList();
		for(Player player : game.getPlayers()) {
			players.add(player);
		}
		
		return players;
	}
	
	private void setTurnRotationImage() {
		if(game.getTurnRotation()) {
			turnRotation.setImage(new Image("file:images/clockwise_turn_rotation.png"));
		} else {
			turnRotation.setImage(new Image("file:images/counter_clockwise_turn_rotation.png"));
		}
	}
	
	private void setPlayerCardImages() {
		ArrayList<Card> currentPlayerHand = game.getCurrentPlayer().getHand();
		int counter = currentCardDisplay;
		
		currentCardImage.setImage(new Image("file:images/"+ game.getLastSeveralCards().get(4).getCardName() + ".jpg"));
		currentCardImage.setRotate(game.getLastSeveralCardRotations().get(4));
		
		for(Node image : currentPlayerCards.getChildren()) {
			if(currentPlayerHand.size() > counter) {
				Image cardImg = new Image("file:images/"+ currentPlayerHand.get(counter++).getCardName() + ".jpg");
				((ImageView) image).setImage(cardImg);
				((ImageView) image).setVisible(true);
			} else {
				((ImageView) image).setVisible(false);
			}
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
	
	private void checkForMatch() {
		if(game.checkForMatch()) {
			drawCardButton.setDisable(true);
		} else {
			drawCardButton.setDisable(false);
		}
	}
	
	private void disableArrowButtons() {
		nextCardSet.setDisable(true);
		previousCardSet.setDisable(true);
	}
	
	private void setChooseColorVisibility(Boolean isVisible) {
		wildCardPrompt.setVisible(isVisible);
		for(Node button : chooseColor.getChildren()) {
			button.setVisible(isVisible);
		}
	}
	
	private void goToIntermediateScene() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/FXML/IntermediateScene.fxml"));
			Parent intermissionParent = loader.load();
			Scene intermediateScene = new Scene(intermissionParent);
			
			IntermissionController intermission = loader.getController();
			intermission.setupScene(game);
			
			Stage newWindow = window;
			newWindow.setWidth(1200);
			newWindow.setScene(intermediateScene);
			newWindow.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void goToWinScreen() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/FXML/WinScene.fxml"));
			Parent winParent = loader.load();
			Scene winScene = new Scene(winParent);
			
			WinController controller = loader.getController();
			controller.setWinner(game.getWinner().getName());
			Stage newWindow = window;
			newWindow.setWidth(1200);
			newWindow.setScene(winScene);
			newWindow.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setArrowVisibility() {
		if(currentCardDisplay > 0) {
			leftArrow.setOpacity(1.0);
		} else {
			leftArrow.setOpacity(0.15);
		}
		if(currentCardDisplay < ((game.getCurrentPlayer().getHand().size()-1) / 10) * 10) {
			rightArrow.setOpacity(1.0);
		} else {
			rightArrow.setOpacity(0.15);
		}
	}
	
	private void centerCards() {
		if(game.getCurrentPlayer().getHand().size() < currentCardDisplay + 11) {
			double width = (game.getCurrentPlayer().getHand().size() - currentCardDisplay) * 106;
			double windowWidth = 1200;
			currentPlayerCards.setLayoutX((windowWidth/2) - (width/2));
		}
	}
}
