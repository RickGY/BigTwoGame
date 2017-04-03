
/**
 * BigTwoDeck is the specific implemenation and subclass of Deck.
 * It contains BigTwoCard, a more specific version of Card.
 * @author Gao Yuan
 *
 */
public class BigTwoDeck extends Deck{
	/** 
	 * This function remove all cards from the deck 
	 * and create a new deck of cards.
	 * Unlike the super class method, this method will
	 * create BigTwoCard instead of general Card.
	 * @see Deck#initialize()
	 */
	public void initialize(){
		removeAllCards();
		for(int i = 0; i < 4; ++i){
			for(int j = 0; j < 13; ++j){
				Card card = new BigTwoCard(i, j);
				addCard(card);
			}
		}
	}
}
