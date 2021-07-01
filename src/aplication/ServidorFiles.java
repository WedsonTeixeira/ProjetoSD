package aplication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.Socket;

public class ServidorFiles {
	public static void main(String[] args) {
		while (true) {

			try {
				try (//Classe java para trabalhar com multicast ou broadcast
				MulticastSocket mcs = new MulticastSocket(6001)) {
					byte rec[] = new byte[256];
					DatagramPacket pkg = new DatagramPacket(rec, rec.length);
					mcs.receive(pkg);//recebendo dados enviados via broadcast
					String data = new String(pkg.getData(), 0, pkg.getLength());
					System.out.println("Nome de arquivo recebido no servers de arquivos: " + data);
					
					try (// Apos pesquisa enviar o nome e endereco
					Socket resposta_para_servidor = new Socket("localhost", 5000)) {
						DataOutputStream envio_para_servidor = new DataOutputStream(resposta_para_servidor.getOutputStream());
						envio_para_servidor.writeBytes("ACHEI"+'\n');
						envio_para_servidor.close();
					}
				}
			}
			catch (Exception e) {
				System.out.println("Erro: " + e.getMessage());
			}
		}
	}
}
