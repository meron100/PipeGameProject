package server;


import algorithm.Charmatrix;
import algorithm.Solution;

public interface Solver<solution,problem> {

	solution solve(problem p);
	
}
