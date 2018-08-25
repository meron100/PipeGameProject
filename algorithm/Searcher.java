package algorithm;

import java.util.ArrayList;

import algorithm.Searchable;

public interface Searcher<T> {
	// the search method
		public ArrayList<State<T>> search(Searchable<T> s);
		// get how many nodes were evaluated by the algorithm
		public int getNumberOfNodesEvaluated();
}
