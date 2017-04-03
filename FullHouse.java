
/**
 * FullHouse models a hand of full house in Big Two.
 * FullHouse is subclass of Hand.
 * @author Gao Yuan
 *
 */
public class FullHouse extends Hand{
	/**
	 * Constructor of FullHouse.
	 * It calls constructor of Hand.
	 * @param player
	 * @param cards
	 */
	public FullHouse(CardGamePlayer player, CardList cards){
		super(player, cards);
	}
	/** 
	 * This method retrieves the top card of a FullHouse.
	 * It can only be use after checked with isValid().
	 * @see Hand#getTopCard()
	 * @return Card.
	 */
	public Card getTopCard(){
		this.sort();
		int counterOfDuplicate = 1;
		int duplicateIndex[] = new int[3];
		duplicateIndex[0] = 0;
		int antiDup[] = new int[3];
		int counterOfDiff = 0;
		for(int i = 1; i < 5; ++i){
			if(this.getCard(i).getRank() == this.getCard(0).getRank()){
				++counterOfDuplicate;
				duplicateIndex[counterOfDuplicate - 1] = i;
			}
			else{
				antiDup[counterOfDiff++] = i;
			}
		}
		if(counterOfDuplicate == 3){
			int largestIndexOfDup = 0;
			for(int i = 1; i < 3; ++i){
				if(this.getCard(duplicateIndex[i]).getSuit() > this.getCard(largestIndexOfDup).getSuit()){
					largestIndexOfDup = i;
				}
			}
			return this.getCard(largestIndexOfDup);
		}
		else{
			int largestIndex = antiDup[0];
			for(int i = 0; i < 3; ++i){
				if(this.getCard(antiDup[i]).getSuit() > this.getCard(largestIndex).getSuit()){
					largestIndex = antiDup[i];
				}
			}
			return this.getCard(largestIndex);
		}
	}
	/** 
	 * This method checks if this Flush beats the hand given.
	 * It checks for all Hand.
	 * @see Hand#beats(Hand)
	 * @return boolean.
	 */
	public boolean beats(Hand hand){
		this.sort();
		hand.sort();
		if(hand.size() != 5){
			return false;
		}
		else if(hand.getType() == "Quad" || hand.getType() == "StraightFlush"){
			return false;
		}
		else if(hand.getType() == "Flush" || hand.getType() == "Straight"){
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
		}
	}
	/** 
	 * This method checks if a given hand is a valid FullHouse.
	 * @return boolean.
	 */
	public boolean isValid(){
		this.sort();
		if(this.size() != 5){
			return false;
		}
		else{
			int[] thisRank = new int[2];
			thisRank[0] = this.getCard(0).getRank();
			thisRank[1] = 5;
			int same1 = 1;
			int same2 = 0;
			for(int i = 1; i < 5; ++i){
				if(this.getCard(i).getRank() != thisRank[0]){
					if(this.getCard(i).getRank() != thisRank[1]){
						if(thisRank[1] != 5){
							return false;
						}
						else{
							thisRank[1] = this.getCard(i).getRank();
							same2 = 1;
						}
					}
					else{
						++same2;
					}
				}
				else{
					++same1;
				}
			}
			if(same1 == 2 && same2 == 3){
				return true;
			}
			else if(same1 == 3 && same2 == 2){
				return true;
			}
			else{
				return false;
			}
		}
	}
	/** 
	 * This method retrieves the type of a hand of FullHouse.
	 * @return String.
	 */
	public String getType(){
		String type = "FullHouse";
		return type;
	}
}
