import javax.swing.*;
import javax.swing.text.DefaultCaret;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
/**
 * This class models a Big Two game table.
 * It models the GUI of big two game and functionalities of the table.
 * @author Rick
 *
 */
public class BigTwoTable implements CardGameTable{
	public BigTwoTable(CardGame game){
		this.game = game;
	}
	private CardGame game;
	private boolean[] selected;
	private int activePlayer;
	private JFrame frame;
	private JPanel bigTwoPanel;
	private JButton playButton;
	private JButton passButton;
	private JTextArea msgArea;
	private Image[][] cardImages; // Rank first, suit next.
	private Image cardBackImage;
	private Image[] avatars;
	private JPanel textPanel;
	private JTextArea chatMsgArea;
	private JTextField outgoing;
	private JMenuItem connectItem;
	//boolean a = ((BigTwoClient)game).endOfGame();
	
	/**
	 * This method is used to disable the connectItem 
	 * when the client is connected to the server.
	 */
	public void disableConnectItem(){
		this.connectItem.setEnabled(false);
	}
	
	
	/**
	 * This method is used to print the chat message to the chatMsgArea.
	 * @param String
	 */
	public void printChatMsg(String msg){
		chatMsgArea.append(msg);
	}
	/**
	 * Setter of setActivePlayer.
	 * @see CardGameTable#setActivePlayer(int)
	 */
	public void setActivePlayer(int activePlayer){
		this.activePlayer = activePlayer;
	}
	/** 
	 * Retrieve the indices of the selected cards.
	 * @see CardGameTable#getSelected()
	 * @return int[]
	 */
	public int[] getSelected(){
		int selectedCounter = 0;
		for(int i = 0; i < selected.length; ++i){
			if(selected[i]){
				++selectedCounter;
			}//end of if
		}//end of the loop
		int[] selectedId = new int[selectedCounter];
		int counter = 0;
		for(int i = 0; i < selected.length; ++i){
			if(selected[i]){
				selectedId[counter] = i;
				++counter;
			}//end of if
		}//end of the loop
		return selectedId;
 	}
	/** 
	 * Reset selected according to active player.
	 * @see CardGameTable#resetSelected()
	 */
	public void resetSelected(){
		int newSize = game.getPlayerList().get(activePlayer).getCardsInHand().size();
		selected = new boolean[newSize];
		for(int i = 0; i < newSize; ++i){
			selected[i] = false;
		}
	}
	/** 
	 * This method repaint the frame.
	 * @see CardGameTable#repaint()
	 */
	public void repaint(){
		frame.repaint();
	}
	/** 
	 * This method print message onto the msgArea.
	 * @param String
	 * @see CardGameTable#printMsg(java.lang.String)
	 */
	public void printMsg(String msg){
		msgArea.append(msg);
	}
	/**
	 * This method clear all messages in the JTextArea.
	 * @see CardGameTable#clearMsgArea()
	 */
	public void clearMsgArea(){
		msgArea.setText("");
	}
	
	/**
	 * This method reset status of table to its initial.
	 * @see CardGameTable#reset()
	 */
	public void reset(){
		selected = new boolean[13];
		for(int i = 0; i < 13; ++i){
			selected[i] = false;
		}
		//activePlayer = game.getCurrentIdx();
		this.clearMsgArea();
		this.repaint();
	}
	
