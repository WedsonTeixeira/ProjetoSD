package aplication;

import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	
	public static void main(String[] args) throws Exception {
        
		try (ServerSocket socket_conexao_cliente = new ServerSocket(10000)) {
			while (true) {
				Socket socket = socket_conexao_cliente.accept();
				ServidorTransfer servidorTransfer = new ServidorTransfer(socket);
				servidorTransfer.start();
			}
		}
    }
}