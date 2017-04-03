
/**
 * Quad models a hand of quad in Big Two.
 * This is a subclass of Hand.
 * @author Gao Yuan
 *
 */
public class Quad extends Hand{
	/**
	 * Constructor of Quad class.
	 * It calls constructor of Hand.
	 * @param player
	 * @param cards
	 */
	public Quad(CardGamePlayer player, CardList cards){
		super(player, cards);
	}
	/**
	 * This method retrieves the top card of a Quad.
	 * Can only be used after checking with isValid()
	 * @see Hand#getTopCard()
	 * @return Card.
	 */
	public Card getTopCard(){
		this.sort();
		int thisRank;
		if(this.getCard(0).getRank() == getCard(1).getRank()){
			thisRank = this.getCard(0).getRank();
		}
		else{
			thisRank = this.getCard(2).getRank();
		}
		int largestIndex = 0;
		if(this.getCard(largestIndex).getRank() != thisRank){
			largestIndex = 1;
		}
		for(int i = 1; i < 5; ++i){
			if(this.getCard(i).getSuit() > this.getCard(largestIndex).getSuit() && this.getCard(i).getRank() == thisRank){
				largestIndex = i;
			}
		}
		return this.getCard(largestIndex);
	}
	/** 
	 * This method checks if this Quad beats a given Hand.
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
		else{
			if(hand.getType() == "StraightFlush"){
				return false;
			}
			else if(hand.getType() == "Quad"){
				if(this.getTopCard().getRank() > hand.getTopCard().getRank()){
					return true;
				}
				else{
					return false;
				}
			}
			else{
				return true;
			}
		}//Has 5 cards.
	}
	/** 
	 * This method checks if this hand is a valid Quad in Big Two.
	 * @return boolean.
	 */
	public boolean isValid(){
		this.sort();
		if(this.size() != 5){
			return false;
		}
		else{
			int counterOfDiff = 0;
			for(int i = 1; i < 5; ++i){
				if(this.getCard(i).getRank() != this.getCard(0).getRank()){
					++counterOfDiff;
				}
			}
			if(counterOfDiff == 1){
				return true;
			}
			else if(counterOfDiff == 4){
				if(this.getCard(1).getRank() == this.getCard(2).getRank() && this.getCard(2).getRank() == this.getCard(3).getRank() && this.getCard(3).getRank() == this.getCard(4).getRank()){
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
	}
	/**
	 * This method retrieves the type of a hand of Quad.
	 * @return String.
	 */
	public String getType(){
		String type = "Quad";
		return type;
	}
}
