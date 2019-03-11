package uno.models;

import java.util.ArrayList;

public class Player {
	private String name;
	private ArrayList<Card> hand = new ArrayList<>();
	private int numberOfCards;
	
	public Player(String name) {
		this.name = name;
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
	
	public int getNumberOfCards() {
		return numberOfCards;
	}
	
	public void setNumberOfCards() {
		numberOfCards = hand.size();
	}
	
	public void drawCard(Card card) {
		//Add card to hand
		hand.add(card);
	}
	
	public Card playCard(int index) {
		Card playedCard = hand.get(index);
		
		//Remove the Card from hand
		hand.remove(index);
		
		//Return that Card
		return playedCard;
	}
}
