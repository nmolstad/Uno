
package uno.controllers;

import java.util.ArrayList;
import java.util.Random;

import uno.enums.*;
import uno.models.*;

public class GameController {
	
	private Player[] players;
	private DrawDeck drawPile;
	private DiscardDeck discardPile;
	private ArrayList<Card> lastSeveralCards = new ArrayList<>();
	private ArrayList<Integer> lastSeveralCardRotations = new ArrayList<>();
	private ArrayList<String> feedMessages = new ArrayList<>();
	private ArrayList<String> feedEffects = new ArrayList<>();
	private boolean isClockwise;
	private boolean multiDraw;
	private boolean stackDrawCards;
	private int turnCount;
	private Player currentPlayer;
	private Player winner;
	private Card currentCard;
	private int stackAmount;
	private boolean stackInProgress;
	
	public void initializeGame(String[] playerNames, boolean multiDraw, boolean stackDrawCards) {
		//Set all of the variables to the default values
		isClockwise = true;
		turnCount = 0;
		stackAmount = 0;
		stackInProgress = false;
		winner = null;
		
		//Create a new instance of DrawDeck and shuffle the deck
		drawPile = new DrawDeck();
		drawPile.shuffleDeck();
		
		//Create a new instance of DiscardDeck
		discardPile = new DiscardDeck();
		
		//Instantiate players with the number of players the user wishes to play with
		players = new Player[playerNames.length];
		
		//Instantiate each player in players
		for(int i = 0; i < players.length; i++) {
			players[i] = new Player(playerNames[i]);
		}
		
		this.multiDraw = multiDraw;
		this.stackDrawCards = stackDrawCards;
		
		//Deal the cards to every player in the game
		dealCards();
		
		resetInfoTrackers();
		
		//Set the current card and player
		//Add the last card in the draw pile to the discard pile
		currentCard = discardPile.insertCard(drawPile.removeCard());
		currentPlayer = players[0];
		
		setInfoTrackers();
		feedMessages.remove(4);
		feedMessages.add(null);
		
		//If the first card is a skip, skip the first player.
		if(currentCard.getType() == CardType.SKIP) {
			skipTurn();
		}
		//If the first card is a draw two, make the first player draw two cards and skip their turn.
		else if(currentCard.getType() == CardType.DRAW_TWO) {
			drawCard(2);
			skipTurn();
		}
		//If the first card is a reverse, set isClockwise to false if true or vice-versa
		else if(currentCard.getType() == CardType.REVERSE) {
			isClockwise = !isClockwise;
		}
		//If the first card is a wild draw four, make the first player draw four cards and skip their turn.
		else if(currentCard.getType() == CardType.WILD_DRAW_FOUR) {
			drawCard(4);
			skipTurn();
		}
	}
	
	private void dealCards() {
		//Loop through each player seven times, adding a card from the drawPile to the player's hand.
		for(int i = 0; i < 7; i++) {
			for(Player player : players) {
				player.drawCard(drawPile.removeCard());
			}
		}
		
		setPlayersNumberOfCards();
	}
	
	public boolean playCard(int cardIndex) {
		if(stackInProgress) {
			if(currentPlayer.getHand().get(cardIndex).getType() == CardType.DRAW_TWO) {
				currentCard = discardPile.insertCard(currentPlayer.playCard(cardIndex));
				
				checkForWinner();
				
				feedEffects.remove(0);
				setInfoTrackers();
				performAction();
				
				if(feedEffects.size() < 5) {
					feedEffects.add(null);
				}
				
				setPlayersNumberOfCards();
				return true;
			} else {
				return false;
			}
		} else {
			if(checkMatch(currentPlayer.getHand().get(cardIndex))) {
				boolean isActionCard;
				currentCard = discardPile.insertCard(currentPlayer.playCard(cardIndex));
				
				checkForWinner();
				
				feedEffects.remove(0);
				setInfoTrackers();
				isActionCard = currentCard.getType() == CardType.DRAW_TWO || currentCard.getType() == CardType.SKIP || currentCard.getType() == CardType.REVERSE || currentCard.getType() == CardType.WILD_DRAW_FOUR;
				if(isActionCard) {
					performAction();
				}
				
				if(feedEffects.size() < 5) {
					feedEffects.add(null);
				}
				
				setPlayersNumberOfCards();
				return true;
			} else {
				return false;
			}
		}
	}
	
	public void setCurrentPlayer() {
		if(isClockwise) {
			currentPlayer = players[Math.abs(++turnCount) % players.length];
		} else {
			currentPlayer = players[Math.abs(--turnCount) % players.length];
		}
	}
	
	private boolean checkMatch(Card card) {
		boolean isMatch = false;
		boolean isSuitMatch = card.getSuit() == currentCard.getSuit();
		boolean isTypeMatch = card.getType() == currentCard.getType();
		boolean isWild = card.getType() == CardType.WILD || card.getType() == CardType.WILD_DRAW_FOUR;
		
		//Check if the card that the player is trying to play matches the current card, either by suit or type (including wild cards)
		isMatch = isSuitMatch || isTypeMatch || isWild;
		
		//Return the result
		return isMatch;
	}
	
	private void replenishDrawPile() {
		//Put the contents of discardPile in drawPile and clear discardPile.
		drawPile.setCards(discardPile.getCards());
		discardPile = new DiscardDeck();
		
		setInfoTrackers();
		
		//Add the last card from drawPile to discardPile
		discardPile.insertCard(drawPile.removeCard());
		
		setInfoTrackers();
		
		//Shuffle drawPile
		drawPile.shuffleDeck();
		
		resetWildCards();
	}
	
