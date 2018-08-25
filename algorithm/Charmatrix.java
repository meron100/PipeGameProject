package algorithm;

import java.io.Serializable;

public class Charmatrix implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private char[][] matrix;

	public Charmatrix(char[][] matrix) {
		super();
		this.matrix = new char[matrix.length][matrix[0].length];
		for(int i=0;i<matrix.length;i++) {
			for(int j=0;j<matrix[0].length;j++) {
				this.matrix[i][j] = matrix[i][j];
			}
		}
	}

	public char[][] getMatrix() {
		return matrix;
	}

	@Override
	public int hashCode() {	
		return this.toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {	
		return this.toString().equals(obj.toString());
	}

	@Override
	public String toString() {	
		String s=new String();
		for(int i=0;i<this.matrix.length;i++)
			s=s.concat(String.valueOf(this.matrix[i]));
		
		
		return s;
	}

}
