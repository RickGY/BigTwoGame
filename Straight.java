
/**
 * Straight class models hand of straight in Big Two.
 * Straight is a subclass of Hand.
 * Note thta StraightFlush is a special case of Straight and Flush.
 * @author Gao Yuan
 *
 */
public class Straight extends Hand{
	/**
	 * Constructor of Straight class.
	 * It directly calls the super class constructor.
	 * @param player
	 * @param cards
	 */
	public Straight(CardGamePlayer player, CardList cards){
		super(player, cards);
	}
	/** 
	 * It is used to retrieve the top card of Straight.
	 * It retrieves the card with the highest rank.
	 * @see Hand#getTopCard()
	 * @return Card
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
	 * Check if this Straight beats a specific hand given.
	 * It checks for Hands given.
	 * @param hand
	 * @return boolean
	 */
	public boolean beats(Hand hand){
		if(hand.getType() == "Straight"){
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
	 * This method check if this is a valid Straight in Big Two.
	 * @see Hand#isValid()
	 */
	public boolean isValid(){
		if(this.size() == 5){
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
		}
		else{
			return false;
		}
	}
	/**  
	 * This method retrieves the type of this hand.
	 * @return String.
	 */
	public String getType(){
		String type = "Straight";
		return type;
	}
}
