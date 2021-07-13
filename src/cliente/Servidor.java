package cliente;

/* Classe para representar uma linha na 
 * tabela de servidores secundarios
 * */

public class Servidor {
	
	String nome;
	String ip;
	int porta;
	String tamanhoArquivo;
    
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public int getPorta() {
		return porta;
	}
	
	public void setPorta(int porta) {
		this.porta = porta;
	}
	
	public String getTamanhoArquivo() {
		return tamanhoArquivo;
	}

	public void setTamanhoArquivo(String tamanhoArquivo) {
		this.tamanhoArquivo = tamanhoArquivo;
	}
}
