package aplication;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class ServidorTabela extends AbstractTableModel{
	
	ArrayList <ServidorTabelaInfo> Armazenar = new ArrayList <>();
    String[] Colunas = {"Nome da Maquina", "IP", "Porta"};
    
    @Override
    public String getColumnName(int column){
        return Colunas[column];   
    }
    @Override
    public int getRowCount() {
        return Armazenar.size();
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
                return Armazenar.get(Linha).getNomeMaquina();
            case 1:
                return Armazenar.get(Linha).getIpMaquina();
            case 2:
                return Armazenar.get(Linha).getPort();
        }
        return null;
        
    }
    
    public void addRow(ServidorTabelaInfo servidorTabelaInfo)
    {
        this.Armazenar.add(servidorTabelaInfo);
        this.fireTableDataChanged();
    }
    
    public int arrayListSize()
    {
       return this.Armazenar.size();
    }
}


