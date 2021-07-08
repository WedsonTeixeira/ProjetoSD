package cliente;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import javax.swing.JButton;
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
			String ipServidor = jTable.getValueAt(jTable.getSelectedRow(), 1).toString();
			int portaServidor = Integer.parseInt(jTable.getValueAt(jTable.getSelectedRow(), 2).toString());  
			int tamanhoArquivo = Integer.parseInt(jTable.getValueAt(jTable.getSelectedRow(), 3).toString());
			
			jProgressBar.setMinimum(0);
			jProgressBar.setMaximum(tamanhoArquivo);			
			
			Socket socket = new Socket(ipServidor, portaServidor);
			
			DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
			InputStream inputStream = socket.getInputStream();
		  
			dataOutputStream.writeBytes(jTextField.getText() + '\n');
		  
		    FileOutputStream fileOut = new FileOutputStream(jTextField.getText());

			byte[] buffer = new byte[2048];
			int bytesRead;
			int bytesReadIncremento = 0;
			
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				fileOut.write(buffer, 0, bytesRead);
				fileOut.flush();
				
				bytesReadIncremento = bytesReadIncremento + bytesRead;
				jProgressBar.setValue(bytesReadIncremento);
			}
			
			fileOut.close();
			socket.close();
			
			jButtonBuscar.setEnabled(true);
			jButtonBaixar.setEnabled(true);
			
		} catch (IOException erro) {
			System.err.println(erro);
		}
	}
}