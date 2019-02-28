package controllers;

import java.util.ArrayList;

import enums.CardType;
import models.*;

public class GameController {

	private static Player[] players;
	private static DrawDeck drawPile;
	private static DiscardDeck discardPile;
	private static boolean isClockwise;
	private static int turnCount;
	private static Player currentPlayer;
	private static Player winner;
	private static Card currentCard;
	private static int drawTwoStackAmount;
	
	public static void run() {
		initializeGame();
		
		//While there is not a winner, have the currentPlayer take a turn
		
	}
	
	private static void initializeGame() {
		int playerAmount;
		
		//Set all of the variables to the default values
		isClockwise = true;
		turnCount = 0;
		
		//Create a new instance of DrawDeck and shuffle the deck
		drawPile = new DrawDeck();
		drawPile.shuffleDeck();
		
		//Create a new instance of DiscardDeck
		discardPile = new DiscardDeck();
		
		//Instantiate players with the number of players the user wishes to play with
		playerAmount = 1;
		players = new Player[playerAmount];
		
		//Instantiate each player in players
		for(int i = 0; i < players.length; i++) {
			String name = "";
			players[i] = new Player(name);
		}
		
		//Deal the cards to every player in the game
		dealCards();
		
		//Set the current card and player
		//Add the last card in the draw pile to the discard pile
		currentCard = discardPile.insertCard(drawPile.removeCard());
		currentPlayer = players[0];
		
	}
	
	private static void dealCards() {
		//Loop through each player seven times, adding a card from the drawPile to the player's hand.
		for(int i = 0; i < 7; i++) {
			for(Player player : players) {
				player.drawCard(drawPile.removeCard());
			}
		}
	}
	
	private static void takeTurn() {
		//Display the player's hand
		
		//Loop through the player's hand and check if any cards match the current card
		
		//If at least one card matches, allow the player to play a matching card.
		
		
		//Otherwise, draw a card from the drawPile
		
	}
	
	private static void displayHand() {
		//For every card in the current players hand, display it to the user
	}
	
	private static boolean checkMatch(Card card) {
		boolean isMatch = false;
		boolean isSuitMatch = card.getSuit() == currentCard.getSuit();
		boolean isTypeMatch = card.getType() == currentCard.getType();
		boolean isWild = card.getType() == CardType.WILD || card.getType() == CardType.WILD_DRAW_FOUR;
		
		//Check if the card that the player is trying to play matches the current card, either by suit or type (including wild cards)
		isMatch = isSuitMatch || isTypeMatch || isWild;
		
		//Return the result
		return isMatch;
	}
	
	private static void replenishDrawPile() {
		//Swap the contents of drawPile and discardPile, then add the last card from drawPile to discardPile
		
		//Shuffle drawPile
	}
	
	private static void performAction() {
		//If the card is a skip, call skipTurn()
		
		//If the card is a draw two, call drawTwo and add drawTwoStackAmount to the next player, then call skipTurn.
		
		//If the card is a reverse, set isClockwise to false if true or vice-versa
		isClockwise = !isClockwise;
		
		//If the card is a wild, call setColor
		
		//If the card is a wild draw four, call drawTwo twice and setColor, then add drawTwoStackAmount to the next player and call skipTurn.
		
		
	}
	
	private static void skipTurn() {
		//Add 1 to the turn rotation if isClockwise is true or subtract 1 if isClockwise is false
	}
	
	private static void drawTwo() {
		//Add two cards to drawTwoStackAmount
	}
	
	private static void setColor() {
		//Prompt the player to set the suit of the wild card and set it to their choice
	}
	
	private static Player checkForWinner() {
		return null;
	}
}
