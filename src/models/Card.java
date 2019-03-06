package models;

import enums.CardSuit;
import enums.CardType;

public class Card {
	private CardSuit suit;
	private final CardType type;
	private String cardName;
	
	public Card(CardSuit suit, CardType type) {
		this.suit = suit;
		this.type = type;
		cardName = suit + "_" + type;
	}
	
	public CardSuit getSuit() {
		return suit;
	}
	
	public void setSuit(CardSuit suit) {
		this.suit = suit;
		cardName = suit + "_" + type;
	}
	
	public CardType getType() {
		return type;
	}
	
	public String getCardName() {
		return cardName;
	}
}
