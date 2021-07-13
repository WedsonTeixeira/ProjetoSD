package cliente;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ThreadBaixar extends Thread{
		
	JTextField jTextField;
	JTable jTable;
	JButton jButtonBuscar;
	JButton jButtonBaixar;
	JProgressBar jProgressBar;
    
    public ThreadBaixar(JTextField jTextField, JTable jTable, JButton jButtonBuscar, JButton jButtonBaixar, JProgressBar jProgressBar) {
    	this.jTextField = jTextField;
		this.jTable = jTable;	
		this.jButtonBuscar = jButtonBuscar;	
		this.jButtonBaixar = jButtonBaixar;	
		this.jProgressBar = jProgressBar;
    }

	public void run() {
		
		try {
			/*Coletando dados de uma linha clicada na tabela 
			 * de servidores com o arquivo encontrado*/
			String ipServidor = jTable.getValueAt(jTable.getSelectedRow(), 1).toString();
			int portaServidor = Integer.parseInt(jTable.getValueAt(jTable.getSelectedRow(), 2).toString());  
			int tamanhoArquivo = Integer.parseInt(jTable.getValueAt(jTable.getSelectedRow(), 3).toString().replace(" bytes", ""));
			
			jProgressBar.setMinimum(0);
			jProgressBar.setMaximum(tamanhoArquivo);			
			
			// Socket para se conectar com o servidor de arquivos que possui o arquivo
			Socket socket = new Socket(ipServidor, portaServidor);
			
			DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
			InputStream inputStream = socket.getInputStream();
		  
			dataOutputStream.writeBytes(jTextField.getText() + '\n');
		  
		    FileOutputStream fileOut = new FileOutputStream(jTextField.getText());

			byte[] buffer = new byte[2048];
			int bytesRead;
			int bytesReadIncremento = 0;
			
			// Recebendo arquivo do servidor de arquivos em pacotes de 2048 bytes
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				fileOut.write(buffer, 0, bytesRead);
				fileOut.flush();
				
				bytesReadIncremento = bytesReadIncremento + bytesRead;
				jProgressBar.setValue(bytesReadIncremento);
			}
			
			fileOut.close();
			socket.close();
			
			JOptionPane.showMessageDialog(null,"Arquivo Baixado com Sucesso","Sucesso",JOptionPane.INFORMATION_MESSAGE);
				
			jProgressBar.setValue(0);
			
			jButtonBuscar.setEnabled(true);
			jButtonBaixar.setEnabled(true);
			
		} catch (IOException erro) {
			System.err.println(erro);
		}
	}
}