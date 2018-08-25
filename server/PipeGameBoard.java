package server;


import java.util.ArrayList;

import algorithm.Charmatrix;
import algorithm.Solution;



public class PipeGameBoard {

	String key;
	char[][] maze;
	int row;
	int col;
	int [][] Normalize;

	
 	public PipeGameBoard(String mazeR,int rows) {
		super();
		
		key = mazeR;
		this.row = rows;
		this.col = key.length()/rows;
		this.Normalize = new int[rows][this.col];
		
		
		this.maze = new char[this.row][this.col];
		int k=-1;
	
		//we normalize the level to generic Permutation and saved the number of rotate we did 
		//so we later "fix" this to send the correct Permutation to the user
		
		for(int i=0;i<row;i++) {
			
			for(int j =0;j<col;j++) {
				k++;
				
				if(key.charAt(k)=='L') {
					this.maze[i][j] = 'F';
					this.Normalize[i][j] = 1;
				}
				else if(key.charAt(k)=='J'){
					this.maze[i][j] = 'F';
					this.Normalize[i][j] = 2;
				}
				else if(key.charAt(k)=='7'){
					this.maze[i][j] = 'F';
					this.Normalize[i][j] = 3;
				}
				else if(key.charAt(k)=='F'){
					this.maze[i][j] = 'F';
					this.Normalize[i][j] = 0;
				}
				else if(key.charAt(k)=='|'){
					this.maze[i][j] = '-';
					this.Normalize[i][j] = 1;
				}else if(key.charAt(k)=='-'){
					this.maze[i][j] = '-';
					this.Normalize[i][j] = 0;
				}else {
					this.maze[i][j] = key.charAt(k);
					this.Normalize[i][j] = 0;
				}
			}
			
		}
		// making generic key for this level
		key=key.replace('-', 'Y');
		key=key.replace('|', 'Y');
		key=key.replaceAll("7", "F");
		key=key.replaceAll("L", "F");
		key=key.replaceAll("J", "F");

		
		
		
	}

	public Solution<ArrayList<String>> solstring(Solution<Charmatrix> sol){
	
		//this method fix from the generic solution of the level we got to the specific solution we need for the current level Permutation.
		// and making array list of String. this array list of string's is the protocol of send the solution to the client.
		//if we want to tell the user to rotate some tile we send it like this "row,column,NumOfRotate".
		
		Solution<ArrayList<String>> solfinal = new Solution<ArrayList<String>>(new ArrayList<String>());
		
		for(int i=0;i<sol.getSol().getMatrix().length;i++) {
			for(int j=0;j<sol.getSol().getMatrix()[0].length;j++) {
				
				if(sol.getSol().getMatrix()[i][j]=='L') {
					Normalize[i][j] = (Normalize[i][j]+3)%4;
				}
				else if(sol.getSol().getMatrix()[i][j]=='J') {
					Normalize[i][j] = (Normalize[i][j]+2)%4;
				}
				else if(sol.getSol().getMatrix()[i][j]=='7') {
					Normalize[i][j] = (Normalize[i][j]+1)%4;
				}
				else if(sol.getSol().getMatrix()[i][j]=='F') {
					Normalize[i][j] = (Normalize[i][j]+0)%4;
				}
				else if(sol.getSol().getMatrix()[i][j]=='-') {
					Normalize[i][j] = (Normalize[i][j]+0)%2;
				}
				else if(sol.getSol().getMatrix()[i][j]=='|') {
					Normalize[i][j] = (Normalize[i][j]+1)%2;
				}
				if(Normalize[i][j]!=0) {
				//							row		,			column	  , 		NumOfRotate
				String toparse = String.valueOf(i)+","+String.valueOf(j)+","+String.valueOf(Normalize[i][j]);
				
				solfinal.getSol().add(toparse);
				}
			}
			
		}
		solfinal.getSol().add("done");
		
		return solfinal;
		
	}
	
	public char[][] getMaze() {
		return maze.clone();
	}

	public int getRows() {
		return this.row;
	}
	
	public int getCol() {
		return this.col;
	}
	
	public String GetKey() {
		
		return this.key;
	}
	
	
}
