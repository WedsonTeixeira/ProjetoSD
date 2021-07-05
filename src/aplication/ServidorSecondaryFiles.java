package aplication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorSecondaryFiles extends Thread{
	
	ServerSocket serverSocket;

    public ServidorSecondaryFiles(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    
	public void run() {
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				ServidorSecondaryFilesThread servidorSecondaryFilesThread = new ServidorSecondaryFilesThread(socket);
				servidorSecondaryFilesThread.start();
			}
			catch(IOException ex) {
	            System.err.println(ex);
			}
			
		}
		
	}
}