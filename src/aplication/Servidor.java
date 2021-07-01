package aplication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	public static void main(String[] args) throws Exception {
        
       try (ServerSocket welcomeSocket = new ServerSocket(10000)) {
		while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            
            BufferedReader mensagem_vinda_cliente = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream resposta_para_cliente = new DataOutputStream(connectionSocket.getOutputStream());
            
            String nomeArquivo = mensagem_vinda_cliente.readLine();
            
            // Enviar mensagem multicast perguntando quais servers possuem o arquivo
            try {
    			byte[] b = nomeArquivo.getBytes();
    			//Definindo o endereço de envio do pacote neste caso o endereço de broadcast
    			InetAddress addr = InetAddress.getByName("255.255.255.255");
    			DatagramPacket pkg = new DatagramPacket(b, b.length, addr,6001);
    			try (DatagramSocket ds = new DatagramSocket()) {
    				ds.send(pkg);//enviando pacote broadcast
    			}
    		}
    		catch (Exception e) {
    			System.out.println("Nao foi possivel enviar a mensagem");
    		}
            resposta_para_cliente.writeBytes("Aqui vai uma lista\n");
        }
	}
    }
}
