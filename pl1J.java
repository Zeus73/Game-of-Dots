import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class pl1J {

	public static void myMove(int side,char[][]board,int[]movePos){
		// 'U' denotes the player's own position
		// 'X' denotes the position of Opponent
		// n denotes the length of each side of square board
		// '.' denotes a visited cell
		// '?' denotes an unvisited cell
		int row=-1,col=-1;
		for(int i=0;i<side;++i)
			for(int j=0;j<side;++j)
				if(board[i][j]=='U'){
					row=i+1;
					col=j+1;
					movePos[0]=row;
					movePos[1]=col;
					return;
				}
					
		movePos[0]=row;
		movePos[1]=col;
		return;	
	}
	
	public static Scanner s;
	static FileReader in=null;
	static FileWriter out=null;
	public static void main(String[] args) throws IOException {
		int side=0;
		int []movePos={-1,-1};
		try {
			in=new FileReader("curBoard.txt");
			s=new Scanner(in);
			if(s.hasNext())
				side=s.nextInt();
			
			char[][]board=new char[side][side];
			if(s.hasNext())
				s.nextLine();
			for(int i=0;i<side && s.hasNext();++i){
				String tempo=s.nextLine();
				for(int j=0;j<side && j<tempo.length();++j){
					board[i][j]=tempo.charAt(j);
				}
			}
			//call function here
			myMove(side,board,movePos);
			s.close();
		} catch (FileNotFoundException e) {
			// do nothing
		}finally{
			if(in!=null)
				in.close();
		}
		out=new FileWriter("move.txt");
		out.write(String.valueOf(movePos[0])+" ");
		out.write(String.valueOf(movePos[1]));
		out.close();
	}
}
