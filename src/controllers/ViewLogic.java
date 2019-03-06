package controllers;

import java.util.ArrayList;

import enums.CardSuit;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Card;
import models.Player;

public class ViewLogic {
	
	private GameController game = new GameController();
	private Stage stage;
	private Scene scene, gameSetup, gameScene, postGame;
	private VBox root;
	private GridPane playerInfo;
	private TextField numOfPlayers;
	private Label invalid, currentPlayerLabel, currentPlayer;
	private int playerAmount;
	private Image currentCardImg;
	private ImageView currentCardImage, leftArrow, rightArrow;
	private HBox currentPlayerCards, chooseColor, cardLevel;
	private Button nextPlayer, drawCard;
	
	public String[] playerNames;
    
	private final int windowWidth = 1200;
	private final int windowHeight = 900;
	private int currentCardDisplay = 0;
	
	public void playButtonClicked() {
		boolean isValid = false;
		
		
		isValid = playerAmount > 1 && playerAmount < 11;
		
		if(isValid) {
			createGameScene();
		} else {
			invalid.setText("Enter a number between 2 and 10.");
		}
	}

	public void createGameScene() {
		root = new VBox();
		HBox currentCardStatus = new HBox();
		cardLevel = new HBox();
		currentPlayerCards = new HBox();
		chooseColor = new HBox();
		root.setPadding(new Insets(20,20,20,20));
		currentPlayer = new Label();
		
		playerInfo = new GridPane();
		playerInfo.setPadding(new Insets(10,10,10,10));
		playerInfo.setVgap(5);
		playerInfo.setHgap(5);
		
		Label playerNameTitle = new Label("Player");
		Label numOfCardsTitle = new Label("Number of Cards");
		playerInfo.add(playerNameTitle, 0, 0);
		playerInfo.add(numOfCardsTitle, 1, 0);
		
		drawCard = new Button("Draw Card");
		nextPlayer = new Button("Next player");
		
		Label currentCard = new Label("Current card");
		
		drawCard.setOnAction(e -> {
			game.drawCard(1);
			for(Node card : currentPlayerCards.getChildren()) {
				((ImageView) card).setImage(null);
			}
			updateGameScene();
			nextPlayer();
		});
		
//		game.initializeGame(playerAmount);
		populatePlayerInfo();
		
		currentPlayerLabel = new Label("Current player");
		currentPlayer.setText(game.getCurrentPlayer().getName());
		
		currentCardImg = new Image("file:cardImages/"+ game.getCurrentCard().getCardName() + ".jpg");
		currentCardImage = new ImageView(currentCardImg);
		
		Image rArrow = new Image("file:cardImages/rightArrow.jpg");
		rightArrow = new ImageView(rArrow);
		Image lArrow = new Image("file:cardImages/leftArrow.jpg");
		leftArrow = new ImageView(lArrow);
		
		wildCardClicked();
		
		leftArrow.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			if(currentCardDisplay > 0) {
				currentCardDisplay -= 10;
				updateCurrentPlayerCards();
			}
		});
		
		rightArrow.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			if(currentCardDisplay < ((game.getCurrentPlayer().getHand().size()-1) / 10) * 10) {
				currentCardDisplay += 10;
				updateCurrentPlayerCards();
			}
		});
		
		ImageView playerCardImage0 = new ImageView();
		ImageView playerCardImage1 = new ImageView();
		ImageView playerCardImage2 = new ImageView();
		ImageView playerCardImage3 = new ImageView();
		ImageView playerCardImage4 = new ImageView();
		ImageView playerCardImage5 = new ImageView();
		ImageView playerCardImage6 = new ImageView();
		ImageView playerCardImage7 = new ImageView();
		ImageView playerCardImage8 = new ImageView();
		ImageView playerCardImage9 = new ImageView();
		
		playerCardImage0.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> cardClicked(0));
		playerCardImage1.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> cardClicked(1));
		playerCardImage2.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> cardClicked(2));
		playerCardImage3.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> cardClicked(3));
		playerCardImage4.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> cardClicked(4));
		playerCardImage5.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> cardClicked(5));
		playerCardImage6.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> cardClicked(6));
		playerCardImage7.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> cardClicked(7));
		playerCardImage8.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> cardClicked(8));
		playerCardImage9.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> cardClicked(9));
		
		currentPlayerCards.getChildren().addAll(playerCardImage0, playerCardImage1, playerCardImage2, playerCardImage3, playerCardImage4, playerCardImage5, playerCardImage6, playerCardImage7, playerCardImage8, playerCardImage9);
		updateCurrentPlayerCards();
		cardLevel.getChildren().addAll(leftArrow, currentPlayerCards, rightArrow);
		
		currentCardStatus.getChildren().addAll(currentCard, currentCardImage, currentPlayerLabel, currentPlayer);
		
		root.getChildren().addAll(playerInfo, currentCardStatus, cardLevel, chooseColor, drawCard);
		
		gameScene = new Scene(root, windowWidth, windowHeight);
		gameScene.getStylesheets().add("application.css");
		stage.setScene(gameScene);
		stage.show();
	}
	
	private void updateArrows() {
		if(currentCardDisplay > 0) {
			leftArrow.setImage(new Image("file:cardImages/leftArrow.png"));
		} else {
			leftArrow.setImage(null);
		}
		if(currentCardDisplay < ((game.getCurrentPlayer().getHand().size()-1) / 10) * 10) {
			rightArrow.setImage(new Image("file:cardImages/rightArrow.png"));
		} else {
			rightArrow.setImage(null);
		}
	}
	
	private void updateCurrentPlayerCards() {
		ArrayList<Card> currentPlayerHand = game.getCurrentPlayer().getHand();
		updateArrows();
		int counter = currentCardDisplay;
		
		for(Node image : currentPlayerCards.getChildren()) {
			if(currentPlayerHand.size() > counter) {
				Image cardImg = new Image("file:cardImages/"+ currentPlayerHand.get(counter++).getCardName() + ".jpg");
				((ImageView) image).setImage(cardImg);
			} else {
				((ImageView) image).setImage(null);
			}
		}
	}

	private void populatePlayerInfo() {
		for(int i = 0; i < playerAmount; i++) {
			Label playerName = new Label("Player name here");
			Label numOfCards = new Label("Number of cards here");
			
			int row = i+1;
			playerInfo.add(playerName, 0, row);
			playerInfo.add(numOfCards, 1, row);
			
			playerName.setText(game.getPlayers()[i].getName());
			numOfCards.setText(String.valueOf(game.getPlayers()[i].getHand().size()));
		}
	}
	
	private void updateGameScene() {
		currentCardDisplay = 0;
		chooseColor.getChildren().clear();
		for(int i = 1; i < playerAmount + 1; i++) {
			Label numOfCards = (Label) getComponent(i, 1, playerInfo);
			if(numOfCards != null) {
				numOfCards.setText(String.valueOf(game.getPlayers()[i-1].getHand().size()));
			}
		}
		currentCardImage.setImage(new Image("file:cardImages/"+game.getCurrentCard().getCardName()+".jpg"));
		currentPlayer.setText(game.getCurrentPlayer().getName());
	}
	
	private Node getComponent(int row, int column, GridPane table) {
		for(Node component : table.getChildren()) {
			if(GridPane.getRowIndex(component) == row && GridPane.getColumnIndex(component) == column) {
				return component;
			}
	}
		
		return null;
	}
	
	private void createPostGameScene(Player theWinner) {
		VBox layout = new VBox();
		
		Label winner = new Label();
		winner.setText("Winner: " + theWinner.getName());
		Button playAgain = new Button("Play Again!");
		
//		playAgain.setOnAction(e -> goToGameSetup());
		
		layout.getChildren().addAll(winner, playAgain);
		
		postGame = new Scene(layout, windowWidth, windowHeight);
		stage.setScene(postGame);
		stage.show();
	}
	
	private void cardClicked(int index) {
		if(game.playCard(currentCardDisplay + index)) {
			if(!wildCardClicked()) {
				for(Node card : currentPlayerCards.getChildren()) {
					((ImageView) card).setImage(null);
				}
				updateGameScene();
				nextPlayer();
			}
		}
		
		if(game.getWinner() != null) {
			createPostGameScene(game.getWinner());
		}
	}
	
	private void nextPlayer() {
		root.getChildren().remove(root.getChildren().size() -1);
		root.getChildren().add(nextPlayer);
		
		nextPlayer.setOnAction(e -> {
			game.setCurrentPlayer();
			updateGameScene();
			updateCurrentPlayerCards();
			root.getChildren().remove(root.getChildren().size() -1);
			root.getChildren().add(drawCard);
		});
	}
	
	private boolean wildCardClicked() {
		boolean isWild = game.getCurrentCard().getSuit() == null;
		if(isWild) {
			Button red = new Button("Red");
			Button yellow = new Button("Yellow");
			Button green = new Button("Green");
			Button blue = new Button("Blue");
			
			red.setOnAction(e -> setWildCardColor(CardSuit.RED));
			yellow.setOnAction(e -> setWildCardColor(CardSuit.YELLOW));
			green.setOnAction(e -> setWildCardColor(CardSuit.GREEN));
			blue.setOnAction(e -> setWildCardColor(CardSuit.BLUE));
			
			chooseColor.getChildren().addAll(red, yellow, green, blue);
		}
		return isWild;
	}
	
	private void setWildCardColor(CardSuit color) {
		game.setColor(color);
		for(Node card : currentPlayerCards.getChildren()) {
			((ImageView) card).setImage(null);
		}
		updateGameScene();
		nextPlayer();
	}
}
