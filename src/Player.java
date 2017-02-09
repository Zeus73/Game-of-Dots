import java.util.Random;
import java.util.Scanner;

public class Player {
	public static final int CPPLang=1;
	public static final int JAVALang=2;
	static Scanner s = new Scanner(System.in);
	String name;
	int pLang;
	char symbol;
	public Player(String name,int pLang,char symbol) {
		this.name = name;
		this.pLang=pLang;
		this.symbol=symbol;
	}
	
//	public static Player takePlayerInput(int playerNumber,char symb) {
//		System.out.println("Enter player " + playerNumber + " name: ");
////		String name = s.nextLine();
//		String name="aman";
//		System.out.println("Choose ur Language\n1.C++\n2.Java");
////		int choice=s.nextInt();
//		int choice=1;
////		s.nextLine();
//		return new Player(name,choice,symb);
//	}
	
}
