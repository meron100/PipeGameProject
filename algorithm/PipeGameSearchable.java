package algorithm;

import java.util.ArrayList;
import java.util.HashSet;




public class PipeGameSearchable implements Searchable<Charmatrix> {

	public State<Charmatrix> maze;
	final public State<Charmatrix> InitialState;
	private String SPosition;
	private int rows;
	private int col;
	public ArrayList<State<Charmatrix>> AllPossibleState;
	private int Steps;
	private boolean[][] visited;
	private HashSet<State<Charmatrix>> States;// this is like state poll so we don't check state's we already see before.

	// ctor
	public PipeGameSearchable(State<Charmatrix> maze, int rows, int col) {
		super();
		this.maze = maze;
		this.rows = rows;
		this.col = col;
		this.SPosition = getS();
		this.InitialState = new State<Charmatrix>(new Charmatrix(maze.state.getMatrix()));
		this.setSteps((this.rows*this.col)+10);
		this.InitialState.setCost(Steps);
		this.visited =new boolean[this.rows][this.col];
		this.AllPossibleState = new ArrayList<>();
		this.States = new  HashSet<State<Charmatrix>>();
		this.States.add(maze);
	}

	//get's method
	private int getRows() {
		return rows;
	}

	private int getCol() {
		return col;
	}

	@Override
	public State<Charmatrix> getInitialState() {

		return this.InitialState;
	}

	private String getS() {//return the start position
		for (int i=0;i<rows;i++) {
			for(int j=0;j<col;j++) {
				if(this.maze.state.getMatrix()[i][j]=='s') {
					String s = Integer.toString(i)+","+Integer.toString(j);

					return s;
				}
			}

		}
		return null;

	}

	// this method return all the possible state's that have Chance to be the goal state.
	@Override
	public ArrayList<State<Charmatrix>> getAllPossibleStates(State<Charmatrix> CurrentState) {
		this.AllPossibleState = new ArrayList<>();
		Charmatrix CharMatrixClone = new Charmatrix(CurrentState.state.getMatrix());
		int NumOfRotation =0;
		State<Charmatrix> NewState = new State<Charmatrix>(CharMatrixClone);


		for (int i=0;i<this.rows;i++) {
			for(int j=0;j<this.col;j++) {

				if(CharMatrixClone.getMatrix()[i][j]!='s'&&CharMatrixClone.getMatrix()[i][j]!='g'&&CharMatrixClone.getMatrix()[i][j]!=' ') {// rotate only tile that can be rotate 
					if(CharMatrixClone.getMatrix()[i][j] =='-'||CharMatrixClone.getMatrix()[i][j] =='|') {// this kind of tile can be rotate twice
						NumOfRotation=2;
					}
					else {// other kind of tiles have 4 possible shapes.
						NumOfRotation=4;
					}
					for(int k=0;k<NumOfRotation;k++) {// rotate every tile the number of possible Rotation
						CharMatrixClone = new Charmatrix(NewState.state.getMatrix());// making deep copy of the char matrix
						CharMatrixClone.getMatrix()[i][j] = change(CharMatrixClone.getMatrix()[i][j]);//rotate one tile clockwise
						NewState = new State<Charmatrix>(CharMatrixClone);
						if(!States.contains(NewState)) {//check in the state poll if we saw this state before
							States.add(NewState);// add this new state to the state poll. so in the Future we won't check this state again.
							if(this.AccessEnd(NewState, i, j)) {// check if this new state have connection from the tile we rotate to the end point. if not this state can't be goal state 
								this.AllPossibleState.add(NewState);	// add this new state to array list of states

							}
						}
					}
				}
			}
		}


		return this.AllPossibleState;
	}

	public int getSteps() {
		return Steps;
	}

	//set'rs
	private void setSteps(int Steps) {
		this.Steps = Steps;
	}

