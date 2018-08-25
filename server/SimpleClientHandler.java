package server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.ArrayList;

import algorithm.Charmatrix;
import algorithm.Solution;



public class SimpleClientHandler implements ClientHandler {
	CacheManager cm = SimpleCacheManager.getInstance();
	Solver solver = new SolverAdpter();
	
	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) {
		BufferedReader FromClient = new BufferedReader(new InputStreamReader(inFromClient));//read from user stream
		PrintWriter toClient = new PrintWriter(outToClient,true);//write to user stream

		String line =new String();
		String maze = new String();
		int NumberOfLines=0;

		while(!(line.equals("done"))) {

			try {
				// every line is line in the maze that we get from the user.
				line = FromClient.readLine();
			} catch (IOException e) {e.printStackTrace();}
			if(!(line.equals("done"))) {
				// we concat all the lines to one line. and count the number of line's we get
				maze = maze.concat(line);
				NumberOfLines++;
			}
		}
		
		//make PipeGameBoard represent with char matrix, and normalize the board to Specific template so every Permutation of this level will look the same.
		// if we already solve this level in another Permutation we don't need to solve it again only to "fix" it to the current Permutation of the level.
		PipeGameBoard board = new PipeGameBoard(maze, NumberOfLines);
		
		Solution<Charmatrix> sol;
		if(!cm.Find(board.GetKey())) {
			// in case we don't have the solution for this level we send the level to the solver and save it in the CacheManager
			sol =(Solution<Charmatrix>) solver.solve(board);
			
			cm.save(board.GetKey(), sol);

		}
		// in case we have the solution already saved in the CacheManager , we don't need to solve it again only load it.
		else {
			sol = cm.load(board.GetKey());
		}
		Solution<ArrayList<String>> solFinal =board.solstring(sol);//this method "fix" the level from the generic Permutation of the level to the current Permutation of the level

		for(String row : solFinal.getSol()) {//send the solution to the client.
		
			toClient.println(row);
		}

	}

}







