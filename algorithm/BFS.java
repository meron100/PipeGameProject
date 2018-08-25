package algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;


public class BFS<T> extends CommonSearcher<T> {

	@Override
	public ArrayList<State<T>> search(Searchable<T> s) {
		
		this.openList= new LinkedList<State<T>>();

		this.openList.add(s.getInitialState());

		HashSet<State<T>> closedSet=new HashSet<State<T>>();

		while(openList.size()>0){
	
			State<T> n=this.popOpenList();// dequeue
			closedSet.add(n);

			if(s.IsGoalState(n)) {
				return backTrace(n, s.getInitialState());
			}
			// private method, back traces through the parents

			ArrayList<State<T>> successors=s.getAllPossibleStates(n); 

			for(State<T> state: successors){

				if(!closedSet.contains(state) && !openList.contains(state)){
					state.setCameFrom(n);
					this.openList.add(state);
				} 

			}

		}

		return null;



	}

	
}
