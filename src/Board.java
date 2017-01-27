public class Board {
	private char[][]board;
	char player1Char;
	char player2Char;
	int boardSize;
	public static int filled;
	public static int status;
	public static final int PLAYER1WON = 1;
	public static final int PLAYER2WON = 2;
	public static final int NOT_FINISHED = 0;
	public static final int DRAW = 3;
	int p1Pos,p2Pos;
	public Board(int side) {
		board = new char[side][side];
		for (int i = 0; i < side; i++) {
			for(int j = 0; j < side; j++) {
				board[i][j] = ' ';
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
	
	public boolean makeAMove(int nxtRow,int nxtCol,char symbol,GameManager1 gm){
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
		gm.changebuttoncolors(nxtRow, nxtCol, symbol, curRow, curCol);
		return true;
	}	
	
}
