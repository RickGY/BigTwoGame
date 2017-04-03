import java.util.ArrayList;
import java.io.*;
import java.net.*;

import javax.swing.JOptionPane;

/**
 * This class BigTwoClient implements CardGame and NetWorkGame.
 * This class models the behavior of the client of Big Two game.
 * @author Rick
 * 
 */
public class BigTwoClient implements CardGame, NetworkGame{

	/**
	 * This is the constructor of BigTwoClient class.
	 */
	public BigTwoClient(){
		playerList = new ArrayList<CardGamePlayer>(4);
		for(int i = 0; i < 4; ++i){
			playerList.add(new CardGamePlayer());
			playerList.get(i).setName(null);
		}
		table = new BigTwoTable(this);
		table.go();
		this.makeConnection();
	}
	
	private int numOfPlayers;
	private Deck deck;
	private ArrayList<CardGamePlayer> playerList;
	private ArrayList<Hand> handsOnTable = new ArrayList<Hand>();
	private int playerID;
	private String playerName;
	private String serverIP;
	private int serverPort;
	private Socket sock;
	private ObjectOutputStream oos;
	private int currentIdx;
	private BigTwoTable table;
	private boolean end = false;
	
	
	/**
	 * This is the getter of variable end.
	 * It is used to check if the game is over or not.
	 * @return boolean
	 */
	public boolean getEnd(){
		return end;
	}
	
	/**
	 * This is the getter of playerID.
	 * @return int 
	 */
	@Override
	public int getPlayerID() {
		// TODO Auto-generated method stub
		return this.playerID;
	}

	/**
	 * This the setter of playerID.
	 * @param int
	 */
	@Override
	public void setPlayerID(int playerID) {
		// TODO Auto-generated method stub
		this.playerID = playerID;
	}

	/**
	 * This is the getter of playerName.
	 * @return String
	 */
	@Override
	public String getPlayerName() {
		// TODO Auto-generated method stub
		return this.playerName;
	}

	/**
	 * This is the setter of playerName.
	 * @param String
	 */
	@Override
	public void setPlayerName(String playerName) {
		// TODO Auto-generated method stub
		this.playerName = playerName;
	}

	/**
	 * This is the getter of serverIP
	 * @return String
	 */
	@Override
	public String getServerIP() {
		// TODO Auto-generated method stub
		return this.serverIP;
	}

	/**
	 * This is the setter of serverIP.
	 * @param String
	 */
	@Override
	public void setServerIP(String serverIP) {
		// TODO Auto-generated method stub
		this.serverIP = serverIP;
	}

	/**
	 * This is the getter of serverPort.
	 * @return int
	 */
	@Override
	public int getServerPort() {
		// TODO Auto-generated method stub
		return this.serverPort;
	}

	/**
	 * This is the setter of serverPort.
	 * @param int
	 */
	@Override
	public void setServerPort(int serverPort) {
		// TODO Auto-generated method stub
		this.serverPort = serverPort;
	}