	private void resetWildCards() {
		//Set the suit of any wild cards back to null
		for(Card card : drawPile.getCards()) {
			if(card.getType() == CardType.WILD || card.getType() == CardType.WILD_DRAW_FOUR) {
				card.setSuit(null);
			}
		}
	}
	
	private void performAction() {
		//If the card is a skip, skip the next players turn
		if(currentCard.getType() == CardType.SKIP) {
			skipTurn();
			feedEffects.add("skipping " + currentPlayer.getName() + "'s turn");
		}
		//If the card is a draw two, skip the next players turn and make them draw two cards.
		else if(currentCard.getType() == CardType.DRAW_TWO) {
			if(stackDrawCards) {
				stackAmount += 2;
				if(checkForDrawCard()) {
					stackInProgress = true;
					feedEffects.add("increasing the stack to " + stackAmount + " cards");
				} else {
					stackInProgress = false;
					skipTurn();
					drawCard(stackAmount);
					feedEffects.add("making " + currentPlayer.getName() + " draw " + stackAmount + " cards");
					stackAmount = 0;
				}
			} else {
				skipTurn();
				drawCard(2);
				feedEffects.add("making " + currentPlayer.getName() + " draw 2 cards");
			}
		}
		//If the card is a reverse, set isClockwise to false if true or vice-versa
		else if(currentCard.getType() == CardType.REVERSE) {
			isClockwise = !isClockwise;
			feedEffects.add("reversing the turn rotation");
		}
		//If the card is a wild draw four, set the color, skip the next players turn and make them draw four cards.
		else if(currentCard.getType() == CardType.WILD_DRAW_FOUR) {
			skipTurn();
			drawCard(4);
			feedEffects.add("making " + currentPlayer.getName() + " draw 4 cards");
		}
	}
	
	private boolean checkForDrawCard() {
		boolean hasDrawCard = false;
		
		setCurrentPlayer();
		
		for(int i = 0; i < currentPlayer.getHand().size() && !hasDrawCard; i++) {
			hasDrawCard = currentPlayer.getHand().get(i).getType() == CardType.DRAW_TWO;
		}
		
		if(isClockwise) {
			currentPlayer = players[Math.abs(--turnCount) % players.length];
		} else {
			currentPlayer = players[Math.abs(++turnCount) % players.length];
		}
		
		return hasDrawCard;
	}
	
	private void skipTurn() {
		//Add 1 to the turn rotation if isClockwise is true or subtract 1 if isClockwise is false
		turnCount = isClockwise ? turnCount + 1 : turnCount - 1;
		
		//Set the current player, in case any further action needs to take place on this player
		currentPlayer = players[Math.abs(turnCount) % players.length];
	}
	
	public void drawCard(int amount) {
		for(int i = 0; i < amount; i++) {
			currentPlayer.drawCard(drawPile.removeCard());
			
			if(drawPile.getCards().size() < 1) {
				replenishDrawPile();
			}
			
			setPlayersNumberOfCards();
		}
	}
	
	public void setColor(CardSuit color) {
		//Set the suit of the wild card
		currentCard.setSuit(color);
	}
	
	public boolean checkForMatch() {
		boolean isMatch = false;
		
		//Iterate through the current players hand, and see if any cards match the current card
		for(int i = 0; i < currentPlayer.getHand().size() && !isMatch; i++) {
			isMatch = checkMatch(currentPlayer.getHand().get(i));
		}
		
		return isMatch;
	}
	
	private boolean checkForWinner() {
		boolean isWinner = false;
		
		//Check if the current player has any cards left in their hand
		if(currentPlayer.getHand().size() < 1) {
			winner = currentPlayer;
			isWinner = true;
		}
		
		return isWinner;
	}
	
	public Player[] getPlayers() {
		return players;
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public Card getCurrentCard() {
		return currentCard;
	}
	
	public Player getWinner() {
		return winner;
	}
	
	private void setPlayersNumberOfCards() {
		for(Player player : players) {
			player.setNumberOfCards();
		}
	}
	
	public ArrayList<Card> getLastSeveralCards() {
		return lastSeveralCards;
	}
	
	public ArrayList<Integer> getLastSeveralCardRotations() {
		return lastSeveralCardRotations;
	}
	
	public ArrayList<String> getFeedMessages() {
		return feedMessages;
	}
	
	public ArrayList<String> getFeedEffects() {
		return feedEffects;
	}
	
	private void setInfoTrackers() {
		lastSeveralCards.remove(0);
		lastSeveralCards.add(currentCard);
		lastSeveralCardRotations.remove(0);
		if(lastSeveralCards.get(3) == null) {
			lastSeveralCardRotations.add(0);
		} else {
			lastSeveralCardRotations.add(new Random().nextInt(359));
		}
		feedMessages.remove(0);
		feedMessages.add(currentPlayer.getName() + " played");
	}
	
	public boolean getTurnRotation() {
		return isClockwise;
	}
	
	private void resetInfoTrackers() {
		for(int i = 0; i < 5; i++) {
			lastSeveralCards.add(null);
			lastSeveralCardRotations.add(null);
			feedMessages.add(null);
			feedEffects.add(null);
		}
	}
	
	public boolean getMultiDrawSetting() {
		return multiDraw;
	}
}
