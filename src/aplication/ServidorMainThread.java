package aplication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServidorMainThread extends Thread {
	
	Socket s;
	ArrayList<String> lstServidorFiles = new ArrayList<String>();

    public ServidorMainThread(Socket s) {
        this.s = s;
    }
	
	public void run() {
		
		try {
			ServerSocket socketTemp = ServerSocketTemp();
			System.out.println("Socket temporario criado na porta: " + socketTemp.getLocalPort());
			
			BufferedReader mensagem_vinda_cliente = new BufferedReader(new InputStreamReader(s.getInputStream()));
		
			DataOutputStream resposta_para_cliente = new DataOutputStream(s.getOutputStream());
			
			String nomeArquivo = mensagem_vinda_cliente.readLine();
			
			String nomeArquivoPort = nomeArquivo + ';' + socketTemp.getLocalPort();
			
			byte[] b = nomeArquivoPort.getBytes();
	
			InetAddress addr = InetAddress.getByName("255.255.255.255");
			DatagramPacket pkg = new DatagramPacket(b, b.length, addr,6001);
	
			DatagramSocket ds = new DatagramSocket();
			ds.send(pkg);
			ds.close();
			
			TheadTime theadTime = new TheadTime(socketTemp);
			theadTime.start();
			
			while(true) {
				try {
					Socket conn_socket_servidor_arquivos = socketTemp.accept();
					BufferedReader mensagem_vinda_servidor_arquivos = new BufferedReader(new InputStreamReader(conn_socket_servidor_arquivos.getInputStream()));
					String resultado = mensagem_vinda_servidor_arquivos.readLine();
					
					lstServidorFiles.add(resultado);
				
				} 
				catch (IOException e) {
					break;
				}
			}
			resposta_para_cliente.writeBytes(lstServidorFiles.toString()+'\n');
		}	
		catch(IOException ex) {
            System.err.println(ex);
		}
	}
		

	public ServerSocket ServerSocketTemp() {
		
		ServerSocket server = null;
		
		while (server == null) {
			for (int port = 20000; port < 35000; port++) {
				try {
					server = new ServerSocket(port);
		            break;
		            }
				catch (Exception ex) {
					
				}
			}
		}
		return server;
	}
}