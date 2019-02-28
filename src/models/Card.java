package models;

import enums.CardSuit;
import enums.CardType;

public class Card {
	private CardSuit suit;
	private final CardType type;
	
	public Card(CardSuit suit, CardType type) {
		this.suit = suit;
		this.type = type;
	}
	
	public CardSuit getSuit() {
		return suit;
	}
	
	public void setSuit(CardSuit suit) {
		this.suit = suit;
	}
	
	public CardType getType() {
		return type;
	}
}
