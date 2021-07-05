package aplication;


import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.Socket;

public class ServidorFiles {
	
	public static void main(String[] args){
		
		while (true) {
			
			try (MulticastSocket mcs = new MulticastSocket(6001)) {
				byte rec[] = new byte[256];
				DatagramPacket pkg = new DatagramPacket(rec, rec.length);
				
				mcs.receive(pkg);
				String data = new String(pkg.getData(), 0, pkg.getLength());
				String[] textoSeparado = data.split(";");
				
				System.out.println("Nome de arquivo: " + textoSeparado[0]);
				System.out.println("Porta do Socket: " + textoSeparado[1]);
				System.out.println("");
				
				Socket resposta_para_servidor = new Socket("localhost", Integer.parseInt(textoSeparado[1]));
				
				DataOutputStream envio_para_servidor = new DataOutputStream(resposta_para_servidor.getOutputStream());
				
				envio_para_servidor.writeBytes("ACHEI"+'\n');
				
				resposta_para_servidor.close();
			}
			catch(IOException ex) {
	            System.err.println(ex);
			}
			
		}
	}
}