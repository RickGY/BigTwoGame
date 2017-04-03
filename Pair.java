
/**
 * Pair class models a hand of pair in Big Two.
 * Pair is subclass of Hand.
 * @author Gao Yuan
 *
 */
public class Pair extends Hand{
	/**
	 * Constructor of Pair.
	 * It directly calls super class constructor.
	 * @param player
	 * @param cards
	 */
	public Pair(CardGamePlayer player, CardList cards){
		super(player, cards);
	}
	/** 
	 * This method returns the top card of this hand.
	 * The card in this pair with a higher suit is the top card.
	 * This method cannot be used before checking with isValid.
	 * @see Hand#getTopCard()
	 * @return Card.
	 */
	public Card getTopCard(){
		if(this.getCard(0).getSuit() > this.getCard(1).getSuit()){
			return this.getCard(0);
		}
		else{
			return this.getCard(1);
		}
	}
	/** 
	 * This method check if this pair beats the hand given.
	 * It checks for all Hands.
	 * @see Hand#beats(Hand)
	 * @return boolean. 
	 */
	public boolean beats(Hand hand){
		if(hand.size() == 2){
			int i = this.getTopCard().compareTo(hand.getTopCard());
			if(i == 1){
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	}
	/** 
	 * This method check if this hand is a valid pair.
	 * @return boolean. True if there are only two cards of same rank in this hand.
	 */
	public boolean isValid(){
		if(this.size() == 2){
			if(this.getCard(0).getRank() == this.getCard(1).getRank()){
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	}
	/** 
	 * Retrieve the type of an object of this class.
	 * @return String.
	 */
	public String getType(){
		String type = "Pair";
		return type;
	}	
}
