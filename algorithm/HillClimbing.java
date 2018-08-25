package algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

public class HillClimbing<T>  extends CommonSearcher<T> {
	long timeToRun;
	StateGrader<T> grader;
	
	public HillClimbing(long timeToRun, StateGrader<T> grader) {
		super();
		this.timeToRun = timeToRun;
		this.grader = grader;
	}

	@Override
	public ArrayList<State<T>> search (Searchable<T> s){
		long time0 = System.currentTimeMillis();

		this.openList = new LinkedList<State<T>>();
		this.openList.add(s.getInitialState());
		HashSet<State<T>> closedSet=new HashSet<State<T>>();


		//Loop until the goal state is achieved or no more operators can be applied on the current state:

		while((System.currentTimeMillis() - time0 < timeToRun)&&openList.size()>0){
		
			State<T> next= this.popOpenList();
			closedSet.add(next);
			if(s.IsGoalState(next)) {
				return backTrace(next, s.getInitialState());
			}


			ArrayList<State<T>> successors=s.getAllPossibleStates(next);
			double rand = Math.random();

			if( rand<0.7){
				double grade = 0 ;
				for(State<T> state: successors){
					state.setCost(grader.Grade(state));
					double g =state.getCost();
					state.setCameFrom(next);
					if(g>grade){
						grade = g;
						next = state;
						//add this step to the solution
						if(!closedSet.contains(state) && !openList.contains(state)){
							this.openList.add(state);
						}
					}
					else if (!closedSet.contains(state) && !openList.contains(state)) {
						this.openList.add(state);
					}
				}
			} else {
				next = successors.get(new Random().nextInt(successors.size()));
			}

			


		}//end of while

		return null;

	}



}