	/**
	 * This method enables functionalities of all components on the frame.
	 * @see CardGameTable#enable()
	 */
	public void enable(){
		playButton.setEnabled(true);
		passButton.setEnabled(true);
		bigTwoPanel.setEnabled(true);
		frame.setEnabled(true);
		msgArea.setEnabled(true);
	}
	/** This method disable functionalities of components on the frame.
	 * @see CardGameTable#disable()
	 */
	public void disable(){
		playButton.setEnabled(false);
		passButton.setEnabled(false);
		bigTwoPanel.setEnabled(false);
		msgArea.setEnabled(false);
	}//Incomplete.
	/**
	 * BigTwoPanel extends JPanel and implements MouseListener.
	 * This class models a Big Two game table's behaviors and status.
	 * @author Gao Yuan
	 *	
	 */
	public class BigTwoPanel extends JPanel implements MouseListener{
		private Image bgImage = new ImageIcon("DT.jpg").getImage();//Background picture
		private Image endImage = new ImageIcon("end.jpg").getImage();
		/**
		 * Implementation of mouseClicked event. 
		 * This method checks whether a card or cards are clicked or not and 
		 * repaint the graph and update the GUI.
		 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
		 */
		public void mouseClicked(MouseEvent arg0) {
			int offsetX = (activePlayer / 2) * 5 * this.getWidth() / 10 - 20 * this.getWidth() / 1525;
			int offsetY = (activePlayer % 2) * 6 * this.getHeight() / 10;
			if(activePlayer == 2 || activePlayer == 3){
				offsetX = offsetX + 25 * this.getWidth() * (13 - game.getPlayerList().get(activePlayer).getCardsInHand().size()) / 1525;
			}
			int x = arg0.getX() - offsetX;
			int y = arg0.getY() - offsetY;
			int[] allSelected = new int[13];
			int counter = 0;
			for(int i = 0; i < game.getPlayerList().get(activePlayer).getCardsInHand().size(); ++i){
				
				if(selected[i]){
					if((x >= this.getWidth() / 10 + 25 * this.getWidth() * i / 1525)&& (x <= this.getWidth() / 10  + this.getWidth() *25 * i / 1525 + this.getWidth() * 100 / 1525) && (y >= this.getHeight() / 10 - this.getHeight() * 15 / 800) && (y <= this.getHeight() / 10 + 120 * this.getHeight() / 903 - getHeight() * 15 / 800)){
						allSelected[counter] = i;
						++counter;
					}
				}
				else{
					if((x >= this.getWidth() / 10 + this.getWidth() * 25 * i / 1525 )&& (x <= this.getWidth() / 10 + this.getWidth() * 25 * i / 1525 + this.getWidth() * 100 / 1525) && (y >= this.getHeight() / 10 ) && (y <= this.getHeight() / 10 + this.getHeight() * 120 / 903)){

						allSelected[counter] = i;
						++counter;
					}
				}
			}
			if(counter != 0){
				selected[allSelected[counter - 1]] = !selected[allSelected[counter - 1]];
				frame.repaint();
			}
	    }
		/** 
		 * This method prints the screen depending on the status of the game.
		 * Print the font side of cards of current players (lifted if selected) while back of cards
		 * of inactive players.
		 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
		 */
		public void paintComponent(Graphics g){
			if(activePlayer != game.getCurrentIdx()){
				playButton.setEnabled(false);
				passButton.setEnabled(false);
			}
			else{
				playButton.setEnabled(true);
				passButton.setEnabled(true);
			}
			g.setFont(new Font("Comic Sans", Font.BOLD, 16));
			g.setColor(new Color(190, 25, 10));
			if(!((BigTwoClient)game).getEnd()){
				g.drawImage(bgImage, 0, 0, this.getWidth(), this.getHeight(), null);
			}
			else{
				g.drawImage(endImage, 0, 0, this.getWidth(), this.getHeight(), null);
			}
			for(int j = 0; j < 4; ++j){
				CardList cardsPainted = game.getPlayerList().get(j).getCardsInHand();
				int offsetX = (j / 2) * 5 * this.getWidth() / 10 - this.getWidth() * 20 / 1525;
				int offsetY = (j % 2) * 6 * this.getHeight() / 10;
				if(j == 2 || j == 3){
					offsetX = offsetX + this.getWidth() * 25 * (13 - cardsPainted.size()) / 1525;
				}
				int offsetA;
				if(j == 0 || j == 1){
					offsetA = -this.getWidth() * 110 / 1525;
				}
				else{
					offsetA = this.getWidth() * 25 * (cardsPainted.size() - 1) / 1525 + this.getWidth() * 100 / 1525;
				}
				if(game.getPlayerList().get(j).getName() != null){
					g.drawImage(avatars[j], this.getWidth() / 10 + offsetA + offsetX,  this.getHeight() / 10 + offsetY, this.getWidth() * 80 / 1525, this.getHeight() * 80 / 903, this);
					if(activePlayer == j){
						g.drawString("You", this.getWidth() / 10 +offsetA + offsetX + this.getWidth() * 10 / 1525, this.getHeight() / 10 + offsetY + this.getHeight() * 120 / 903);
					}
					else{
						g.drawString(game.getPlayerList().get(j).getName(), this.getWidth() / 10 +offsetA + offsetX + this.getWidth() * 10 / 1525, this.getHeight() / 10 + offsetY + this.getHeight() * 120 / 903);
					}
				}
				if(j == activePlayer){
					for(int i = 0; i < cardsPainted.size(); ++i){
						if(selected[i]){
							g.drawImage(cardImages[cardsPainted.getCard(i).getRank()][cardsPainted.getCard(i).getSuit()], this.getWidth() / 10 + this.getWidth() * 25 * i / 1525 + offsetX, this.getHeight() / 10 - this.getHeight() * 15 / 800 + offsetY,  this.getWidth() * 100 / 1525, this.getHeight() * 120 / 903, this);
						}
						else{
							g.drawImage(cardImages[cardsPainted.getCard(i).getRank()][cardsPainted.getCard(i).getSuit()], this.getWidth() / 10 + this.getWidth() * 25 * i / 1525 + offsetX, this.getHeight() / 10 + offsetY, this.getWidth() * 100 / 1525, this.getHeight() * 120 / 903, this);
						}
					}//end of inner for loop
				}//end of if
				else{
					for(int i = 0; i < cardsPainted.size(); ++i){
						g.drawImage(cardBackImage, this.getWidth() / 10 + this.getWidth() * 25 * i / 1525 + offsetX, this.getHeight() / 10 + offsetY, this.getWidth() * 100 / 1525, this.getHeight() * 120 / 903, this);
					}
				}//end of else
			}//end of outer for loop
			//The following codes deal with drawing handsOnTable.
			int sizeOfHandsOnTable = game.getHandsOnTable().size();
			Hand lastHand;
			if(sizeOfHandsOnTable != 0){
				lastHand = game.getHandsOnTable().get(sizeOfHandsOnTable - 1);
				String lastPlayer = lastHand.getPlayer().getName();
				if(lastPlayer == ((BigTwoClient)game).getPlayerList().get(((BigTwoClient)game).getPlayerID()).getName()){
					lastPlayer = "You";
				}
				g.setColor(new Color(190, 25, 190));
				g.drawString("Last hand played by: " + lastPlayer, this.getWidth() / 2 - this.getWidth() * 50 / 1525, 2 * this.getHeight() / 5 - this.getHeight() * 15 / 903); //Need to decide the location.
				for(int i = 0; i < lastHand.size(); ++i){
					g.drawImage(cardImages[lastHand.getCard(i).getRank()][lastHand.getCard(i).getSuit()],  this.getWidth() / 2 + this.getWidth() * 25 * i / 1525, 2 * this.getHeight() / 5, this.getWidth() * 120 / 1525, this.getHeight() * 140 / 903, this);
				}
			}
		}
		
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		
	} 
	
