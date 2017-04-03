
/**
 * StraightFlush models a hand of straight flush in Big Two.
 * This is a subclass of Hand.
 * Note that actually StraightFlush is a special case of Straight and Flush.
 * @author Gao Yuan
 *
 */
public class StraightFlush extends Hand{
	/**
	 * Constructor of StraightFlush.
	 * It calls the constructor of Hand.
	 * @param player
	 * @param cards
	 */
	public StraightFlush(CardGamePlayer player, CardList cards){
		super(player, cards);
	}
	/** 
	 * This method retrieves the top card of this StraighFlush.
	 * @see Hand#getTopCard()
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
	 * This method checks if this StraightFlush beats the given hand.
	 * It checks for all hands.
	 * @return boolean.
	 */
	public boolean beats(Hand hand){
		if(hand.size() != 5){
			return false;
		}
		else if(hand.getType() != "StraightFlush"){
			return true;
		}
		else{
			int i = this.getTopCard().compareTo(hand.getTopCard());
			if(i == 1){
				return true;
			}
			else{
				return false;
			}
		}//The given hand is a StraightFlush.
	}
	/** 
	 * This method check if this hand is StraightFlush.
	 * @return boolean.
	 */
	public boolean isValid(){
		if(this.size() != 5){
			return false;
		}
		else{
			int thisSuit = this.getCard(0).getSuit(); //Cannot use getTopCard. Need to avoid overflow.
			for(int i = 0; i < 5; ++i){
				if(this.getCard(i).getSuit() != thisSuit){
					return false;
				}
			}
			//Codes for Straight Checking.
			int[] rankStore = new int[5];
			for(int i = 0; i < 5; ++i){
				if(this.getCard(i).getRank() == 0 || this.getCard(i).getRank() == 1){
					rankStore[i] = this.getCard(i).getRank() + 13;
				}
				else{
					rankStore[i] = this.getCard(i).getRank();
				}
			}
			//The following codes sorts all the ranks.
			int smallestValueLeft = 0;
			int indexOfSmallestLeft = 0;
			for(int i = 0; i < 4; ++i){
				indexOfSmallestLeft = i;
				smallestValueLeft = rankStore[indexOfSmallestLeft];
				for(int j = i + 1; j < 5; ++j){
					if(rankStore[j] < smallestValueLeft){
						smallestValueLeft = rankStore[j];
						indexOfSmallestLeft = j;
					}
				}
				int temp = rankStore[i];
				rankStore[i] = smallestValueLeft;
				rankStore[indexOfSmallestLeft] = temp;
			}
			for(int i = 0; i < 4; ++i){
				if(rankStore[i + 1] - rankStore[i] != 1){
					return false;
				}
			}
			return true;
		}//Has five cards.
	}
	/** 
	 * This method retrieves the type of a hand of StraightFlush.
	 * @return String.
	 */
	public String getType(){
		String type = "StraightFlush";
		return type;
	}
}

