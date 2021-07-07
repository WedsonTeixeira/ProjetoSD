package aplication;


import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import javax.swing.JButton;

public class ClienteReceived extends Thread{
	
	String ipMaquina;
    int port;
    String nomeArquivo;
    JButton btnNewButton;
	JButton btnBuscar;
    
    public ClienteReceived(String ipMaquina, int port, String nomeArquivo,JButton btnNewButton, JButton btnBuscar) {
    	this.ipMaquina = ipMaquina;
    	this.port = port;
    	this.nomeArquivo = nomeArquivo;
    	this.btnNewButton = btnNewButton;	
		this.btnBuscar = btnBuscar;	
    }

	public void run() {
		
		try {
			Socket socketServidorSecondary = new Socket(ipMaquina, port);
			DataOutputStream solicitacao_para_servidor_secondary = new DataOutputStream(socketServidorSecondary.getOutputStream());
			InputStream socketIn = socketServidorSecondary.getInputStream();
		  
		    solicitacao_para_servidor_secondary.writeBytes(nomeArquivo+'\n');
		  
		    FileOutputStream fileOut = new FileOutputStream("Recebido_"+nomeArquivo);

			byte[] cbuffer = new byte[2048];
			int bytesRead;
			
			while ((bytesRead = socketIn.read(cbuffer)) != -1) {
				fileOut.write(cbuffer, 0, bytesRead);
			}
			fileOut.close();
			socketServidorSecondary.close();
			
			btnNewButton.setEnabled(true);
			btnBuscar.setEnabled(true);
			
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
 
}