	/**
	 * This class implements ActionListener and listen from the 
	 * play button. Call makeMove method if it is a legal play.
	 * @author Rick
	 *
	 */
	public class PlayButtonListener implements ActionListener{

		/**
		 * This method handles the click on the playButtin.
		 * It calls the makeMove method accordingly.
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if(game.getCurrentIdx() == activePlayer){
				int[] cardIdx = getSelected();
				if(cardIdx.length == 0){
					msgArea.append("Please select cards before clicking <play>\n");
				}
				else{
					game.makeMove(activePlayer, cardIdx);
				}
			}
		}
		
	}
	/**
	 * This class implements ActionListener and listen from the
	 * pass button. Call checkMove if pass button is clicked.
	 * @author Rick
	 *
	 */
	public class PassButtonListener implements ActionListener{

		/**
		 * This method handles the click on passButton
		 * It calls the makeMove method accordingly.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(game.getCurrentIdx() == activePlayer){
				game.makeMove(activePlayer, null);
			}
		}
		
	}
	/**
	 * This class implements ActionListener and listen from the 
	 * ConnectMenuItem.Call makeConnection method to make the connection server.
	 * @author Rick
	 *
	 */
	public class ConnectMenuItemListener implements ActionListener{

		/**
		 * This method handles the click on the connectItem.
		 * It calls the makeConnection method accordingly.
		 */
		@Override
		
		

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//if(((BigTwoClient)game).isConnected()){
				//printMsg("Already Connected to server.\n");
			//}
			//else{
				/*if(((BigTwoClient)game).isConnected()){
					printMsg("Already connected!\n");
					return;
				}*/
			String IP = null;
			String port = null;
			while(IP == null){
				IP = JOptionPane.showInputDialog(null, "Input the serverIP\n", "127.0.0.1");
			}
			((BigTwoClient)game).setServerIP(IP);
			while(port == null){
				port = JOptionPane.showInputDialog(null, "Input the serverPort\n", "2396");
			}
			((BigTwoClient)game).setServerPort(Integer.parseInt(port));
				//((BigTwoClient)game).setServerIP(JOptionPane.showInputDialog(frame, "Input the serverIP\n"));
				//((BigTwoClient)game).setServerPort(Integer.parseInt(JOptionPane.showInputDialog(frame, "Input the serverPort\n")));
			((BigTwoClient)game).makeConnection();
			//}
		}
		
	}
	/**
	 * This class implements ActionListener and listen from the 
	 * QuitMenuItem. Exit the program if item clicked.
	 * @author Rick
	 *
	 */
	public class QuitMenuItemListener implements ActionListener{

