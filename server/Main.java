package server;

public class Main {
	public static void main(String[] args) throws Exception {
		SimpleClientHandler ch = new SimpleClientHandler();
		int portNumber = Integer.parseInt("6400");
		int NumOfTrehads = Integer.parseInt("4");
		SimpleServer s = new SimpleServer(portNumber,NumOfTrehads);
		
		s.start(ch);
	
	}

}
