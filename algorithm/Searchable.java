package algorithm;

import java.util.ArrayList;


public interface Searchable<T> {
	State<T> getInitialState();
	boolean IsGoalState(State<T> s);
	ArrayList<State<T>> getAllPossibleStates(State<T> s);
}
