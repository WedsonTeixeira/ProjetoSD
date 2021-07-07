package aplication;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServidorSecondaryFilesThread extends Thread {
	
	Socket s;
	static String path = "PathServers/";

    public ServidorSecondaryFilesThread(Socket s) {
        this.s = s;
    }
    
    public void run() {
    	try {
			BufferedReader mensagem_vinda_cliente = new BufferedReader(new InputStreamReader(s.getInputStream()));
			DataOutputStream resposta_para_cliente = new DataOutputStream(s.getOutputStream());
			
			String nomeArquivo = mensagem_vinda_cliente.readLine();
			System.out.println("Enviando arquivo: " + nomeArquivo + "\n");
			
			File f = new File(path + s.getLocalPort() + "/" + nomeArquivo);
			System.out.print(path + s.getLocalPort() + "/" + nomeArquivo);
					
			FileInputStream fileIn = new FileInputStream(f);
			
			byte[] cbuffer = new byte[2048];
			int bytesRead;

			while ((bytesRead = fileIn.read(cbuffer)) != -1) {
				resposta_para_cliente.write(cbuffer, 0, bytesRead);
				resposta_para_cliente.flush();
			}
			
			fileIn.close();
			resposta_para_cliente.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		    	
    }
	
}
