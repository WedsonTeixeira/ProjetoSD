package aplication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {
	
	static ArrayList<Integer> lstServidorFiles = new ArrayList<Integer>();

	public static void main(String[] args) throws Exception {
        
       try (ServerSocket socket_conexao_cliente = new ServerSocket(10000)) {
		while (true) {
            Socket connectionSocket = socket_conexao_cliente.accept();
            
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
    				try (ServerSocket socket_conexao_resposta_servidor_arquivos = new ServerSocket(5000)) {
						while(true) {
							Socket conn_socket_servidor_arquivos = socket_conexao_resposta_servidor_arquivos.accept();
							BufferedReader mensagem_vinda_servidor_arquivos = new BufferedReader(new InputStreamReader(conn_socket_servidor_arquivos.getInputStream()));
							String resultado_busca_arquivo = mensagem_vinda_servidor_arquivos.readLine();
							System.out.println(resultado_busca_arquivo);
							System.out.println(conn_socket_servidor_arquivos.getInetAddress());
							System.out.println(conn_socket_servidor_arquivos.getPort());
							lstServidorFiles.add(conn_socket_servidor_arquivos.getPort());
							break;
						}
					}
    			}
    		}
    		catch (Exception e) {
    			System.out.println("Nao foi possivel enviar a mensagem");
    		}
            resposta_para_cliente.writeBytes("Aqui vai uma lista: " + lstServidorFiles.toString() + "\n");
        }
	}
    }
}
