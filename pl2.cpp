#include<bits/stdc++.h>
using namespace std;

pair<int,int> myMove(vector<vector<char> >&board){
	// 'U' denotes the player's own position
	// 'X' denotes the position of Opponent
	// n denotes the length of each side of square board
	// '.' denotes a visited cell
	// '?' denotes an unvisited cell
	
	int n=board.size();
	for(int i=n-1;i>=0;--i)
		for(int j=n-1;j>=0;--j)
			if(board[i][j]=='U')
				return make_pair(i-1,j-1);
		
	return make_pair(n,n);	
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