	//this method check if we have path from the start point ('s') to the end point ('g'). use the recursive method path.
	public boolean IsGoalState(State<Charmatrix> state) {

		int Srow = Integer.parseInt(SPosition.split(",")[0]);//start Row
		int Scol = Integer.parseInt(SPosition.split(",")[1]);//start column


		if(!(Out(Srow,Scol+1))) { //going right is possible and we have connection to the right tile.
			if(state.state.getMatrix()[Srow][Scol+1]=='-'||state.state.getMatrix()[Srow][Scol+1]=='J'||state.state.getMatrix()[Srow][Scol+1]=='7'||state.state.getMatrix()[Srow][Scol+1]=='g') {
				this.visited =new boolean[this.rows][this.col];
				if(path(state, Srow, Scol+1,"left",this.getSteps())) {
					return true;
				}
			}	
		}if(!(Out(Srow+1,Scol))) {  //going down is possible and we have connection to the down tile.
			if(state.state.getMatrix()[Srow+1][Scol]=='|'||state.state.getMatrix()[Srow+1][Scol]=='J'||state.state.getMatrix()[Srow+1][Scol]=='L'||state.state.getMatrix()[Srow+1][Scol]=='g') {
				this.visited =new boolean[this.rows][this.col];
				if(path(state, Srow+1, Scol,"up",this.getSteps())) {
					return true;
				}
			}	
		}if(!(Out(Srow,Scol-1))) {  //going left is possible and we have connection to the left tile.
			if(state.state.getMatrix()[Srow][Scol-1]=='-'||state.state.getMatrix()[Srow][Scol-1]=='L'||state.state.getMatrix()[Srow][Scol-1]=='F'||state.state.getMatrix()[Srow][Scol-1]=='g') {
				this.visited =new boolean[this.rows][this.col];
				if(path(state, Srow, Scol-1,"right",this.getSteps())) {
					return true;
				}
			}	
		}if(!(Out(Srow-1,Scol))) {  //going up is possible and we have connection to the up tile.
			if(state.state.getMatrix()[Srow-1][Scol]=='|'||state.state.getMatrix()[Srow-1][Scol]=='F'||state.state.getMatrix()[Srow-1][Scol]=='7'||state.state.getMatrix()[Srow-1][Scol]=='g') {
				this.visited =new boolean[this.rows][this.col];
				if(path(state, Srow-1, Scol,"down",this.getSteps())) {
					return true;
				}
			}	
		}

		return false;// start tile is not connected at all



	}

