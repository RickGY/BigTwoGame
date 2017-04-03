
/**
 * Single class model a hand of single in Big Two.
 * Single is a subclass of Hand.
 * @author Gao Yuan
 *
 */
public class Single extends Hand{
	/**
	 * Constructor of Single.
	 * It directly calls the constructor of its super class Hand.
	 * @param player
	 * @param cards
	 */
	public Single(CardGamePlayer player, CardList cards){
		super(player, cards);
	}
	/**
	 * geType returns "Single", its type.
	 * @return String
	 */
	public String getType(){
		String type = "Single";
		return type;
	}
	/**
	 * This method returns the top card of this hand.
	 * The only card in this hand Single is the top card.
	 * @return Card
	 */
	public Card getTopCard(){
		return this.getCard(0);
	}
	/** 
	 * This method checks if this single beats the specific hand given.
	 * It checks for all Hands.
	 * @see Hand#beats(Hand)
	 * @return Boolean. 
	 */
	public boolean beats(Hand hand){
		if(hand.size() == 1){
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
	 * This method check if this hand is a Single or not;
	 * @return boolean. True if the size of this hand is one.
	 */
	public boolean isValid(){
		if(this.size() == 1){
			return true;
		}
		else{
			return false;
		}
	}
}







