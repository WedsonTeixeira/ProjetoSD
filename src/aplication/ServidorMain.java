package aplication;

import java.net.ServerSocket;
import java.net.Socket;

public class ServidorMain {
	
	public static void main(String[] args) throws Exception {
        
		try (ServerSocket socket_conexao_cliente = new ServerSocket(10000)) {
			while (true) {
				Socket socket = socket_conexao_cliente.accept();
				ServidorMainThread servidorMainThread = new ServidorMainThread(socket);
				servidorMainThread.start();
			}
		}
    }
}