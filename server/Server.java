package server;


public interface Server {
	public void start(ClientHandler ch);
	public void runServer(ClientHandler ch) throws Exception;
	public void stop();
	
}
