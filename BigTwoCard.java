
/**
 * BigTwoCard is the specific implementation and subclass of Card class.
 * The behaviors of this class's object follows the rules of Big Two.
 * @author Gao Yuan
 *
 */
public class BigTwoCard extends Card{
	public BigTwoCard(int suit, int rank){
		super(suit, rank);
	}
	/** 
	 * This method compares two cards' order. Different from the super class's method,
	 * this overridden method treat 0(A) the second largest and 1(2) the largest.
	 * @see Card#compareTo(Card)
	 * @return int. 1 if this card is larger than the specified. 0 if equal and -1 if smaller.
	 */
	public int compareTo(Card card){
		if(this.rank == 0){
			if(card.rank == 1){
				return -1;
			}
			else if(card.rank == 0){
				if(this.suit > card.suit){
					return 1;
				}
				else if(this.suit == card.suit){
					return 0;
				}
				else{
					return -1;
				}
			}
			else{
				return 1;
			}
		}
		else if(this.rank == 1){
			if(card.rank == 1){
				if(this.suit > card.suit){
					return 1;
				}
				else if(this.suit == card.suit){
					return 0;
				}
				else{
					return -1;
				}
			}
			else{
				return 1;
			}
		}
		else{
			if(card.rank == 1 || card.rank == 0){
				return -1;
			}
			else if(this.rank == card.rank){
				if(this.suit > card.suit){
					return 1;
				}
				else if(this.suit == card.suit){
					return 0;
				}
				else{
					return -1;
				}
			}
			else if(this.rank > card.rank){
				return 1;
			}
			else{
				return -1; 
			}
		}
	} 
}
	