package aplication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Cliente {
	public static void main(String[] args) throws IOException{
        
        Socket clientSocket = new Socket("localhost", 10000);
        
        DataOutputStream solicitacao_para_servidor = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader mensagem_vinda_servidor = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        BufferedReader entrada_usuario_cliente = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.print("Digite o nome arquivo: ");
		String nome_arquivo = entrada_usuario_cliente.readLine();
		solicitacao_para_servidor.writeBytes(nome_arquivo+'\n');
        
        String lista_servers_arquivo = mensagem_vinda_servidor.readLine();
        System.out.println("Lista de servers com o arquivo: " + lista_servers_arquivo);
        clientSocket.close();
        
    }
}