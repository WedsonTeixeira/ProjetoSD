package projetosd;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Cliente {

    public static void main(String argv[]) throws IOException{
        
        Socket clientSocket = new Socket("localhost", 10000);
        
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        outToServer.writeBytes("arquivo" + '\n');
        
        String arquivo = inFromServer.readLine();
        System.out.println(arquivo);
        clientSocket.close();
        
    }
}
