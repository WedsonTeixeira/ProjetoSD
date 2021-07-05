package aplication;

import java.net.ServerSocket;
import java.net.Socket;

public class ServidorMain {
	
	static int portaServidor = 10000;
	
	public static void main(String[] args) throws Exception {
        
		try (ServerSocket socket_conexao_cliente = new ServerSocket(portaServidor)) {
			while (true) {
				Socket socket = socket_conexao_cliente.accept();
				ServidorMainThread servidorMainThread = new ServidorMainThread(socket);
				servidorMainThread.start();
			}
		}
    }
}