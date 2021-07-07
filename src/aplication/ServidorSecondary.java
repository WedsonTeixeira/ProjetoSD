package aplication;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorSecondary {
	
	static String ipServidor = "127.0.0.1";
	static String nomeMaquina;
	static String path = "PathServers/";
	
	
	public static void main(String[] args) throws IOException{
		
		ServerSocket serverSocket = ServerSocketTemp();
		ServidorSecondaryFiles servidorSecondaryFiles = new ServidorSecondaryFiles(serverSocket);
		servidorSecondaryFiles.start();
			
		System.out.println("Socket para envio de arquivo iniciado: " + ipServidor + ":" + serverSocket.getLocalPort());
		System.out.println("");
		
		while (true) {
			
			try (MulticastSocket mcs = new MulticastSocket(1001)) {
				byte rec[] = new byte[256];
				DatagramPacket pkg = new DatagramPacket(rec, rec.length);
				
				mcs.receive(pkg);
				String data = new String(pkg.getData(), 0, pkg.getLength());
				String[] textoSeparado = data.split(";");
				
				System.out.println("Pedido sobre informacao do arquivo: " + textoSeparado[0]);
				System.out.println("Envio sobre a informacao na porta: " + textoSeparado[1]);
				System.out.println("");
				
				File f = new File(path + serverSocket.getLocalPort() + "/" + textoSeparado[0]);
				System.out.print(path + serverSocket.getLocalPort() + "/" + textoSeparado[0]);
				
				if (f.exists()) {
					Socket resposta_para_servidor = new Socket(ipServidor, Integer.parseInt(textoSeparado[1]));
					nomeMaquina = InetAddress.getLocalHost().getHostName();
					
					DataOutputStream envio_para_servidor = new DataOutputStream(resposta_para_servidor.getOutputStream());
					envio_para_servidor.writeBytes(nomeMaquina + ":" + ipServidor + ":" + serverSocket.getLocalPort());
					resposta_para_servidor.close();
				}
				
			}
			catch(IOException ex) {
	            System.err.println(ex);
			}
			
		}
	}
	
	private static ServerSocket ServerSocketTemp() {
			
			ServerSocket server = null;
			
			while (server == null) {
				for (int port = 35000; port < 50000; port++) {
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