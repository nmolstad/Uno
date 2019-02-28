package models;

public class DiscardDeck extends Deck {

	public DiscardDeck() {
		super();
	}
	
	public Card insertCard(Card card) {
		//Add a card to the last index of the Array
		cards.add(card);
		
		//Return the card that was added
		return card;
	}
}
