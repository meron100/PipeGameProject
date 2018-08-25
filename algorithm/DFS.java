package algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;


public class DFS<T> extends CommonSearcher<T> {


	private ArrayList<State<T>> visit(Searchable<T> sa,State<T> s,HashSet<State<T>> visited){

		visited.add(s);
		if(sa.IsGoalState(s)) {
			return backTrace(s,sa.getInitialState());
		}
		ArrayList<State<T>> successors=sa.getAllPossibleStates(s);

		for(State<T> state : successors)
		{
			if(!visited.contains(state)&&!this.openList.contains(state)) {
				state.setCameFrom(s);
				this.openList.add(state);
				this.visit(sa, state, visited);
			}
		}

		return null;
	}

	@Override
	public ArrayList<State<T>> search(Searchable<T> s) {
		
		this.openList =new LinkedList<>();
		HashSet<State<T>> visited = new HashSet<>();
		this.openList.add(s.getInitialState());

		while(this.openList.size()>0) {
			State<T> n = this.popOpenList();
			ArrayList<State<T>> sol = this.visit(s, n,visited );
			

			if(sol!=null) {
				return sol;
			}


		}




		return null;
	}


	
}
