package aplication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	public static void main(String[] args) throws Exception {
        
       try (ServerSocket welcomeSocket = new ServerSocket(10000)) {
		while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            
            String nomeArquivo = inFromClient.readLine();
            
            System.out.print(nomeArquivo);
            outToClient.writeBytes("Aqui vai uma lista\n");
            
        }
	}
    }
}
