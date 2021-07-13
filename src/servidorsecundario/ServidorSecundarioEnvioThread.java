package servidorsecundario;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServidorSecundarioEnvioThread extends Thread {
	
	Socket socket;
	// Diretorio base para simular os varios servidores de arquivos
	String path = "PathServers/";

    public ServidorSecundarioEnvioThread(Socket socket) {
        this.socket = socket;
    }
    
    public void run() {
    	try {
    		// Recebimento do nome do arquivo
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
			
			String nomeArquivo = bufferedReader.readLine();
			
			System.out.println("Enviando arquivo: " + nomeArquivo + "\n");
			
			File file = new File(path + socket.getLocalPort() + "/" + nomeArquivo);
					
			FileInputStream fileInputStream = new FileInputStream(file);
			
			byte[] buffer = new byte[2048];
			int tamanhoBuffer;

			// Envio do arquivo do arquivo para o cliente em pacotes de 2048 bytes
			while ((tamanhoBuffer = fileInputStream.read(buffer)) != -1) {
				dataOutputStream.write(buffer, 0, tamanhoBuffer);
				dataOutputStream.flush();
			}
			
			fileInputStream.close();
			dataOutputStream.close();
			
		} 
		catch(IOException erro) {
			System.err.println(erro);	
		}   	
    }
}