package algorithm;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

import algorithm.Searcher;
import algorithm.State;

public abstract class CommonSearcher<T> implements Searcher<T> {
	protected Queue<State<T>> openList;
	private int evaluatedNodes;
	
	public CommonSearcher() {
		openList=new PriorityQueue<State<T>>();
		evaluatedNodes=0;
	}

	protected State<T> popOpenList() {
		evaluatedNodes++;
		return openList.poll();
	}

	@Override
	public int getNumberOfNodesEvaluated() {
		return evaluatedNodes;
	}
	
	protected ArrayList<State<T>> backTrace(State<T> goalState, State<T> initialState) {
		//this method backtrace all the way from the goalstate to the initialState
		ArrayList<State<T>> sol2 = new ArrayList<State<T>>();
		
		while(!goalState.equals(initialState)) {	
			sol2.add(goalState);
			goalState = goalState.CameFrom;
		}
		
		sol2.add(initialState);
		return sol2;

	}
}