	//this is recursive method that try to get to the end of the maze who represent in the char 'g'. 
	//if we have success we set the cost of the state to be the number of tile's we move in the maze.
	private boolean path(State<Charmatrix> State,int CRow,int CCol,String move,int Step) {

		if(Step==0) {// if we are in circle the step will get to zero and return false for this state.
			return false;
		}
		if(!Out(CRow,CCol)) {// check if we don't try to get out of boundary in the char matrix.
			if(State.state.getMatrix()[CRow][CCol]=='g') {// we get to the end of the maze we return true and set the cost of the state to be the number of steps we did.
				State.setCost(Step-1);
				return true;
			}

			if(this.checkcircle(visited, State, CRow, CCol)) {// Check if we are in circle around the start tile or the end tile
				return false;
			}

			this.visited[CRow][CCol]=true;// boolean matrix said we visited this tile before.

			if(move.equals("right")) {//came from the right tile
				if(State.state.getMatrix()[CRow][CCol]=='-') {
					if(path(State, CRow, CCol-1,"right",Step-1)) {//came from right tile and Left move is possible 
						return true;
					}
				}
				if(State.state.getMatrix()[CRow][CCol]=='L') {
					if(path(State, CRow-1, CCol,"down",Step-1)) {//came from right tile and Up move is possible 
						return true;
					}
				}
				if(State.state.getMatrix()[CRow][CCol]=='F') {
					if(path(State, CRow+1, CCol,"up",Step-1)) {//came from right tile and Down move is possible 
						return true;
					}
				}
			}
			if(move.equals("down")) {//came from down tile
				if(State.state.getMatrix()[CRow][CCol]=='|') {
					if(path(State, CRow-1, CCol,"down",Step-1)) {//came from Down tile and Up move is possible 
						return true;
					}
				}
				if(State.state.getMatrix()[CRow][CCol]=='F') {
					if(path(State, CRow, CCol+1,"left",Step-1)) {//came from Down tile and Right move is possible 
						return true;
					}
				}
				if(State.state.getMatrix()[CRow][CCol]=='7') {
					if(path(State, CRow, CCol-1,"right",Step-1)) {//came from Down tile and Left move is possible 
						return true;
					}
				}
			}
			if(move.equals("left")) {//came from left tile
				if(State.state.getMatrix()[CRow][CCol]=='-') {//came from Left tile and Right move is possible 
					if(path(State, CRow, CCol+1,"left",Step-1)) {
						return true;
					}
				}
				if(State.state.getMatrix()[CRow][CCol]=='7') {//came from Left tile and Down move is possible 
					if(path(State, CRow+1, CCol,"up",Step-1)) {
						return true;
					}
				}
				if(State.state.getMatrix()[CRow][CCol]=='J') {//came from Left tile and Up move is possible 
					if(path(State, CRow-1, CCol,"down",Step-1)) {
						return true;
					}
				}
			}
			if(move.equals("up")) {//came from up tile
				if(State.state.getMatrix()[CRow][CCol]=='|') {//came from Up tile and Down move is possible 
					if(path(State, CRow+1, CCol,"up",Step-1)) {
						return true;
					}
				}
				if(State.state.getMatrix()[CRow][CCol]=='L') {//came from Up tile and Right move is possible 
					if(path(State, CRow, CCol+1,"left",Step-1)) {
						return true;
					}
				}
				if(State.state.getMatrix()[CRow][CCol]=='J') {//came from Up tile and Left move is possible 
					if(path(State, CRow, CCol-1,"right",Step-1)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	//this method check if around the tile we check there is circle. if we do have circle we will say this state is not have chance to be the goal state.
	private boolean checkcircle(boolean[][] visited,State<Charmatrix> maze,int CRow,int CCol) {
		int counter=0;

		if(!Out(CRow-1,CCol)) {
			if(visited[CRow-1][CCol]) {
				if(maze.state.getMatrix()[CRow-1][CCol]=='|'||maze.state.getMatrix()[CRow-1][CCol]=='F'||maze.state.getMatrix()[CRow-1][CCol]=='7') {
					counter++;
				}
			}
		}
		if(!Out(CRow+1,CCol)) {
			if(visited[CRow+1][CCol]) {
				if(maze.state.getMatrix()[CRow+1][CCol]=='|'||maze.state.getMatrix()[CRow+1][CCol]=='J'||maze.state.getMatrix()[CRow+1][CCol]=='L') {
					counter++;
				}
			}
		}
		if(!Out(CRow,CCol-1)) {
			if(visited[CRow][CCol-1]) {
				if(maze.state.getMatrix()[CRow][CCol-1]=='-'||maze.state.getMatrix()[CRow][CCol-1]=='F'||maze.state.getMatrix()[CRow][CCol-1]=='L') {
					counter++;
				}
			}
		}
		if(!Out(CRow,CCol+1)) {
			if(visited[CRow][CCol+1]) {
				if(maze.state.getMatrix()[CRow][CCol+1]=='-'||maze.state.getMatrix()[CRow][CCol+1]=='7'||maze.state.getMatrix()[CRow][CCol+1]=='J') {
					counter++;
				};
			}
		}
		if(counter>1) {
			return true;
		}
		return false;
	}

	//this method check if the move we want to do is out of boundary of the char matrix
	private boolean Out(int checkRow,int chcekCol) {
		if(checkRow>=rows) 
			return true;

		else if(checkRow<0)
			return true;

		else if(chcekCol>=col) 
			return true;

		else if(chcekCol<0) 
			return true;

		else 
			return false;
	}

	//this method change the tile in Clockwise
	private char change(char ch) {

		if(ch=='L') {
			return 'F';
		}
		else if(ch=='J'){
			return 'L';
		}
		else if(ch=='7'){
			return 'J';
		}
		else if(ch=='F'){
			return '7';
		}
		else if(ch=='|'){
			return '-';
		}
		else if(ch=='-'){
			return '|';
		}
		else 
			return ch;

	}

	// this method use the path method to tell if from the current tile we have connection to the end point('g').
	private boolean AccessEnd(State<Charmatrix> State,int CRow,int CCol) {

		if(Out(CRow,CCol)) {
			return false;
		}

		if(State.state.getMatrix()[CRow][CCol]=='L') {
			if(!Out(CRow-1,CCol)) {
				this.visited =new boolean[this.rows][this.col];
				if(path(State,CRow-1,CCol,"down",this.getSteps())) {// try to reach the end point from upper tile.
					return true;
				}
			}
			if(!Out(CRow,CCol+1)) {
				this.visited =new boolean[this.rows][this.col];
				if(path(State,CRow,CCol+1,"left",this.getSteps())) {// try to reach the end point from the right tile.
					return true;
				}
			}
		}
		if(State.state.getMatrix()[CRow][CCol]=='J') {
			if(!Out(CRow-1,CCol)) {
				this.visited =new boolean[this.rows][this.col];
				if(path(State,CRow-1,CCol,"down",this.getSteps())) {// try to reach the end point from upper tile.
					return true;
				}
			}
			if(!Out(CRow,CCol-1)) {
				this.visited =new boolean[this.rows][this.col];
				if(path(State,CRow,CCol-1,"right",this.getSteps())) {// try to reach the end point from the left tile.
					return true;
				}
			}
		}
		if(State.state.getMatrix()[CRow][CCol]=='7') {
			if(!Out(CRow+1,CCol)) {
				this.visited =new boolean[this.rows][this.col];
				if(path(State,CRow+1,CCol,"up",this.getSteps())) {// try to reach the end point from the down tile.
					return true;
				}
			}
			if(!Out(CRow,CCol-1)) {
				this.visited =new boolean[this.rows][this.col];
				if(path(State,CRow,CCol-1,"right",this.getSteps())) {// try to reach the end point from the left tile.
					return true;
				}
			}
		}
		if(State.state.getMatrix()[CRow][CCol]=='F') {
			if(!Out(CRow+1,CCol)) {
				this.visited =new boolean[this.rows][this.col];
				if(path(State,CRow+1,CCol,"up",this.getSteps())) {// try to reach the end point from the down tile.
					return true;
				}
			}
			if(!Out(CRow,CCol+1)) {
				this.visited =new boolean[this.rows][this.col];
				if(path(State,CRow,CCol+1,"left",this.getSteps())) {// try to reach the end point from the right tile.
					return true;
				}
			}
		}
		if(State.state.getMatrix()[CRow][CCol]=='-') {
			if(!Out(CRow,CCol+1)) {
				this.visited =new boolean[this.rows][this.col];
				if(path(State,CRow,CCol+1,"left",this.getSteps())) {// try to reach the end point from the right tile.
					return true;
				}
			}
			if(!Out(CRow,CCol-1)) {
				this.visited =new boolean[this.rows][this.col];
				if(path(State,CRow,CCol-1,"right",this.getSteps())) {// try to reach the end point from the left tile.
					return true;
				}
			}
		}
		if(State.state.getMatrix()[CRow][CCol]=='|') {
			if(!Out(CRow-1,CCol)) {
				this.visited =new boolean[this.rows][this.col];
				if(path(State,CRow-1,CCol,"down",this.getSteps())) {// try to reach the end point from the upper tile.
					return true;
				}
			}
			if(!Out(CRow+1,CCol)) {
				this.visited =new boolean[this.rows][this.col];
				if(path(State,CRow+1,CCol,"up",this.getSteps())) {// try to reach the end point from the down tile.
					return true;
				}
			}
		}

		return false;// return false in case we didn't get to the end point from the current tile.
	}

}
