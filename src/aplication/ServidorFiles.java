package aplication;

import java.net.DatagramPacket;
import java.net.MulticastSocket;

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
					
					// Apos pesquisa enviar o nome e endereco
				}
			}
			catch (Exception e) {
				System.out.println("Erro: " + e.getMessage());
			}
		}
	}
}
