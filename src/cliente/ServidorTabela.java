package cliente;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ServidorTabela extends AbstractTableModel{
	
	ArrayList <Servidor> listaServidores = new ArrayList <>();
    String[] Colunas = {"Nome do Servidor", "IP", "Porta", "Tamanho Arquivo"};
    
    @Override
    public String getColumnName(int column){
        return Colunas[column];   
    }
    @Override
    public int getRowCount() {
        return listaServidores.size();
    }

    @Override
    public int getColumnCount() {
        return Colunas.length;
    }

    @Override
    public Object getValueAt(int Linha, int Coluna) {
        switch(Coluna)
        {
            case 0:
                return listaServidores.get(Linha).getNome();
            case 1:
                return listaServidores.get(Linha).getIp();
            case 2:
                return listaServidores.get(Linha).getPorta();
            case 3:
                return listaServidores.get(Linha).getTamanhoArquivo();
        }
        return null;
        
    }
    
    public void addRow(Servidor servidor)
    {
        this.listaServidores.add(servidor);
        this.fireTableDataChanged();
    }
    
    public int arrayListSize()
    {
       return this.listaServidores.size();
    }
}