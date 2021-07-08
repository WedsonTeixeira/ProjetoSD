package cliente;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ThreadBuscar extends Thread{
	String IpServidor = "127.0.0.1";
	int portaServidor = 10000;
	
	JTextField jTextField;
	JTable jTable;
	JButton jButtonBuscar;
	JButton jButtonBaixar;
	
	public ThreadBuscar(JTextField jTextField, JTable jTable, JButton jButtonBuscar, JButton jButtonBaixar) {
		this.jTextField = jTextField;	
		this.jTable = jTable;	
		this.jButtonBuscar = jButtonBuscar;	
		this.jButtonBaixar = jButtonBaixar;	
    }
	
	public void run() {
	
		try {
			Socket socket = new Socket(IpServidor, portaServidor);
						
			DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
		    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    
		    dataOutputStream.writeBytes(jTextField.getText() + '\n');
		    
		    String retornoServidor = bufferedReader.readLine();
		 
		    socket.close();
		    
		    retornoServidor = retornoServidor.replace("[", "").replace("]", "").replace(" ", "");
	        	       
		    ServidorTabela modeloTabela = new ServidorTabela();
		    
	        if(!retornoServidor.isEmpty()) {
	        	
	        	ArrayList<String> listaServidores = new ArrayList<String>(Arrays.asList(retornoServidor.split(",")));
	        	
	    		for (int index = 0; index < listaServidores.size(); index++) {
	    			
	    			String stringListaServidores = listaServidores.get(index).toString();
	    			
	    			String vetorListaServidores []  = stringListaServidores.split(":");
	    			Servidor servidor = new Servidor();
	    			    			
	    			servidor.nome = vetorListaServidores[0];
	    			servidor.ip = vetorListaServidores[1];
	    			servidor.porta = Integer.parseInt(vetorListaServidores[2]);
	    			servidor.tamanhoArquivo = Integer.parseInt(vetorListaServidores[3]);

	    			modeloTabela.addRow(servidor);
	    		}
	        }
	        
	        jTable.setModel(modeloTabela);
	        
	        jButtonBaixar.setEnabled(true);
	        	        
		} catch (IOException erro) {
			JOptionPane.showMessageDialog(null, "Não foi possivel se conectar ao servidor", "Alerta", JOptionPane.WARNING_MESSAGE);
		}
		
		jButtonBuscar.setEnabled(true);
	}
} 