	/**
	 * This method is used to make connection to 
	 * the server according to serverIP and serverPort.
	 * It starts a new thread to handle object input from the server.
	 */
	@Override
	public void makeConnection() {
		// TODO Auto-generated method stub
		try{
			sock = new Socket(this.serverIP, this.serverPort);
			oos = new ObjectOutputStream(sock.getOutputStream());
			Thread receiverThread = new Thread(new ServerHandler());
			receiverThread.start();
			this.sendMessage(new CardGameMessage(CardGameMessage.JOIN , -1, this.playerName));
			this.sendMessage(new CardGameMessage(CardGameMessage.READY, -1, null));
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	/**
	 * This method is used to interpret the message get from the server
	 * and react accordingly.
	 * @param GameMessage
	 */
	@Override
	public void parseMessage(GameMessage message) {
		// TODO Auto-generated method stub
		int msgType = message.getType();
		if(msgType == CardGameMessage.PLAYER_LIST){
			this.setPlayerID(message.getPlayerID());
			table.setActivePlayer(this.playerID);
			for(int i = 0; i < playerList.size(); ++i){
				playerList.get(i).setName(((String[])(message.getData()))[i]);
				table.repaint();
			}
		}
		else if(msgType == CardGameMessage.JOIN){
			playerList.get(message.getPlayerID()).setName((String)(message.getData()));
			table.disableConnectItem();
			table.repaint();
		}
		else if(msgType == CardGameMessage.FULL){
			table.printMsg("The server is full.\n Cannot join the game.\n");
		}
		else if(msgType == CardGameMessage.QUIT){
			playerList.get(message.getPlayerID()).setName(null);
			table.printMsg((String)message.getData() + " has left the game.\n");
			if(!this.end){
				table.disable();
				sendMessage(new CardGameMessage(CardGameMessage.READY, -1, null));
			}
			table.repaint();
		}
		else if(msgType == CardGameMessage.READY){
			table.printMsg(playerList.get(message.getPlayerID()).getName() + " is ready to play.\n");
		}
		else if(msgType == CardGameMessage.START){
			this.start((BigTwoDeck)(message.getData()));
		}
		else if(msgType == CardGameMessage.MOVE){
			this.checkMove(message.getPlayerID(), (int[])(message.getData()));
		}
		else if(msgType == CardGameMessage.MSG){
			table.printChatMsg((String)(message.getData()));
			table.printChatMsg("\n");
		}
	}

	/**
	 * This method is used to send message to the server.
	 * @param GameMessage
	 */
	@Override
	public void sendMessage(GameMessage message) {
		// TODO Auto-generated method stub
		try{
			oos.writeObject(message);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	/**
	 * This is the getter of numOfPlayer
	 * @return int
	 */
	@Override
	public int getNumOfPlayers() {
		// TODO Auto-generated method stub
		return this.playerList.size();
	}

	/**
	 * This is the getter of deck.
	 * @return Deck
	 */
	@Override
	public Deck getDeck() {
		// TODO Auto-generated method stub
		return this.deck;
	}

	/**
	 * This is the getter of playerList.
	 * @return ArrayList<CardGamePlayer>
	 */
	@Override
	public ArrayList<CardGamePlayer> getPlayerList() {
		// TODO Auto-generated method stub
		return this.playerList;
	}

	/**
	 * This is the getter of handsOntable.
	 * @return ArrayList<Hand>
	 */
	@Override
	public ArrayList<Hand> getHandsOnTable() {
		// TODO Auto-generated method stub
		return this.handsOnTable;
	}

	/**
	 * This is the getter of currentIdx.
	 * @return int
	 */
	@Override
	public int getCurrentIdx() {
		// TODO Auto-generated method stub
		return this.currentIdx;
	}

	/**
	 * This method take a shuffled deck and start the game.
	 * It also repaints the GUI.
	 * @param Deck
	 */
	@Override
	public void start(Deck deck) {
		// TODO Auto-generated method stub
		//The following codes remove cards from all players and the table .
		end = false;
		for(int i = 0; i < 4; ++i){
			playerList.get(i).removeAllCards();
		}
		handsOnTable = new ArrayList<Hand>();
		//The following codes distribute cards to all players.
		for(int i = 0; i < deck.size(); ++i){
			playerList.get(i / 13).addCard(deck.getCard(i));
		}
		//The following codes identify the player who holds the 3 of Diamonds.
		//Set the currentIdx to be the playerID of the one holding 3 of Diamonds.
		for(int i = 0; i < 4; ++i){
			playerList.get(i).sortCardsInHand();
			if(playerList.get(i).getCardsInHand().getCard(0).getRank() == 2 && playerList.get(i).getCardsInHand().getCard(0).getSuit() == 0){
				currentIdx = i;
			}
		}
		//The following codes set the active player to be the local player.
		//Hence the table will only show the cards of the local player.
		table.setActivePlayer(playerID);
		//The following codes reset and enable the table components.
		table.enable();
		table.reset();
		table.resetSelected();
		table.clearMsgArea();
		table.repaint();
		if(currentIdx == playerID){
			table.printMsg("Your turn.\n");
		}
		else{
			table.printMsg(playerList.get(currentIdx).getName() + "'s turn.\n");
		}
	}

	/**
	 * This method models the main logic of the game. It checks whether a move is legal or not
	 * and makes decision to make the move and proceed or wait for new move accordingly.
	 * @param int, int[]
	 */
	@Override
	public void checkMove(int playerID, int[] cardIdx) {
		// TODO Auto-generated method stub
		
		
		int prevPlayer = currentIdx;
		Hand lastHand;
		if(cardIdx == null){
			if(handsOnTable.size() == 0){
				//if(this.playerID == playerID){
					table.printMsg("Not a legal move!!!\n");
				//}
			}
			else{
				lastHand = handsOnTable.get(handsOnTable.size() - 1);
				//The case that last is hand is played by the active player. Pass is not allowed.
				if(playerList.get(currentIdx).getName() == lastHand.getPlayer().getName()){
					//if(this.playerID == playerID){
						table.printMsg("Not a legal move!!!\n");
					//}
				}
				else{
					table.printMsg("{Pass}\n");
					prevPlayer = currentIdx;
					++currentIdx;
					currentIdx = currentIdx % 4;
				}
			}
		}
		if(cardIdx != null){
			CardList selectedCards = playerList.get(currentIdx).play(cardIdx);
			selectedCards.sort();
			//Handles the first round
			if(handsOnTable.size() == 0){
				if(BigTwoClient.composeHand(playerList.get(currentIdx), selectedCards) == null || selectedCards.getCard(0).getRank() != 2 || selectedCards.getCard(0).getSuit() != 0){
					//if(this.playerID == playerID){
						table.printMsg("Not a legal move!!!\n");
					//}
				}
				else{
					table.printMsg("{");
					table.printMsg(BigTwoClient.composeHand(playerList.get(currentIdx), selectedCards).getType() + "}");
					table.printMsg(BigTwoClient.composeHand(playerList.get(currentIdx), selectedCards).toString() + "\n");
					handsOnTable.add(BigTwoClient.composeHand(playerList.get(currentIdx), selectedCards));
					playerList.get(currentIdx).removeCards(selectedCards);
					prevPlayer = currentIdx;
					++currentIdx;
					currentIdx = currentIdx % 4;
				}//end of else.
			}// end of the case of first round.
			//The following codes handle the case of non-first round
			else{
				lastHand = handsOnTable.get(handsOnTable.size() - 1);
				//the following codes handle the case that the last hand is played by the active player
				if(lastHand.getPlayer().getName() == playerList.get(currentIdx).getName()){
					if(BigTwoClient.composeHand(playerList.get(currentIdx), selectedCards) == null){
						//if(this.playerID == playerID){
							table.printMsg("Not a legal move!!!\n");
						//}
					}
					else{
						table.printMsg("{");
						table.printMsg(BigTwoClient.composeHand(playerList.get(currentIdx), selectedCards).getType() + "}");
						table.printMsg(BigTwoClient.composeHand(playerList.get(currentIdx), selectedCards).toString() + "\n");
						handsOnTable.add(BigTwoClient.composeHand(playerList.get(currentIdx), selectedCards));
						playerList.get(currentIdx).removeCards(selectedCards);
						prevPlayer = currentIdx;
						++currentIdx;
						currentIdx = currentIdx % 4;
					}
				}
				//The following codes handle the case that the last hand is not played by the active player.
				else{
					int numOfHandsOnTable = handsOnTable.size();
					if(BigTwoClient.composeHand(playerList.get(currentIdx), selectedCards) == null || !BigTwoClient.composeHand(playerList.get(currentIdx), selectedCards).beats(handsOnTable.get(numOfHandsOnTable - 1))){
						//if(this.playerID == playerID){
							table.printMsg("Not a legal move!!!\n");
						//}
					}
					else{
						table.printMsg("{");
						table.printMsg(BigTwoClient.composeHand(playerList.get(currentIdx), selectedCards).getType() + "}");
						table.printMsg(BigTwoClient.composeHand(playerList.get(currentIdx), selectedCards).toString() + "\n");
						handsOnTable.add(BigTwoClient.composeHand(playerList.get(currentIdx), selectedCards));
						playerList.get(currentIdx).removeCards(selectedCards);
						prevPlayer = currentIdx;
						++currentIdx;
						currentIdx = currentIdx % 4;
					}//end of else.
				}//end of the case that last hand is not played by the active player.
			}
		}//end of the case that player selected some cards.
		if(!endOfGame()){
			table.resetSelected();
			table.repaint();
			if(currentIdx == this.playerID){
				table.printMsg("Your turn.\n");
			}
			else{
				table.printMsg(playerList.get(currentIdx).getName() + "'s turn.\n");
			}
		}
		if(endOfGame()){
			table.setActivePlayer(prevPlayer);
			table.resetSelected();
			table.repaint();
			table.printMsg("Game ends\n");
			String result = new String("");
			for(int i = 0; i < 4; ++i){
				if(i == prevPlayer){
					if(i == this.playerID){
						table.printMsg("You win the game.\n");
						result += ("You win the game.\n");
					}
					else{
						table.printMsg(playerList.get(i).getName() + " wins the game.\n");
						result += (playerList.get(i).getName() + " wins the game.\n");
					}
				}
				else{
					if(i == this.playerID){
						table.printMsg("You have " + playerList.get(i).getNumOfCards() + " cards in hand.\n");
						result += ("You have " + playerList.get(i).getNumOfCards() + " cards in hand.\n");
					}
					else{
						table.printMsg(playerList.get(i).getName() + " has " + playerList.get(i).getNumOfCards() + " cards in hand.\n");
						result += (playerList.get(i).getName() + " has " + playerList.get(i).getNumOfCards() + " cards in hand.\n");
					}
				}
			}
			JOptionPane.showMessageDialog(null, result, "Game Over!", JOptionPane.PLAIN_MESSAGE);
			this.sendMessage(new CardGameMessage(CardGameMessage.READY, -1, null));
			table.disable();
		}
	}

	/**
	 * This method checks whether it is the end of the game or not.
	 * @return boolean
	 */
	@Override
	public boolean endOfGame() {
		// TODO Auto-generated method stub
		for(int i = 0; i < playerList.size(); ++i){
			if(playerList.get(i).getNumOfCards() == 0){
				end = true;
				return true;
			}
		}
		end = false;
		return false;
	}

	/**
	 * This method is used to communicate with the server and send
	 * information of the move. It sends the playerID of the local player 
	 * and the cards selected to the server.
	 * @param int, int[]
	 */
	@Override
	public void makeMove(int playerID, int[] cardIdx) {
		// TODO Auto-generated method stub
		CardGameMessage msg = new CardGameMessage(CardGameMessage.MOVE, -1, cardIdx);
		this.sendMessage(msg);
	}
	
	
	/**
	 * This class establish the ObjectInputStream and handles all the incoming messages from the server.
	 * It calls the parseMessage to interpret the message and react accordingly.
	 * 
	 * @author Rick
	 *
	 */
	private class ServerHandler implements Runnable{
		
		CardGameMessage incomingMsg;
		ObjectInputStream ois;

		/**
		 * This method specifies the jobs for the thread to handle.
		 * It establish the ObjectInputStream and handles all the incoming messages from the server.
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			try{
				ois = new ObjectInputStream(sock.getInputStream());
				while((incomingMsg = (CardGameMessage)ois.readObject()) != null){
					parseMessage(incomingMsg);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * This method is used to check whether the selected cards compose a valid hand
	 * and a valid hand if possible.
	 * @param player
	 * @param cards
	 * @return Hand   null if the selected cards do not compose a valid hand.
	 */
	public static Hand composeHand(CardGamePlayer player, CardList cards){
		if(cards.size() == 1){
			Single single = new Single(player, cards);
			return single;
		}
		else if(cards.size() == 2){
			Pair pair = new Pair(player, cards);
			if(pair.isValid()){
				return pair;
			}
			else{
				return null;
			}
		}
		else if(cards.size() == 3){
			Triple triple = new Triple(player, cards);
			if(triple.isValid()){
				return triple;
			}
			else{
				return null;
			}
		}
		else if(cards.size() == 5){
			Straight straight = new Straight(player, cards);
			Flush flush = new Flush(player, cards);
			FullHouse fullHouse = new FullHouse(player, cards);
			Quad quad = new Quad(player, cards);
			StraightFlush straightFlush = new StraightFlush(player, cards);
			if(straightFlush.isValid()){
				return straightFlush;
			}
			else if(quad.isValid()){
				return quad;
			}
			else if(fullHouse.isValid()){
				return fullHouse;
			}
			else if(flush.isValid()){
				return flush;
			}
			else if(straight.isValid()){
				return straight;
			}
			else{
				return null;
			}
		}
		else{
			return null;
		}
	}
	public static void main(String[] args){
		BigTwoClient game = new BigTwoClient();
	}
}
