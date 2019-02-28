package models;

import java.util.ArrayList;

import enums.*;

public class Deck {
	protected ArrayList<Card> cards = new ArrayList<>();
	
	public ArrayList<Card> getCards() {
		return cards;
	}
	
	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}
}
