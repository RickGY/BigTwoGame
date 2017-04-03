
/**
 * Triple class models a hand of triple in Big Two.
 * Triple is a subclass of Hand.
 * @author Gao Yuan
 *
 */
public class Triple extends Hand{
	/**
	 * Constructor of Triple class.
	 * It directly calls the super class constructor.
	 * @param player
	 * @param cards
	 */
	public Triple(CardGamePlayer player, CardList cards){
		super(player, cards);
	}
	/** 
	 * It is used to retrieve the top card of this triple.
	 * @see Hand#getTopCard()
	 * @return Card. Returns the card in this hand with the highest suit.
	 */
	public Card getTopCard(){
		int suitIndex = 0;
		for(int i = 1; i < this.size(); ++i){
			if(this.getCard(i).getSuit() > this.getCard(suitIndex).getSuit()){
				suitIndex = i;
			}
		}
		return this.getCard(suitIndex);
	}
	/**
	 * Check if this Triple beats the specific Hand given.
	 * It checks for all Hands given.
	 * @param hand
	 * @return boolean. 
	 */
	public boolean beats(Hand hand){
		if(hand.size() == 3){
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
	 * This method check if this hand is a valid Triple in BigTwo.
	 * @see Hand#isValid()
	 * @return boolean. True if all the cards in this hand are of same rank.
	 */
	public boolean isValid(){
		if(this.size() == 3){
			if(this.getCard(0).getRank() == this.getCard(1).getRank() && this.getCard(0).getRank() == this.getCard(2).getRank()){
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
	 * @return
	 */
	public String getType(){
		String type = "Triple";
		return type;
	}
}
