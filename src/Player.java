import java.util.Scanner;

public class Player {
	static Scanner s = new Scanner(System.in);
	String name;
	int pLang;
	char symbol;
	public Player(String name,int pLang,char symbol) {
		this.name = name;
		this.pLang=pLang;
		this.symbol=symbol;
	}
	
	public static Player takePlayerInput(int playerNumber,char symb) {
		System.out.println("Enter player " + playerNumber + " name: ");
		String name = s.nextLine();
		System.out.println("By default language set to JAVA!");
		return new Player(name,1,symb);
	}
	
}
