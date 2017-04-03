
/**
 * Flush class models a hand of flush in Big Two.
 * Flush is a subclass of Hand.
 * Note that StraightFlush is a special case of Straight  and Flush.
 * @author Gao Yuan
 *
 */
public class Flush extends Hand{
	/**
	 * Constructor of Flush class.
	 * It directly calls super class's constructor.
	 * @param player
	 * @param hand
	 */
	public Flush(CardGamePlayer player, CardList hand){
		super(player, hand);
	}
	/** 
	 * This method retrieves the top card of a hand of flush.
	 * It retrieves the card of highest Rank.
	 * @see Hand#getTopCard()
	 */
	public Card getTopCard(){
		int largestIndex = 0;
		for(int i = 1; i < 5; ++i){
			if(this.getCard(i).getRank() > this.getCard(largestIndex).getRank()){
				largestIndex = i;
			}
		}
		return this.getCard(largestIndex);
	}
	/**
	 * This method check if this Straight beats the hand given.
	 * It checks for all Hand.
	 * @see Hand#beats(Hand)
	 * @return boolean.
	 */
	public boolean beats(Hand hand){
		if(hand.size() != 5){
			return false;
		}
		else if(hand.getType() == "Straight"){
			return true;
		}
		else if(hand.getType() == "Flush"){
			if(this.getTopCard().getSuit() > hand.getTopCard().getSuit()){
				return true;
			}//This hand has higher suit.
			else if(this.getTopCard().getSuit() == hand.getTopCard().getSuit()){
				if(this.getTopCard().getRank() > hand.getTopCard().getRank()){
					return true;
				}//This hand has same suit, higher rank.
				else{
					return false;
				}// This hand has same suit, lower rank.
			}
			else{
				return false;
			}//This hand has a lower suit.
		}
		else{
			return false;
		}// hand given is Full House or Quad or Straight Flush.
	}
	/**
	 * This method checks this hand is a Flush in Big Two.
	 * @return boolean.
	 */
	public boolean isValid(){
		if(this.size() != 5){
			return false;
		}
		else{
			int thisSuit = this.getCard(0).getRank(); //Cannot use getTopCard. Need to avoid overflow.
			for(int i = 0; i < 5; ++i){
				if(this.getCard(i).getSuit() != thisSuit){
					return false;
				}
			}
			return true;
		}//Has five cards.
	}
	/** 
	 * This method retrieves the type of this hand of Flush.
	 * @return String.
	 */
	public String getType(){
		String type = "Flush";
		return type;
	}
}

