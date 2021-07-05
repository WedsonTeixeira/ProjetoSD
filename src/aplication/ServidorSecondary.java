package aplication;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorSecondary {
	
	public static void main(String[] args) throws IOException{
		
		ServerSocket serverSocket = ServerSocketTemp();
		ServidorSecondaryFiles servidorSecondaryFiles = new ServidorSecondaryFiles(serverSocket);
		servidorSecondaryFiles.start();
		
		System.out.println("Socket para envio de arquivo iniciado: " + serverSocket.getLocalSocketAddress());
		System.out.println("");
		
		while (true) {
			
			try (MulticastSocket mcs = new MulticastSocket(6001)) {
				byte rec[] = new byte[256];
				DatagramPacket pkg = new DatagramPacket(rec, rec.length);
				
				mcs.receive(pkg);
				String data = new String(pkg.getData(), 0, pkg.getLength());
				String[] textoSeparado = data.split(";");
				
				System.out.println("Pedido sobre informacao do arquivo: " + textoSeparado[0]);
				System.out.println("Envio sobre a informacao na porta: " + textoSeparado[1]);
				System.out.println("");
				
				Socket resposta_para_servidor = new Socket("localhost", Integer.parseInt(textoSeparado[1]));
				
				DataOutputStream envio_para_servidor = new DataOutputStream(resposta_para_servidor.getOutputStream());

				envio_para_servidor.writeBytes(serverSocket.getLocalSocketAddress().toString());
				
				resposta_para_servidor.close();
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