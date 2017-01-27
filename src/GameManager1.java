import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;



public class GameManager1 extends JFrame {

	private static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}


	static Board b;
	static Player p1;
	static Player p2;
	int wait=0;
	static Scanner s = new Scanner(System.in);
	private static final long serialVersionUID = 1L;
	private static final int boardSize=getRandomNumberInRange(5, 9);
	JPanel[] row = new JPanel[boardSize+1];
	static JButton[] button = new JButton[boardSize*boardSize];
	String buttonString = "";
	int[] dimW = {300,45,100,90};
	int[] dimH = {35, 40};
	Dimension displayDimension = new Dimension(dimW[0], dimH[0]);
	Dimension regularDimension = new Dimension(dimW[1], dimH[1]);

	double[] temporary = {0, 0};
	static JTextArea display = new JTextArea(1,20);
	Font font = new Font("Times new Roman", Font.BOLD, 14);

	public GameManager1()  {

		super("Game of Dots");

		p1 = Player.takePlayerInput(1,'1');
		p2 = Player.takePlayerInput(2,'2');


		b  = new Board(boardSize);

		setDesign();
		setSize(550, 550);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		GridLayout grid = new GridLayout(boardSize+1,boardSize);
		setLayout(grid);

		FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
		FlowLayout f2 = new FlowLayout(FlowLayout.CENTER,1,1);
		for(int i = 0; i < boardSize+1; i++) {
			row[i] = new JPanel();
		}

		row[0].setLayout(f1);

		for(int i = 1; i < boardSize+1; i++)
			row[i].setLayout(f2);

		for(int i = 0; i < boardSize*boardSize; i++) {
			button[i] = new JButton();
			button[i].setText(buttonString);
			button[i].setFont(font);
//			button[i].addActionListener(this);
		}


		display.setFont(font);
		display.setEditable(false);
		display.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		display.setPreferredSize(displayDimension);
		for(int i = 0; i < boardSize*boardSize; i++)
			button[i].setPreferredSize(regularDimension);

		row[0].add(display);
		add(row[0]);

		for(int j=0;j<boardSize;++j){
			for(int i = 0; i < boardSize; i++)
				row[j+1].add(button[j*boardSize+i]);
			add(row[j+1]);
		}

		setVisible(true);
		button[0].setBackground(Color.BLUE);
		button[boardSize*boardSize-1].setBackground(Color.YELLOW);
//
//		play();
	}



	static Player currentPlayer;

	public final void setDesign() {
		try {
			UIManager.setLookAndFeel(
					"com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch(Exception e) {   
		}
	}
	static boolean player1Turn = false;
	static String showNow="";
	
	private static void play() throws InterruptedException, IOException{

		player1Turn = !player1Turn;
		if (player1Turn)
			currentPlayer = p1;
		else
			currentPlayer = p2;
		
		showNow+=currentPlayer.name + "'s turn";
		display.setText(showNow);
		showNow="";
		
		//write to file
		Board.performWrite(currentPlayer.symbol);
		//Execute C++ program here
		if(player1Turn)
			Runtime.getRuntime().exec("pl1.exe",null,new File("C:\\Users\\Zeus\\workspace\\GameOfDots\\"));
		else
			Runtime.getRuntime().exec("pl2.exe",null,new File("C:\\Users\\Zeus\\workspace\\GameOfDots\\"));
		Thread.sleep(2000);
		//read file now
		Board.performRead(currentPlayer.symbol);
		//if(no changes in file) declare other winner
		
	}			//end of play

	public static void main(String[] args) throws InterruptedException, IOException {
		GameManager1 gm = new GameManager1();
		while(b.status==Board.NOT_FINISHED){
			play();
		}
		
		if (b.status==Board.PLAYER1WON) {
			showNow+=(p1.name + " won");
		} else{
			showNow+=(p2.name+" won");
		}
		display.setText(showNow);
	}


//	@Override
//	public void actionPerformed(ActionEvent ae) {
//		int iter=0;
//		for(iter=0;iter<boardSize*boardSize;++iter){
//			if(ae.getSource()==button[iter])
//				break;
//		}
//		if(iter==boardSize*boardSize)
//			iter=-1;
//		int x=iter/boardSize;
//		int y=iter%boardSize;
//
//		boolean done = b.makeAMove(x,y, currentPlayer.symbol);
//		if(!done){
//			showNow="Invalid move by "+currentPlayer.name+'\n';
//		}
//		play();
//
//	}

	public static void changebuttoncolors(int xaxis,int yaxis,char symb,int tX,int tY){
		button[tX*boardSize+tY].setBackground(Color.BLACK);

		if(symb=='1')
			button[xaxis*boardSize+yaxis].setBackground(Color.BLUE);
		else if(symb=='2')
			button[xaxis*boardSize+yaxis].setBackground(Color.YELLOW);

	}


}
