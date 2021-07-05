package aplication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class Cliente {
	public static void main(String[] args) throws IOException{
        
        Socket socketServidorMain = new Socket("localhost", 10000);
        
        DataOutputStream solicitacao_para_servidor = new DataOutputStream(socketServidorMain.getOutputStream());
        BufferedReader mensagem_vinda_servidor = new BufferedReader(new InputStreamReader(socketServidorMain.getInputStream()));
        BufferedReader entrada_usuario_cliente = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.print("Digite o nome arquivo: ");
		String nome_arquivo = entrada_usuario_cliente.readLine();
		solicitacao_para_servidor.writeBytes(nome_arquivo+'\n');
        
        String lista_servers_arquivo = mensagem_vinda_servidor.readLine();
        System.out.println("Lista de servers com o arquivo: " + lista_servers_arquivo);
        socketServidorMain.close();
        
        lista_servers_arquivo = lista_servers_arquivo.replace("[", "");
        lista_servers_arquivo = lista_servers_arquivo.replace("]", "");
        
        
        ArrayList<String> myList = new ArrayList<String>(Arrays.asList(lista_servers_arquivo.split(",")));
        
        String IP = myList.get(0).toString();
        Socket socketServidorSecondary = new Socket(IP.split("/")[0], Integer.parseInt(IP.split(":")[1]));
        
        DataOutputStream solicitacao_para_servidor_secondary = new DataOutputStream(socketServidorSecondary.getOutputStream());
        BufferedReader mensagem_vinda_servidor_secondary = new BufferedReader(new InputStreamReader(socketServidorSecondary.getInputStream()));
        
        solicitacao_para_servidor_secondary.writeBytes(nome_arquivo+'\n');
        
        String lista_secondary = mensagem_vinda_servidor_secondary.readLine();
        System.out.println(lista_secondary);
        socketServidorSecondary.close();
   
        
    }
}