package servidorprincipal;

import java.net.ServerSocket;
import java.net.Socket;

/*
 * Classe para iniciar o servidor principal*/
public class ServidorPrincipal {
	
	static int portaServidor = 10000;
	
	public static void main(String[] args) throws Exception {
		
		System.out.println("iniciando o servidor principal...");
		
		try (ServerSocket serverSocket = new ServerSocket(portaServidor)) {
			while (true) {
				
				Socket socket = serverSocket.accept();
				ServidorPrincipalThread servidorPrincipalThread = new ServidorPrincipalThread(socket);
				servidorPrincipalThread.start();
			}
		}
    }
}