#include<bits/stdc++.h>
using namespace std;

pair<int,int> myMove(vector<vector<char> >&board){
	// 'U' denotes the player's own position
	// 'X' denotes the position of Opponent
	// n denotes the length of each side of square board
	// '.' denotes a visited cell
	// '?' denotes an unvisited cell
	int side=board.size();
	for(int i=0;i<side;++i)
		for(int j=0;j<side;++j)
			if(board[i][j]=='U')
				return make_pair(i+1,j+1);
		
	return make_pair(-1,-1);	
}

int main(){
	
	freopen("curBoard.txt","r",stdin);
	freopen("move.txt","w",stdout);
	int n;
	cin>>n;
	vector<char>in(n,' ');
	vector<vector<char> >board(n,in);
	for(int i=0;i<n;++i)
		for(int j=0;j<n;++j){
			cin>>board[i][j];
		}
	pair<int,int> ans=myMove(board);
	cout<<ans.first<<" "<<ans.second;
	
	fclose(stdin);
	fclose(stdout);
	return 0;
}
