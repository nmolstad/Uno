package models;

import java.util.ArrayList;

public class Deck {
	private ArrayList<Card> cards;
	
	public Deck() {
		cards = new ArrayList<>();
	}
	
	public ArrayList<Card> getCards() {
		return cards;
	}
	
	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}
	
	private void populateDeck() {
		//Iterate through every CardSuit
			//Iterate through every CardType, creating an instance of Card with that CardSuit and CardType. Store it in cards
			//Iterate through the CardTypes that appear in the deck twice, creating an instance of Card with that CardSuit and CardType. Store it in cards. 
	}
}
