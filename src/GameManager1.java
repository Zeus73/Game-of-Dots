import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class GameManager1 extends JFrame{
	public static String MYDIRECTORY=null;
	public static int pl1Lang=1,pl2Lang=1;
	static JTextField instPl1 = new JTextField("Player 1",20);
	static JTextField instPl2 = new JTextField("Player 2",20);
	static String pl1Name=null, pl2Name=null;
	static public JButton proceedButton=new JButton("Battle!");
	static public JButton cpp1,cpp2;
	public static JButton jav1;
	static public JButton jav2;

	private static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	class yy implements Runnable {
		public void run() {
			while(b.status==Board.NOT_FINISHED){
				try {
					play();
				} catch (InterruptedException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			if (b.status==Board.PLAYER1WON) {
				showNow+=(p1.name + " won");
			} else{
				showNow+=(p2.name+" won");
			}
			display.setText(showNow);
		}
	}

	static Board b;
	static Player p1;
	static Player p2;
	int wait=0;
	static Scanner s = new Scanner(System.in);
	private static final long serialVersionUID = 1L;
	private static final int boardSize=getRandomNumberInRange(5, 12);
	JPanel[] row = new JPanel[boardSize+1+5];
	static JButton[] button = new JButton[boardSize*boardSize];
	String buttonString = "";
	int[] dimW = {300,45,100,90};
	int[] dimH = {35, 40};
	Dimension displayDimension = new Dimension(dimW[0], dimH[0]);
	Dimension regularDimension = new Dimension(dimW[1], dimH[1]);
	static JTextArea display = new JTextArea(1,20);
	Font font = new Font("Times new Roman", Font.BOLD, 14);

	public GameManager1() throws InterruptedException  {

		super("Game of Dots");
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<s.length();++i){
			sb.append(s.charAt(i));
			if(s.charAt(i)=='\\')
				sb.append(s.charAt(i));
		}
		sb.append("\\\\");
		MYDIRECTORY=sb.toString();

		instPl1.setFont(font);
		instPl1.setPreferredSize(displayDimension);
		instPl1.setFont(font);
		instPl1.setPreferredSize(displayDimension);
		cpp1=new JButton("C++");
		cpp2=new JButton("C++");
		jav1=new JButton("Java");
		jav2=new JButton("Java");

		b  = new Board(boardSize);

		setDesign();
		setSize(550, 550);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		GridLayout grid = new GridLayout(boardSize+1+5,boardSize);
		setLayout(grid);

		FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
		FlowLayout f2 = new FlowLayout(FlowLayout.CENTER,1,1);
		for(int i = 0; i < boardSize+1+5; i++) {
			row[i] = new JPanel();
		}
		for(int i=0;i<5;++i)
			row[i].setLayout(f1);
		row[0+5].setLayout(f1);

		for(int i = 1+5; i < boardSize+1+5; i++)
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

		row[0].add(instPl1);
		row[1].add(cpp1);
		row[1].add(jav1);
		row[2].add(instPl2);
		row[3].add(cpp2);
		row[3].add(jav2);
		row[4].add(proceedButton);


		for(int i=0;i<5;++i)
			add(row[i]);

		row[0+5].add(display);
		add(row[0+5]);

		for(int j=0;j<boardSize;++j){
			for(int i = 0; i < boardSize; i++)
				row[j+1+5].add(button[j*boardSize+i]);
			add(row[j+1+5]);
		}

		setVisible(true);
		button[0].setBackground(Color.BLUE);
		button[boardSize*boardSize-1].setBackground(Color.YELLOW);
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
		if(player1Turn){
			if(p1.pLang==Player.CPPLang)
				Runtime.getRuntime().exec("pl1C.exe",null,new File(MYDIRECTORY));
			else
				Runtime.getRuntime().exec("cmd.exe /c java pl1J",null,new File(MYDIRECTORY));

		}

		else{
			if(p2.pLang==Player.CPPLang)
				Runtime.getRuntime().exec("pl2C.exe",null,new File(MYDIRECTORY));
			else
				Runtime.getRuntime().exec("cmd.exe /c java pl2J",null,new File(MYDIRECTORY));

		}

		Thread.sleep(3000);
		//read file now
		Board.performRead(currentPlayer.symbol);
		//if(no changes in file) declare other winner

	}			//end of play

	public static void main(String[] args) throws InterruptedException, IOException {
		GameManager1 gm = new GameManager1();
		yy obj=gm.new yy();
		Thread t=new Thread(obj);

		jav1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pl1Lang=2;	
			}
		});
		jav2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pl2Lang=2;
			}
		});
		proceedButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pl1Name=instPl1.getText();
				pl2Name=instPl2.getText();
				GameManager1.p1 = new Player(pl1Name,pl1Lang,'1'); 
				GameManager1.p2 = new Player(pl2Name,pl2Lang,'2');

				try {
					Process p;
					if(GameManager1.p1.pLang==Player.CPPLang){
						p=Runtime.getRuntime().exec("cmd.exe /c g++ -o pl1C pl1C.cpp",null,new File(MYDIRECTORY));
					}else{
						p=Runtime.getRuntime().exec("cmd.exe /c javac pl1J.java",null,new File(MYDIRECTORY));
					}
					p.waitFor();
					if(GameManager1.p2.pLang==Player.CPPLang){
						p=Runtime.getRuntime().exec("cmd.exe /c g++ -o pl2C pl2C.cpp",null,new File(MYDIRECTORY));
					}else{
						p=Runtime.getRuntime().exec("cmd.exe /c javac pl2J.java",null,new File(MYDIRECTORY));
					}
					p.waitFor();
					t.start();
				} catch (IOException | InterruptedException e1) {
					e1.printStackTrace();
				}				
			}
		});

	}
	public static void changebuttoncolors(int xaxis,int yaxis,char symb,int tX,int tY){
		button[tX*boardSize+tY].setBackground(Color.BLACK);

		if(symb=='1')
			button[xaxis*boardSize+yaxis].setBackground(Color.BLUE);
		else if(symb=='2')
			button[xaxis*boardSize+yaxis].setBackground(Color.YELLOW);

	}




}