package aplication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JTable;

public class ClienteSend extends Thread{
	
	static String ipServidor = "127.0.0.1";
	static int portaServidor = 10000;
	String nomeArquivo;
	JTable table;
	JButton btnNewButton;
	JButton btnBuscar;
	
	public ClienteSend(String nomeArquivo, JTable table, JButton btnNewButton, JButton btnBuscar) {
		this.nomeArquivo = nomeArquivo;	
		this.table = table;	
		this.btnNewButton = btnNewButton;	
		this.btnBuscar = btnBuscar;	
    }
	
	public void run() {
	
		try {
			Socket socketServidorMain = new Socket(ipServidor, portaServidor);
			DataOutputStream solicitacao_para_servidor = new DataOutputStream(socketServidorMain.getOutputStream());
		    BufferedReader mensagem_vinda_servidor = new BufferedReader(new InputStreamReader(socketServidorMain.getInputStream()));
		    
			solicitacao_para_servidor.writeBytes(nomeArquivo+'\n');
		    
		    String lista_servers_arquivo = mensagem_vinda_servidor.readLine();
		 
		    socketServidorMain.close();
		    
		    lista_servers_arquivo = lista_servers_arquivo.replace("[", "");
	        lista_servers_arquivo = lista_servers_arquivo.replace("]", "");
	        lista_servers_arquivo = lista_servers_arquivo.replace(" ", "");
	        
	        ServidorTabela modeloTabela = new ServidorTabela();
	        
	        if(!lista_servers_arquivo.isEmpty()) {
	        	ArrayList<String> myList = new ArrayList<String>(Arrays.asList(lista_servers_arquivo.split(",")));
	        	
	    		for (int index = 0; index < myList.size(); index++) {
	    			
	    			String aux = myList.get(index).toString();
	    			
	    			ServidorTabelaInfo servidorTabelaInfo = new ServidorTabelaInfo();
	    			
	    			servidorTabelaInfo.nomeMaquina = "Maquina";
	    			servidorTabelaInfo.ipMaquina = aux.split(":")[0];
	    			servidorTabelaInfo.port = Integer.parseInt(aux.split(":")[1]);

	    			modeloTabela.addRow(servidorTabelaInfo);
	    		}
	        }
	        
			table.setModel(modeloTabela);
			btnNewButton.setEnabled(true);
			btnBuscar.setEnabled(true);
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
} 