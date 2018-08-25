package server;



import java.util.ArrayList;

import algorithm.BFS;
import algorithm.BestFirstSearche;
import algorithm.Charmatrix;
import algorithm.DFS;
import algorithm.HillClimbing;
import algorithm.PipeGameGrader;
import algorithm.PipeGameSearchable;
import algorithm.Searcher;
import algorithm.Solution;
import algorithm.State;
import algorithm.StateGrader;



public class SolverAdpter implements Solver<Solution<Charmatrix>,PipeGameBoard>{

	Searcher<Charmatrix> S;

	@Override
	public Solution<Charmatrix> solve(PipeGameBoard P) {
		Charmatrix charmatrix = new Charmatrix(P.maze);

		State<Charmatrix> pipeState = new State<Charmatrix>(charmatrix);
		pipeState.setCameFrom(null);

		PipeGameSearchable pipegamesearchable = new PipeGameSearchable(pipeState, P.row,P.col);

		Searcher<Charmatrix> bestFirstSearcher = new BestFirstSearche<>();

		// the algorithm will return array of state's from the solution back to the first state.
		ArrayList<State<Charmatrix>> states = bestFirstSearcher.search(pipegamesearchable);

		// we need only the final state of the game, this final state is the solution of the game.
		Solution<Charmatrix> sol = new Solution<>(states.get(0).state);


		return sol;
	}

}