		/**
		 * This method handles the click on the quitMenuItem.
		 * It shut the client immediately.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.exit(0);
		}
	}
	
	/**
	 * This class listens from the enterMenuItem and handles the click.
	 * @author Rick
	 *
	 */
	public class EnterMenuItemListener implements ActionListener{

		/**
		 * This method handles the click on the enterMenuItem.
		 * It calls sendMessage method to communicate with the server.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			CardGameMessage msg = new CardGameMessage(CardGameMessage.MSG, -1, outgoing.getText());
			((BigTwoClient)game).sendMessage(msg);
			outgoing.setText("");
			outgoing.requestFocus();
		}
		
	}
	/**
	 * This method listens the press on the ENTER.
	 *
	 */
	public class EnterListener implements ActionListener{

		/**
		 * This method handles the press on ENTER.
		 * It will send the chat message to the server.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			CardGameMessage msg = new CardGameMessage(CardGameMessage.MSG, -1, outgoing.getText());
			((BigTwoClient)game).sendMessage(msg);
			outgoing.setText("");
			outgoing.requestFocus();
		}
		
	}
	
	/**
	 * This method initializes the GUI.
	 * It integrates all the components of this GUI and displays them.
	 */
	public void go(){
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bigTwoPanel = new BigTwoPanel();
		playButton = new JButton("play");
		passButton = new JButton("pass");
		playButton.addActionListener(new PlayButtonListener());
		passButton.addActionListener(new PassButtonListener());
		
		outgoing = new JTextField(15);
		outgoing.addActionListener(new EnterListener());
		JLabel messageDiscriptor = new JLabel("Message: ");
		messageDiscriptor.setLabelFor(outgoing);
		//messageDiscriptor.getLabelFor();
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(passButton);
		buttonPanel.add(playButton);
		
		
		msgArea = new JTextArea(30, 25);
		msgArea.setWrapStyleWord(true);
		msgArea.setLineWrap(true);
		DefaultCaret caret = (DefaultCaret)msgArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		JScrollPane scroller = new JScrollPane(msgArea);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		chatMsgArea = new JTextArea(30, 25);
		chatMsgArea.setWrapStyleWord(true);
		chatMsgArea.setLineWrap(true);
		DefaultCaret chatCaret = (DefaultCaret)chatMsgArea.getCaret();
		chatCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		JScrollPane chatScroller = new JScrollPane(chatMsgArea);
		chatScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		chatScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		textPanel = new JPanel();
		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
		textPanel.add(scroller);
		textPanel.add(chatScroller);
		textPanel.add(messageDiscriptor);
		textPanel.add(outgoing);
		
		bigTwoPanel.addMouseListener((BigTwoPanel)bigTwoPanel);
		
		
		JMenuBar menuBar = new JMenuBar();
		JMenu gameLabel = new JMenu("Game");
	    connectItem = new JMenuItem("connect");
		JMenuItem quitItem = new JMenuItem("quit");
		connectItem.addActionListener(new ConnectMenuItemListener());
		quitItem.addActionListener(new QuitMenuItemListener());
		
		JMenu messageLabel = new JMenu("Message");
		JMenuItem enterItem = new JMenuItem("Enter");
		enterItem.addActionListener(new EnterMenuItemListener());
		
		messageLabel.add(enterItem);
		gameLabel.add(connectItem);
		gameLabel.add(quitItem);
		menuBar.add(gameLabel);
		menuBar.add(messageLabel);
		frame.setJMenuBar(menuBar);
		
		//The following codes deal with the initializations of all images.
		cardBackImage = new ImageIcon("b.gif").getImage();
		String[] rankConvert = {"a", "2", "3", "4", "5", "6", "7", "8", "9", "t", "j", "q", "k"};
		String[] suitConvert = {"d", "c", "h", "s"};
		cardImages = new Image[13][4];
		for(int i = 0; i < 13; ++i){
			for(int j = 0; j < 4; ++j){
				cardImages[i][j] = new ImageIcon(rankConvert[i] + suitConvert[j] + ".gif").getImage();
			}//end of the inner loop
		}//End of the outer loop
		Image avatar0 = new ImageIcon("batman_128.png").getImage();
		Image avatar1 = new ImageIcon("flash_128.png").getImage();
		Image avatar2 = new ImageIcon("superman_128.png").getImage();
		Image avatar3 = new ImageIcon("wonder_woman_128.png").getImage();
		avatars = new Image[4];
		avatars[0] = avatar0;
		avatars[1] = avatar1;
		avatars[2] = avatar2;
		avatars[3] = avatar3;
		
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		leftPanel.add(bigTwoPanel, BorderLayout.CENTER);
		leftPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		frame.add(leftPanel, BorderLayout.CENTER);
		frame.add(textPanel, BorderLayout.EAST);
		frame.setSize(1900, 1000);
		frame.setVisible(true);
		String name = null;
		String IP = null;
		String port = null;
		while(name == null){
			name = JOptionPane.showInputDialog(null, "Create a nick name:\n");
		}
		((BigTwoClient)game).setPlayerName(name);
		while(IP == null){
			IP = JOptionPane.showInputDialog(null, "Input the serverIP\n", "127.0.0.1");
		}
		((BigTwoClient)game).setServerIP(IP);
		while(port == null){
			port = JOptionPane.showInputDialog(null, "Input the serverPort\n", "2396");
		}
		((BigTwoClient)game).setServerPort(Integer.parseInt(port));
		
		//((BigTwoClient)game).setPlayerName(JOptionPane.showInputDialog(null, "Create a nick name:\n"));
		//((BigTwoClient)game).setServerIP(JOptionPane.showInputDialog(null, "Input the serverIP\n", "127.0.0.1"));
		//((BigTwoClient)game).setServerPort(Integer.parseInt(JOptionPane.showInputDialog(null, "Input the serverPort\n", "2396")));
		//((BigTwoClient)game).makeConnection();
		
	}
}
