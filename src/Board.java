import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Board {
	private static char[][]board;
	char player1Char;
	char player2Char;
	static int boardSize;
	public static int filled;
	public static int status;
	public static final int PLAYER1WON = 1;
	public static final int PLAYER2WON = 2;
	public static final int NOT_FINISHED = 0;

	static int p1Pos,p2Pos;
	public Board(int side) {
		board = new char[side][side];
		for (int i = 0; i < side; i++) {
			for(int j = 0; j < side; j++) {
				board[i][j] = '?';
			}
		}
		this.player1Char = '1';
		this.player2Char = '2';
		this.boardSize=side;
		p1Pos=0;
		p2Pos=side*side-1;
		status=NOT_FINISHED;
		filled=2;
	}

	public void printBoard() {
		for(int xiter=0;xiter<boardSize;++xiter){
			for(int yiter=0;yiter<boardSize;++yiter){
				System.out.print(board[xiter][yiter]+"|");
			}
			System.out.println();
		}	
	}

	public static boolean makeAMove(int nxtRow,int nxtCol,char symbol){
		char oppo;
		if(symbol=='1')oppo='2';
		else oppo='1';
		boolean ret=true;
		if(nxtRow<0||nxtRow>=boardSize||nxtCol<0||nxtCol>=boardSize)
			ret= false;
		if(ret && board[nxtRow][nxtCol]=='.')
			ret= false;
		int curRow,curCol,playerPos;
		if(symbol=='1')
			playerPos=p1Pos;
		else
			playerPos=p2Pos;
		curRow=playerPos/boardSize;
		curCol=playerPos%boardSize;
		int val1=Math.abs(curRow-nxtRow),val2=Math.abs(curCol-nxtCol);
		if(ret && (val1>1||val2>1||val1+val2==0))
			ret= false;
		if(!ret){
			if(symbol=='2'){
				status=PLAYER1WON;
			}else{
				status=PLAYER2WON;
			}
			return ret;
		}
		if(symbol=='1'){
			p1Pos=nxtRow*boardSize+nxtCol;
		}else{
			p2Pos=nxtRow*boardSize+nxtCol;
		}
		board[curRow][curCol]='.';
		if(board[nxtRow][nxtCol]==oppo){
			if(symbol=='1'){
				status=PLAYER1WON;
			}else{
				status=PLAYER2WON;
			}
		}else{
			++filled;
			if(filled==boardSize*boardSize){
				if(symbol=='1'){
					status=PLAYER1WON;
				}else{
					status=PLAYER2WON;
				}
			}
		}
		board[nxtRow][nxtCol]=symbol;
		GameManager1.changebuttoncolors(nxtRow, nxtCol, symbol, curRow, curCol);
		return true;
	}

	public static void performWrite(char symbol) throws IOException {
		FileWriter out=new FileWriter("curBoard.txt");
		out.write(String.valueOf(boardSize));
		out.write('\n');
		int row1=p1Pos/boardSize,row2=p2Pos/boardSize,col1=p1Pos%boardSize,col2=p2Pos%boardSize;
		if(symbol=='1'){
			board[row1][col1]='U';
			board[row2][col2]='X';
		}else{
			board[row1][col1]='X';
			board[row2][col2]='U';
		}
		for(int i=0;i<boardSize;++i){
			for(int j=0;j<boardSize;++j)
				out.write(board[i][j]);
			out.write('\n');
		}
		board[row1][col1]='1';
		board[row2][col2]='2';
		out.close();
	}	

	public static boolean performRead(char symbol) throws IOException{
		int para1=-1,para2=-1;
		//read here
		FileReader in=null;
		FileWriter out=new FileWriter("move.txt");
		
		try {
			in=new FileReader("move.txt");
			Scanner s=new Scanner(in);
			if(s.hasNext())
				para1=s.nextInt();
			if(s.hasNext())
				para2=s.nextInt();
			out.write("-1 -1");
			s.close();
		} catch (FileNotFoundException e) {
			//do nothing
		}finally{
			if(in!=null)
				in.close();
			out.close();
			return makeAMove(para1, para2, symbol);
		}	
	}
}
