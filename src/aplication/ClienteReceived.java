package aplication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
		    BufferedReader mensagem_vinda_servidor_secondary = new BufferedReader(new InputStreamReader(socketServidorSecondary.getInputStream()));
		  
		    solicitacao_para_servidor_secondary.writeBytes(nomeArquivo+'\n');
		  
		    String lista_secondary = mensagem_vinda_servidor_secondary.readLine();
		    System.out.println(lista_secondary);
		    socketServidorSecondary.close();
		    btnNewButton.setEnabled(true);
		    btnBuscar.setEnabled(true);
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
 
}
