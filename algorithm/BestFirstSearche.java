package algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

import algorithm.Searchable;

public class BestFirstSearche<T> extends CommonSearcher<T> {

	@Override
	public ArrayList<State<T>> search(Searchable<T> s) {
		
		
		this.openList = new PriorityQueue<>();
		this.openList.add(s.getInitialState());

		HashSet<State<T>> closedSet=new HashSet<State<T>>();
		
		while(openList.size()>0){
			State<T> n=this.popOpenList();// dequeue
			closedSet.add(n);
			
			if(s.IsGoalState(n)) {
				return  backTrace(n, s.getInitialState());
			}
			// private method, back traces through the parents

			ArrayList<State<T>> successors=s.getAllPossibleStates(n); //however it is implemented

			for(State<T> state : successors){

				if(!closedSet.contains(state) && !openList.contains(state)){
					state.setCameFrom(n);
					this.openList.add(state);
				} 
				else{
					if(n.getCost()>state.getCost()) {//Otherwise, if this new path is better than previous one
						if(!openList.contains(state)) {//i. If it is not in OPEN add it to OPEN.
							state.setCameFrom(n);
						
							this.openList.add(state);
						}
						else {//ii. Otherwise, adjust its priority in OPEN
							openList.remove(state);
							openList.add(state);
						}
					}
				}//end of else

			}//end of for
		
		}//end of while
	
		return null;
	}


	
}
