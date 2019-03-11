package uno.models;

import java.util.Collections;
import java.util.Random;

import uno.enums.CardSuit;
import uno.enums.CardType;

public class DrawDeck extends Deck {
	
	public DrawDeck() {
		super();
		populateDeck();
	}
	
	private void populateDeck() {
		CardType[] cardTypes = CardType.values();
		
		//Clear the deck of any pre-existing cards
		cards.clear();
		
		//Iterate through every suit
		for(CardSuit suit : CardSuit.values()) {
			//Add a new card with the current suit and type zero to the deck.
			cards.add(new Card(suit, CardType.ZERO));
			//Iterate through all CardTypes besides ZERO, WILD, and WILD_DRAW_FOUR. Add a new card with the current suit and type to the deck twice.
			for(int i = 1; i < cardTypes.length - 2; i++) {
				cards.add(new Card(suit, cardTypes[i]));
				cards.add(new Card(suit, cardTypes[i]));
			}
		}
		//Add four WILD and WILD_DRAW_FOUR cards to the deck.
		for(int i = 0; i < 4; i++) {
			cards.add(new Card(null, CardType.WILD));
			cards.add(new Card(null, CardType.WILD_DRAW_FOUR));
		}
	}
	
	public void shuffleDeck() {
		Collections.shuffle(cards);
	}
	
	public Card removeCard() {
		Card topCard = cards.get(cards.size() - 1);
		
		//Remove the card from the last index of the list
		cards.remove(cards.size() - 1);
		
		return topCard;
	}
}
