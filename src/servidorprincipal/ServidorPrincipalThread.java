package servidorprincipal;

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

public class ServidorPrincipalThread extends Thread {
	
	Socket socket;
	ArrayList<String> listaServidores = new ArrayList<String>();

    public ServidorPrincipalThread(Socket socket) {
        this.socket = socket;
    }

	public void run() {
		
		try {
			ServerSocket serverSocketTemporario = ServerSocketTemporario();
			
			System.out.println("Socket temporario criado na porta: " + serverSocketTemporario.getLocalPort());
			
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
			
			String retornoCliente = bufferedReader.readLine();
			String nomeArquivoPorta = retornoCliente + ';' + serverSocketTemporario.getLocalPort();
			
			byte[] buffer = nomeArquivoPorta.getBytes();
	
			InetAddress inetAddress = InetAddress.getByName("255.255.255.255");
			DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, 6000);
			DatagramSocket datagramSocket = new DatagramSocket();
			
			datagramSocket.send(datagramPacket);
			datagramSocket.close();
			
			serverSocketTemporario.setSoTimeout(10000);
			
			while(true) {
				try {
					Socket socketServidorSecundario = serverSocketTemporario.accept();
					
					BufferedReader bufferedReaderServidorSecundario = new BufferedReader(new InputStreamReader(socketServidorSecundario.getInputStream()));
					String retornoServidorSecundario = bufferedReaderServidorSecundario.readLine();
					
					listaServidores.add(retornoServidorSecundario);
				} 
				catch (IOException e) {
					break;
				}
			}
			dataOutputStream.writeBytes(listaServidores.toString() + '\n');
			
			serverSocketTemporario.close();
		}	
		catch(IOException erro) {
			System.err.println(erro);
		}
	}
		

	private static ServerSocket ServerSocketTemporario() {
		
		ServerSocket serverSocket = null;
		
		while (serverSocket == null) {
			for (int porta = 20000; porta < 35000; porta++) {
				try {
					serverSocket = new ServerSocket(porta);
		            break;
		            }
				catch (Exception erro) {
					System.err.println(erro);
				}
			}
		}
		return serverSocket;
	}
}