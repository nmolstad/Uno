package models;

import java.util.ArrayList;

public class Player {
	private String name;
	private ArrayList<Card> hand;
	
	public Player(String name) {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}
	
	public void drawCard(Card card) {
		//Add card to hand
		hand.add(card);
	}
	
	public Card playCard() {
		//Remove the Card from hand
		
		
		//Return that Card
		return null;
	}
}
