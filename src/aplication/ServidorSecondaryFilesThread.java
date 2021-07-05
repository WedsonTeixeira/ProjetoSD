package aplication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
public class ServidorSecondaryFilesThread extends Thread {
	
	Socket s;

    public ServidorSecondaryFilesThread(Socket s) {
        this.s = s;
    }
    
    public void run() {
    	try {
			BufferedReader mensagem_vinda_cliente = new BufferedReader(new InputStreamReader(s.getInputStream()));
			DataOutputStream resposta_para_cliente = new DataOutputStream(s.getOutputStream());
			
			String nomeArquivo = mensagem_vinda_cliente.readLine();
			System.out.println("Enviando arquivo: " + nomeArquivo + "\n");
			
			resposta_para_cliente.writeBytes("Tome aqui amigão esse arquivo: " + nomeArquivo + '\n');
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		    	
    }
	
}
