package servidorsecundario;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorSecundario {
	
	static String ipServidor = "127.0.0.1";
	static String caminhoPastas = "PathServers/";
	
	public static void main(String[] args) throws IOException{
		
		ServerSocket serverSocketTemporario = ServerSocketTemporario();
		
		ServidorSecundarioEnvio servidorSecundarioEnvio = new ServidorSecundarioEnvio(serverSocketTemporario);
		servidorSecundarioEnvio.start();
			
		System.out.println("Socket para enviar arquivo criado na porta: " + serverSocketTemporario.getLocalPort());
		
		while (true) {
			
			try (MulticastSocket multicastSocket = new MulticastSocket(6000)) {
				
				byte buffer[] = new byte[256];
				
				DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
				
				multicastSocket.receive(datagramPacket);
				
				String retornoServidor = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
				
				String[] vetorRetornoServidor = retornoServidor.split(";");
				
				System.out.println("");
				System.out.println("Informa��o sobre o arquivo: " + vetorRetornoServidor[0]);
				System.out.println("Envio da informa��o na porta: " + vetorRetornoServidor[1]);
				
				File f = new File(caminhoPastas + serverSocketTemporario.getLocalPort() + "/" + vetorRetornoServidor[0]);
				
				if (f.exists()) {
					Socket socket = new Socket(ipServidor, Integer.parseInt(vetorRetornoServidor[1]));
					
					DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
					
					String nomeMaquina = InetAddress.getLocalHost().getHostName();
					dataOutputStream.writeBytes(nomeMaquina + ":" + ipServidor + ":" + serverSocketTemporario.getLocalPort() + ":" + f.length());
					
					socket.close();
				}
				
			}
			catch(IOException erro) {
				System.err.println(erro);
			}
			
		}
	}
	
	private static ServerSocket ServerSocketTemporario() {
		
		ServerSocket serverSocket = null;
		
		while (serverSocket == null) {
			for (int porta = 35000; porta < 50000; porta++) {
				try {
					serverSocket = new ServerSocket(porta);
		            break;
		            }
				catch (Exception erro) {
					
				}
			}
		}
		return serverSocket;
	}
}