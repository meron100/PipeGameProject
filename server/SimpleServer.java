package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;




public class SimpleServer implements Server {
	protected int port;
	private int NumOfThread;
	protected volatile boolean stop;
	private ExecutorService ThreadPool;
	
	
	public SimpleServer(int port,int NumOfThread) {
		this.port = port;
		this.NumOfThread = NumOfThread;
		ThreadPool = Executors.newFixedThreadPool(this.NumOfThread);
		
	}
	public void start(ClientHandler ch){
		new Thread(()->{
			try {
				runServer(ch);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				
			}
		}).start(); 
		}
	@Override
	public void runServer(ClientHandler ch)throws Exception {

		ServerSocket server = new ServerSocket(port);
		server.setSoTimeout(1000);


		while (!stop) {

			try {

				Socket aClient = server.accept();//blocking call make queue

				ThreadPool.execute(() -> {
					try {

						Socket OneClient = aClient;
						ch.handleClient(OneClient.getInputStream(), OneClient.getOutputStream());//calling the client handler
						OneClient.getInputStream().close();
						OneClient.close();
					} catch (IOException e) {
						e.printStackTrace();
					}

				});

			} catch (SocketTimeoutException e) {/*System.out.println(e.getMessage());*/}
		}
		server.close();
	}

	@Override
	public void stop() {// this method will stop the while loop and stop the server.
		ThreadPool.shutdownNow();
		this.stop = true;
		
	}
	

	
	
	
}
