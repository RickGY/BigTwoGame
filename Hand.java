
/**
 * Hand is subclass of CardList.
 * This class is a general implementation of all possible hands.
 * @author Gao Yuan
 *
 */
abstract public class Hand extends CardList{
	private CardGamePlayer player;
	/**
	 * Constructor of the Hand class.
	 * Initialize player and cards with the arguments provided.
	 * @param player
	 * @param cards
	 */
	public Hand(CardGamePlayer player, CardList cards){
		this.player = player;
		removeAllCards();
		for(int i = 0; i < cards.size(); ++i){
			this.addCard(cards.getCard(i));
		}
	} 
	/**
	 * Getter of instance variable player.
	 * @return
	 */
	public CardGamePlayer getPlayer(){
		return this.player;
	}
	/**
	 * This method is a general method retrieving the top card of a Hand;
	 * It returns the Card that is the largest according to the compareTo() function.
	 * @return Card. 
	 */
	public Card getTopCard(){
		int largestIndex = 0;
		for(int i = 1; i < this.size(); ++i){
			if(this.getCard(i).compareTo(this.getCard(largestIndex)) == 1){
				largestIndex = i;
			}
		}
		return this.getCard(largestIndex);
	}
	/**
	 * This method checks the hand beats the specific hand provided. 
	 * It compares two hands according to their top cards' order.
	 * @param hand
	 * @return boolean. True if this Hand beats the Hand provided.
	 */
	public boolean beats(Hand hand){
		if(this.getTopCard().compareTo(hand.getTopCard()) == 1){
			return true;
		}
		else{
			return false;
		}
	}
	/**
	 * isValid check if a Hand is valid or not. It will be check against specific 
	 * cases, thus no useful implementation is put here.
	 * @return boolean
	 */
	public abstract boolean isValid();
	/**
	 * getType() returns a string specifying the type of this hand.
	 * Requires specific information, thus dummy here.
	 * @return String 
	 */
	public abstract String getType();
}





