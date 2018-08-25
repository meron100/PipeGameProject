package algorithm;

import java.io.Serializable;

public class Solution<T> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 248047223500152355L;
	T sol;
	
	public Solution(T sol) {
		super();
		this.sol = sol;
	
	}

	public Solution() {
		super();
	}

	public T getSol() {
		return sol;
	}

	public void setSol(T sol) {
		this.sol = sol;
	}
	
	
}
