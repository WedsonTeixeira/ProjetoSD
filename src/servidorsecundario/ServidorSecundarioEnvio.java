package servidorsecundario;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * Thread responsavel por tratar cada envio de arquivo
 * para o cliente a partir do servidor secundario */
public class ServidorSecundarioEnvio extends Thread{
	
	ServerSocket serverSocket;

    public ServidorSecundarioEnvio(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    
	public void run() {
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				ServidorSecundarioEnvioThread servidorSecundarioEnvioThread = new ServidorSecundarioEnvioThread(socket);
				servidorSecundarioEnvioThread.start();
			}
			catch(IOException erro) {
				System.err.println(erro);
			}
		}
	}
}