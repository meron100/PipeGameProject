package algorithm;


public class State<T> implements Comparable<State<T>> {

	public T state;
	public State<T> CameFrom;
	private double cost;
	
	public State(T state) {
		super();		
		this.state = state;
		this.cost = 0;
	}

	public State<T> getCameFrom() {
		return CameFrom;
	}

	public void setCameFrom(State<T> cameFrom) {
		CameFrom = cameFrom;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public T getState() {
		return state;
	}

	public double getCost() {
		return cost;
	}

	@Override
	public int hashCode() {
		return this.state.hashCode();
	}
	
	@Override
	public String toString() {
		return this.state.toString();
	}

	@Override
	public boolean equals(Object obj) {		
		return this.state.equals(obj);
	}

	@Override
	public int compareTo(State<T> o) {
		Double c = this.cost -o.cost;
		return c.intValue();
	}


}
